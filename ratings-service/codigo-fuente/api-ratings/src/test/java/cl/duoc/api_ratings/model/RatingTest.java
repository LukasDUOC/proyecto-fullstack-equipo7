package cl.duoc.api_ratings.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RatingTest {

    @Test
    @DisplayName("Constructor vacío - debe crear una instancia no nula")
    void constructorVacioDebeCrearInstanciaNoNula() {
        Rating rating = new Rating();

        assertNotNull(rating);
    }

    @Test
    @DisplayName("Constructor completo - debe asignar todos los campos correctamente")
    void constructorCompletoDebeAsignarTodosLosCampos() {

        LocalDateTime fecha = LocalDateTime.now();

        Rating rating = new Rating(
                1L,
                10L,
                20L,
                new BigDecimal("4.5"),
                fecha
        );

        assertEquals(1L, rating.getId());
        assertEquals(10L, rating.getIdUsuario());
        assertEquals(20L, rating.getIdContenido());
        assertEquals(new BigDecimal("4.5"), rating.getPuntuacion());
        assertEquals(fecha, rating.getFechaRating());
    }

    @Test
    @DisplayName("Setters - debe permitir modificar cada campo individualmente")
    void settersDebenPermitirModificarCampos() {

        LocalDateTime fecha = LocalDateTime.now();

        Rating rating = new Rating();

        rating.setId(2L);
        rating.setIdUsuario(100L);
        rating.setIdContenido(200L);
        rating.setPuntuacion(new BigDecimal("5.0"));
        rating.setFechaRating(fecha);

        assertEquals(2L, rating.getId());
        assertEquals(100L, rating.getIdUsuario());
        assertEquals(200L, rating.getIdContenido());
        assertEquals(new BigDecimal("5.0"), rating.getPuntuacion());
        assertEquals(fecha, rating.getFechaRating());
    }

    @Test
    @DisplayName("equals y hashCode - dos ratings con los mismos datos deben ser iguales")
    void dosRatingsConMismosDatosDebenSerIguales() {

        LocalDateTime fecha = LocalDateTime.now();

        Rating r1 = new Rating(
                1L,
                10L,
                20L,
                new BigDecimal("4.5"),
                fecha
        );

        Rating r2 = new Rating(
                1L,
                10L,
                20L,
                new BigDecimal("4.5"),
                fecha
        );

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    @DisplayName("toString - debe contener el idUsuario")
    void toStringDebeContenerIdUsuario() {

        Rating rating = new Rating(
                1L,
                10L,
                20L,
                new BigDecimal("4.5"),
                LocalDateTime.now()
        );

        String texto = rating.toString();

        assertNotNull(texto);
        assertTrue(texto.contains("10"));
    }

    @Test
    @DisplayName("prePersist - debe asignar fecha automáticamente")
    void prePersistDebeAsignarFecha() {

        Rating rating = new Rating();

        rating.prePersist();

        assertNotNull(rating.getFechaRating());
    }
}