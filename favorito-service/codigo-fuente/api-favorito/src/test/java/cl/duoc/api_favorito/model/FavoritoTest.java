package cl.duoc.api_favorito.model;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FavoritoTest {

    @Test
    @DisplayName("Constructor vacío - debe crear una instancia no nula")
    void constructorVacioDebeCrearInstanciaNoNula() {
        Favorito favorito = new Favorito();
        assertNotNull(favorito);
    }

    @Test
    @DisplayName("Constructor completo - debe asignar todos los campos correctamente")
    void constructorCompletoDebeAsignarTodosLosCampos() {
        Favorito favorito = new Favorito(1L, 100L, 500L, true);

        assertEquals(1L, favorito.getId());
        assertEquals(100L, favorito.getUsuarioId());
        assertEquals(500L, favorito.getContenidoId());
        assertTrue(favorito.isFavorito());
    }

    @Test
    @DisplayName("Setters - debe permitir modificar cada campo individualmente")
    void settersDebenPermitirModificarCampos() {
        Favorito favorito = new Favorito();

        favorito.setId(2L);
        favorito.setUsuarioId(200L);
        favorito.setContenidoId(600L);
        favorito.setFavorito(false);

        assertEquals(2L, favorito.getId());
        assertEquals(200L, favorito.getUsuarioId());
        assertEquals(600L, favorito.getContenidoId());
        assertFalse(favorito.isFavorito());
    }

    @Test
    @DisplayName("equals y hashCode - dos favoritos con los mismos datos deben ser iguales")
    void dosFavoritosConMismosDatosDebenSerIguales() {
        Favorito f1 = new Favorito(1L, 100L, 500L, true);
        Favorito f2 = new Favorito(1L, 100L, 500L, true);

        assertEquals(f1, f2);
        assertEquals(f1.hashCode(), f2.hashCode());
    }

    @Test
    @DisplayName("toString - debe contener el ID del usuario en la representación")
    void toStringDebeContenerUsuarioId() {
        Favorito favorito = new Favorito(3L, 300L, 700L, true);

        String texto = favorito.toString();

        assertNotNull(texto);
        assertTrue(texto.contains("usuarioId=300") || texto.contains("300"));
    }
}