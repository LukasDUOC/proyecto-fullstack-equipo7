package cl.duoc.api_busqueda.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.api_busqueda.client.ContenidoClient;
import cl.duoc.api_busqueda.dto.BusquedaCreateDTO;
import cl.duoc.api_busqueda.dto.BusquedaDTO;
import cl.duoc.api_busqueda.dto.ContenidoDTO;
import cl.duoc.api_busqueda.exception.RecursoNoEncontradoException;
import cl.duoc.api_busqueda.exception.ServicioNoDisponibleException;
import cl.duoc.api_busqueda.model.Busqueda;
import cl.duoc.api_busqueda.repository.BusquedaRepository;
import feign.FeignException;


@ExtendWith(MockitoExtension.class)
public class BusquedaServiceTest {

    @Mock
    private BusquedaRepository repository;

    @Mock
    private ContenidoClient contenidoClient; 

    @InjectMocks
    private BusquedaService busquedaService;

  

    @Test
    @DisplayName("buscar por ID - debe retornar contenido externo y guardar en historial")
    void debeBuscarPorIdCorrectamente() {
        // Given
        BusquedaCreateDTO dto = new BusquedaCreateDTO(1L, null);
        ContenidoDTO contenidoMock = new ContenidoDTO(1L, "Inception", "Ciencia Ficción", "Sinopsis...", "148 min", "pelicula", "Netflix", LocalDate.parse("2010-07-28"));
        
        when(contenidoClient.buscarPorId(1L)).thenReturn(contenidoMock);
        when(repository.save(any(Busqueda.class))).thenReturn(new Busqueda());

        // When
        ContenidoDTO resultado = busquedaService.buscar(dto);

        // Then
        assertNotNull(resultado);
        assertEquals("Inception", resultado.getTitulo());
        verify(contenidoClient, times(1)).buscarPorId(1L);
        verify(repository, times(1)).save(any(Busqueda.class));
    }

    @Test
    @DisplayName("buscar por Título - debe retornar el primer resultado y guardar en historial")
    void debeBuscarPorTituloCorrectamente() {
        // Given
        BusquedaCreateDTO dto = new BusquedaCreateDTO(null, "Avatar");
        ContenidoDTO contenidoMock = new ContenidoDTO(5L, "Avatar", "Acción", "Sinopsis...", "162 min", "pelicula", "Disney+", LocalDate.parse("2009-12-18"));
        
        when(contenidoClient.buscarPorTitulo("Avatar")).thenReturn(List.of(contenidoMock));
        when(repository.save(any(Busqueda.class))).thenReturn(new Busqueda());

        // When
        ContenidoDTO resultado = busquedaService.buscar(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(5L, resultado.getId());
        verify(contenidoClient, times(1)).buscarPorTitulo("Avatar");
        verify(repository, times(1)).save(any(Busqueda.class));
    }



    @Test
    @DisplayName("buscar por ID - debe lanzar RecursoNoEncontradoException cuando Feign responde 404")
    void debeManejarError404DeFeign() {
        // Given
        BusquedaCreateDTO dto = new BusquedaCreateDTO(999L, null);
        
        // Simulamos la excepción de Feign para un error 404
        FeignException.NotFound feignException = mock(FeignException.NotFound.class);
        when(contenidoClient.buscarPorId(999L)).thenThrow(feignException);

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () -> busquedaService.buscar(dto));
        verify(repository, never()).save(any());
    }


    

    @Test
    @DisplayName("findAll - debe retornar lista del historial mapeada a DTO")
    void debeRetornarHistorialCompleto() {
        // Given
        List<Busqueda> listaSimulada = List.of(
            new Busqueda(1L, 10L, "The Matrix"),
            new Busqueda(2L, 11L, "Gladiador")
        );
        when(repository.findAll()).thenReturn(listaSimulada);

        // When
        List<BusquedaDTO> resultado = busquedaService.findAll();

        // Then
        assertEquals(2, resultado.size());
        assertEquals("The Matrix", resultado.get(0).getTitulo());
        assertEquals(11L, resultado.get(1).getContenidoId());
    }

    @Test
    @DisplayName("obtenerPorId - debe retornar el registro si existe")
    void debeRetornarBusquedaPorIdExistente() {
        // Given
        Busqueda busqueda = new Busqueda(1L, 50L, "Interstellar");
        when(repository.findById(1L)).thenReturn(Optional.of(busqueda));

        // When
        BusquedaDTO resultado = busquedaService.obtenerPorId(1L);

        // Then
        assertNotNull(resultado);
        assertEquals("Interstellar", resultado.getTitulo());
    }



    @Test
    @DisplayName("buscarPorTexto - debe retornar coincidencias de títulos del historial")
    void debeBuscarEnHistorialPorTexto() {
        // Given
        when(repository.findByTituloContainingIgnoreCase("batman"))
            .thenReturn(List.of(new Busqueda(1L, 5L, "The Batman")));

        // When
        List<BusquedaDTO> resultado = busquedaService.buscarPorTexto("batman");

        // Then
        assertEquals(1, resultado.size());
        assertEquals("The Batman", resultado.get(0).getTitulo());
    }

    @Test
    @DisplayName("eliminarBusquedaPorId - debe invocar deleteById si existe")
    void debeEliminarBusquedaExistente() {
        // Given
        when(repository.existsById(1L)).thenReturn(true);

        // When
        busquedaService.eliminarBusquedaPorId(1L);

        // Then
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("eliminarBusquedaPorId - debe lanzar excepción si el ID no existe en el historial")
    void debeLanzarExcepcionAlEliminarInexistente() {
        // Given
        when(repository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () -> busquedaService.eliminarBusquedaPorId(999L));
        verify(repository, never()).deleteById(any());
    }

    @Test
    @DisplayName("eliminarTodo - debe limpiar el historial completo")
    void debeEliminarTodoElHistorial() {
        // When
        busquedaService.eliminarTodo();

        // Then
        verify(repository, times(1)).deleteAll();
    }
}
