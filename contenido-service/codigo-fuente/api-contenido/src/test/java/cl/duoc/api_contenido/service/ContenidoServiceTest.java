package cl.duoc.api_contenido.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import cl.duoc.api_contenido.dto.ContenidoCreateDTO;
import cl.duoc.api_contenido.dto.ContenidoDTO;
import cl.duoc.api_contenido.exception.RecursoNoEncontradoException;
import cl.duoc.api_contenido.model.Contenido;
import cl.duoc.api_contenido.repository.ContenidoRepository;

@ExtendWith(MockitoExtension.class)
public class ContenidoServiceTest {
    @Mock
    private ContenidoRepository contenidoRepository;

    @InjectMocks
    private ContenidoService contenidoService;

    @Test
    @DisplayName("findAll - debe retornar una lista de contenido cuando existen registros")
    void debeRetornarListaDeContenido() {
        // Given

        List<Contenido> contenidoSim = List.of(
                new Contenido(
                        1L,
                        "Pinocho de Guillermo del Toro",
                        "Animación",
                        "Una reinvención musical en stop-motion del clásico cuento de madera, ambientada en la Italia fascista de los años 30",
                        "117 minutos",
                        "pelicula",
                        "Netflix",
                        LocalDate.parse("2022-12-09")),
                new Contenido(
                        2L,
                        "Ex Machina",
                        "Ciencia Ficción",
                        "Un programador es seleccionado para participar en un experimento innovador que implica evaluar las cualidades humanas de una inteligencia artificial avanzada",
                        "108 minutos",
                        "pelicula",
                        "Netflix",
                        LocalDate.parse("2015-04-16")));

        when(contenidoRepository.findAll()).thenReturn(contenidoSim);

        // WHEN

        List<ContenidoDTO> resultado = contenidoService.obtenerTodos();

        // THEN
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Pinocho de Guillermo del Toro", resultado.get(0).getTitulo());
        assertEquals("Animación", resultado.get(0).getGenero());

        
        assertEquals("Ex Machina", resultado.get(1).getTitulo());
        assertEquals("Ciencia Ficción", resultado.get(1).getGenero());
        verify(contenidoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll - debe retornar lista vacía cuando no hay contenido")
    void debeRetornarListaVaciaSiNoHayProductos() {
        // Given
        when(contenidoRepository.findAll()).thenReturn(List.of());

        // When
        List<ContenidoDTO> resultado = contenidoService.obtenerTodos();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("findById - debe retornar el DTO correcto cuando el contenido existe")
    void debeRetornarContenidoPorId() {
        // Given
        Contenido contenido = new Contenido(1L,
                "The Terminator",
                "Acción",
                "Un asesino cibernético es enviado desde el futuro para eliminar a una mujer cuyo hijo no nacido liderará una guerra contra las máquinas",
                "107 minutos",
                "pelicula",
                "Prime Video",
                LocalDate.parse("1984-10-26"));

        when(contenidoRepository.findById(1L)).thenReturn(Optional.of(contenido));

        // When
        ContenidoDTO resultado = contenidoService.obtenerPorId(1L);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("The Terminator", resultado.getTitulo());
        assertEquals("Acción", resultado.getGenero());
    }

    @Test
    @DisplayName("findById - debe lanzar RecursoNoEncontradoException cuando el ID no existe")
    void debeLanzarExcepcionCuandoProductoNoExiste() {
        // Given
        when(contenidoRepository.findById(999L)).thenReturn(Optional.empty());

        // When Y Then
        assertThrows(RecursoNoEncontradoException.class, () -> contenidoService.obtenerPorId(999L));
    }

    @Test
    @DisplayName("crear - debe persistir y retornar el contenido con Id generado")
    void debeCrearContenidoCorrectamente() {
        // Given
        ContenidoCreateDTO dto = new ContenidoCreateDTO(

                "Breaking Bad",
                "Drama / Crimen",
                "Un profesor de química de secundaria con cáncer terminal se asocia con un antiguo estudiante para asegurar el futuro financiero de su familia fabricando y vendiendo metanfetamina",
                "62 episodios",
                "serie",
                "Netflix",
                LocalDate.parse("2008-01-20"));

        Contenido guardado = new Contenido(
                3L,
                "Breaking Bad",
                "Drama / Crimen",
                "Un profesor de química de secundaria con cáncer terminal se asocia con un antiguo estudiante para asegurar el futuro financiero de su familia fabricando y vendiendo metanfetamina",
                "62 episodios",
                "serie",
                "Netflix",
                LocalDate.parse("2008-01-20"));

        // When
        when(contenidoRepository.save(any(Contenido.class))).thenReturn(guardado);
        ContenidoDTO resultado = contenidoService.crearContenido(dto);

        // Then

        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
        assertEquals("Breaking Bad", resultado.getTitulo());
        verify(contenidoRepository, times(1)).save(any(Contenido.class));
    }

    @Test
    @DisplayName("crear - debe lanzar excepcion cuando el titulo ya existe")
    void debeLanzarExcepcionCuandoTituloYaExiste() {
        // Given
        ContenidoCreateDTO dto = new ContenidoCreateDTO(
                "Breaking Bad",
                "Drama / Crimen",
                "Sinopsis...",
                "62 episodios",
                "serie",
                "Netflix",
                LocalDate.parse("2008-01-20"));

        when(contenidoRepository.existsByTituloIgnoreCase("Breaking Bad")).thenReturn(true);

        // When Y Then
      
        assertThrows(RuntimeException.class, () -> {
            contenidoService.crearContenido(dto);
        });

     
        verify(contenidoRepository, never()).save(any(Contenido.class));
    }

    @Test
    @DisplayName("eliminar - debe invocar delete cuando el contenido existe")
    void debeEliminarContenidoExistente() {
        // Given
        Contenido contenidoExistente = new Contenido();
        contenidoExistente.setId(1L);

        when(contenidoRepository.findById(1L)).thenReturn(Optional.of(contenidoExistente));

        // When
        contenidoService.eliminar(1L);

        // Then 
        verify(contenidoRepository, times(1)).delete(any(Contenido.class));
    }

    @Test
    @DisplayName("eliminar - debe lanzar excepción al intentar eliminar un ID inexistente")
    void debeLanzarExcepcionAlEliminarContenidoInexistente() {
        // Given
        when(contenidoRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () -> contenidoService.eliminar(999L));

        verify(contenidoRepository, never()).deleteById(any());
    }

}
