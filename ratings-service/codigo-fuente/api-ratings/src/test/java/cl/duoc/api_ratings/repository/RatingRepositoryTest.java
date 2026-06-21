package cl.duoc.api_ratings.repository;

import cl.duoc.api_ratings.model.Rating;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RatingRepositoryTest {

    @Autowired
    private RatingRepository repository;

    @Test
    @DisplayName("Debe guardar un rating correctamente")
    void debeGuardarRating() {

        // Arrange
        Rating rating = new Rating();
        rating.setIdUsuario(1L);
        rating.setIdContenido(10L);
        rating.setPuntuacion(new BigDecimal("4.5"));

        // Act
        Rating guardado = repository.save(rating);

        // Assert
        assertNotNull(guardado.getId());
        assertEquals(1L, guardado.getIdUsuario());
        assertEquals(10L, guardado.getIdContenido());
        assertEquals(new BigDecimal("4.5"), guardado.getPuntuacion());
    }

    @Test
    @DisplayName("Debe encontrar un rating por id")
    void debeEncontrarRatingPorId() {

        // Arrange
        Rating rating = new Rating();
        rating.setIdUsuario(2L);
        rating.setIdContenido(20L);
        rating.setPuntuacion(new BigDecimal("5.0"));

        Rating guardado = repository.save(rating);

        // Act
        Rating encontrado = repository.findById(guardado.getId()).orElse(null);

        // Assert
        assertNotNull(encontrado);
        assertEquals(guardado.getId(), encontrado.getId());
    }

    @Test
    @DisplayName("Debe listar todos los ratings")
    void debeListarTodosLosRatings() {

        // Arrange
        Rating r1 = new Rating();
        r1.setIdUsuario(1L);
        r1.setIdContenido(100L);
        r1.setPuntuacion(new BigDecimal("3.5"));

        Rating r2 = new Rating();
        r2.setIdUsuario(2L);
        r2.setIdContenido(200L);
        r2.setPuntuacion(new BigDecimal("4.0"));

        repository.save(r1);
        repository.save(r2);

        // Act
        var ratings = repository.findAll();

        // Assert
        assertTrue(ratings.size() >= 2);
    }

    @Test
    @DisplayName("Debe verificar existencia por usuario y contenido")
    void debeVerificarExistenciaPorUsuarioYContenido() {

        // Arrange
        Rating rating = new Rating();
        rating.setIdUsuario(5L);
        rating.setIdContenido(50L);
        rating.setPuntuacion(new BigDecimal("4.0"));

        repository.save(rating);

        // Act
        boolean existe = repository.existsByIdUsuarioAndIdContenido(5L, 50L);

        // Assert
        assertTrue(existe);
    }

    @Test
    @DisplayName("Debe eliminar un rating")
    void debeEliminarRating() {

        // Arrange
        Rating rating = new Rating();
        rating.setIdUsuario(8L);
        rating.setIdContenido(80L);
        rating.setPuntuacion(new BigDecimal("2.5"));

        Rating guardado = repository.save(rating);

        // Act
        repository.deleteById(guardado.getId());

        // Assert
        assertFalse(repository.findById(guardado.getId()).isPresent());
    }
}