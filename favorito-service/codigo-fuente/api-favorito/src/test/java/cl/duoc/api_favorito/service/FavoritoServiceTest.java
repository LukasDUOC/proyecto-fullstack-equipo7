package cl.duoc.api_favorito.service;


import cl.duoc.api_favorito.client.ContenidoClient;
import cl.duoc.api_favorito.client.UsuarioClient;
import cl.duoc.api_favorito.dto.FavoritoCreateDTO;
import cl.duoc.api_favorito.dto.FavoritoDTO;
import cl.duoc.api_favorito.exception.RecursoNoEncontradoException;
import cl.duoc.api_favorito.model.Favorito;
import cl.duoc.api_favorito.repository.FavoritoRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FavoritoServiceTest {

    @Mock
    private FavoritoRepository repository;

    @Mock
    private ContenidoClient contenidoClient;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private FavoritoService favoritoService;

    // ── findAll ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("findAll - debe retornar lista de favoritos cuando existen registros")
    void debeRetornarListaDeFavoritos() {
        // Given
        List<Favorito> favoritosSimulados = List.of(
            new Favorito(1L, 100L, 500L, true),
            new Favorito(2L, 200L, 600L, false)
        );
        when(repository.findAll()).thenReturn(favoritosSimulados);

        // When
        List<FavoritoDTO> resultado = favoritoService.findAll();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(100L, resultado.get(0).getUsuarioId());
        assertTrue(resultado.get(0).isFavorito());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll - debe retornar lista vacía cuando no hay favoritos")
    void debeRetornarListaVaciaSiNoHayFavoritos() {
        // Given
        when(repository.findAll()).thenReturn(List.of());

        // When
        List<FavoritoDTO> resultado = favoritoService.findAll();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // ── findById ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("findById - debe retornar el DTO correcto cuando el favorito existe")
    void debeRetornarFavoritoPorId() {
        // Given
        Favorito favorito = new Favorito(1L, 100L, 500L, true);
        when(repository.findById(1L)).thenReturn(Optional.of(favorito));

        // When
        FavoritoDTO resultado = favoritoService.findById(1L);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(100L, resultado.getUsuarioId());
        assertTrue(resultado.isFavorito());
    }

    @Test
    @DisplayName("findById - debe lanzar RecursoNoEncontradoException cuando el ID no existe")
    void debeLanzarExcepcionCuandoFavoritoNoExiste() {
        // Given
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () ->
            favoritoService.findById(999L)
        );
    }

    // ── crear ──────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("crear - debe persistir y retornar el favorito cuando los clientes externos responden ok")
    void debeCrearFavoritoCorrectamente() {
        // Given
        FavoritoCreateDTO dto = new FavoritoCreateDTO(100L, 500L, true);
        Favorito guardado = new Favorito(3L, 100L, 500L, true);

        when(usuarioClient.getUsuario(100L)).thenReturn(null);
        when(contenidoClient.obtenerContenido(500L)).thenReturn(null);
        when(repository.save(any(Favorito.class))).thenReturn(guardado);

        // When
        FavoritoDTO resultado = favoritoService.crear(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
        assertEquals(100L, resultado.getUsuarioId());
        assertTrue(resultado.isFavorito());
        verify(repository, times(1)).save(any(Favorito.class));
    }

   

    // ── actualizarEstado ───────────────────────────────────────────────────────

    @Test
    @DisplayName("actualizarEstado - debe modificar el estado correctamente cuando el ID existe")
    void debeActualizarEstadoCorrectamente() {
        // Given
        Favorito favoritoOriginal = new Favorito(1L, 100L, 500L, false);
        Favorito favoritoModificado = new Favorito(1L, 100L, 500L, true);

        when(repository.findById(1L)).thenReturn(Optional.of(favoritoOriginal));
        when(repository.save(any(Favorito.class))).thenReturn(favoritoModificado);

        // When
        FavoritoDTO resultado = favoritoService.actualizarEstado(1L, true);

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isFavorito());
        verify(repository, times(1)).save(any(Favorito.class));
    }

    @Test
    @DisplayName("actualizarEstado - debe lanzar RecursoNoEncontradoException cuando se actualiza un ID inexistente")
    void debeLanzarExcepcionAlActualizarIdInexistente() {
        // Given
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () ->
            favoritoService.actualizarEstado(999L, true)
        );
        verify(repository, never()).save(any(Favorito.class));
    }
}