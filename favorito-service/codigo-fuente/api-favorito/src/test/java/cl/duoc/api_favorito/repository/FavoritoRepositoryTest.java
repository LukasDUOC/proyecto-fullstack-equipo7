package cl.duoc.api_favorito.repository;


import cl.duoc.api_favorito.model.Favorito;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FavoritoRepositoryTest {

    @Autowired
    private FavoritoRepository repository;

    @Test
    @DisplayName("save - debe persistir el favorito y asignar un ID generado automáticamente")
    void debePersistirFavoritoYAsignarIdGenerado() {
        // Given
        Favorito favorito = new Favorito(null, 100L, 500L, true);

        // When
        Favorito guardado = repository.save(favorito);

        // Then
        assertNotNull(guardado.getId());
        assertTrue(guardado.getId() > 0);
        assertEquals(100L, guardado.getUsuarioId());
        assertEquals(500L, guardado.getContenidoId());
        assertTrue(guardado.isFavorito());
    }

    @Test
    @DisplayName("findAll - debe retornar todos los favoritos guardados en la BD")
    void debeRetornarTodosLosFavoritosGuardados() {
        // Given
        repository.save(new Favorito(null, 100L, 500L, true));
        repository.save(new Favorito(null, 200L, 600L, false));

        // When
        List<Favorito> favoritos = repository.findAll();

        // Then
        assertNotNull(favoritos);
        assertEquals(2, favoritos.size());
    }

    @Test
    @DisplayName("findById - debe retornar el favorito correcto cuando el ID existe")
    void debeEncontrarFavoritoPorIdExistente() {
        // Given
        Favorito guardado = repository.save(new Favorito(null, 300L, 700L, true));

        // When
        Optional<Favorito> resultado = repository.findById(guardado.getId());

        // Then
        assertTrue(resultado.isPresent());
        assertEquals(300L, resultado.get().getUsuarioId());
        assertEquals(700L, resultado.get().getContenidoId());
        assertTrue(resultado.get().isFavorito());
    }

    @Test
    @DisplayName("findById - debe retornar Optional vacío cuando el ID no existe")
    void debeRetornarOptionalVacioCuandoIdNoExiste() {
        // When
        Optional<Favorito> resultado = repository.findById(999L);

        // Then
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("deleteById - debe eliminar el favorito de la base de datos")
    void debeEliminarFavoritoPorId() {
        // Given
        Favorito guardado = repository.save(new Favorito(null, 400L, 800L, true));
        Long id = guardado.getId();

        // When
        repository.deleteById(id);

        // Then
        assertFalse(repository.findById(id).isPresent());
    }
}