package cl.duoc.api_ratings.service;

import cl.duoc.api_ratings.client.ContenidoClient;
import cl.duoc.api_ratings.client.UsuarioClient;
import cl.duoc.api_ratings.dto.RatingCreateDTO;
import cl.duoc.api_ratings.dto.RatingDTO;
import cl.duoc.api_ratings.exception.RecursoNoEncontradoException;
import cl.duoc.api_ratings.model.Rating;
import cl.duoc.api_ratings.repository.RatingRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    @Mock
    private RatingRepository repository;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private ContenidoClient contenidoClient;

    @InjectMocks
    private RatingService service;

    @Test
    @DisplayName("Debe obtener un rating por id")
    void debeObtenerRatingPorId() {

        // Arrange
        Rating rating = new Rating();
        rating.setId(1L);
        rating.setIdUsuario(10L);
        rating.setIdContenido(20L);
        rating.setPuntuacion(new BigDecimal(5));
        rating.setFechaRating(LocalDateTime.now());

        when(repository.findById(1L))
                .thenReturn(Optional.of(rating));

        // Act
        RatingDTO resultado = service.findById(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(new BigDecimal(5), resultado.getPuntuacion());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el rating no existe")
    void debeLanzarExcepcionCuandoNoExiste() {

        // Arrange
        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                RecursoNoEncontradoException.class,
                () -> service.findById(1L)
        );
    }

    @Test
    @DisplayName("Debe crear un rating correctamente")
    void debeCrearRating() {

        // Arrange
        RatingCreateDTO dto = new RatingCreateDTO();
        dto.setIdUsuario(1L);
        dto.setIdContenido(2L);
        dto.setPuntuacion(new BigDecimal(4));

        when(repository.existsByIdUsuarioAndIdContenido(1L, 2L))
                .thenReturn(false);

        Rating guardado = new Rating();
        guardado.setId(100L);
        guardado.setIdUsuario(1L);
        guardado.setIdContenido(2L);
        guardado.setPuntuacion(new BigDecimal(4));
        guardado.setFechaRating(LocalDateTime.now());

        when(repository.save(any(Rating.class)))
                .thenReturn(guardado);

        // Act
        RatingDTO resultado = service.crear(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals(new BigDecimal(4), resultado.getPuntuacion());

        verify(repository, times(1))
                .save(any(Rating.class));
    }

    @Test
    @DisplayName("Debe impedir que un usuario califique dos veces el mismo contenido")
    void debeImpedirRatingDuplicado() {

        // Arrange
        RatingCreateDTO dto = new RatingCreateDTO();
        dto.setIdUsuario(1L);
        dto.setIdContenido(2L);
        dto.setPuntuacion(new BigDecimal(5));

        when(repository.existsByIdUsuarioAndIdContenido(1L, 2L))
                .thenReturn(true);

        // Act + Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.crear(dto)
        );

        assertEquals(
                "El usuario ya calificó este contenido",
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("Debe actualizar la puntuación de un rating")
    void debeActualizarRating() {

        // Arrange
        Rating existente = new Rating();
        existente.setId(1L);
        existente.setPuntuacion(new BigDecimal(3));

        RatingCreateDTO dto = new RatingCreateDTO();
        dto.setPuntuacion(new BigDecimal(5));

        when(repository.findById(1L))
                .thenReturn(Optional.of(existente));

        when(repository.save(any(Rating.class)))
                .thenReturn(existente);

        // Act
        RatingDTO resultado = service.actualizar(1L, dto);

        // Assert
        assertEquals(new BigDecimal(5), resultado.getPuntuacion());
    }

    @Test
    @DisplayName("Debe eliminar un rating existente")
    void debeEliminarRating() {

        // Arrange
        when(repository.existsById(1L))
                .thenReturn(true);

        // Act
        service.eliminar(1L);

        // Assert
        verify(repository, times(1))
                .deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar un rating inexistente")
    void debeLanzarExcepcionAlEliminarInexistente() {

        // Arrange
        when(repository.existsById(1L))
                .thenReturn(false);

        // Act + Assert
        assertThrows(
                RecursoNoEncontradoException.class,
                () -> service.eliminar(1L)
        );
    }
}