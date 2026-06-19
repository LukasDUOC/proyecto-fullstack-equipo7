package cl.duoc.api_usuario.repository;
import cl.duoc.api_usuario.model.Usuario;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repository;

    

    @Test
    @DisplayName("save - debe persistir el usuario y asignar un ID generado automáticamente")
    void debePersistirUsuarioYAsignarIdGenerado() {
        // Given 
        Usuario usuario = new Usuario(null, "Juan Perez", "juan.perez@duoc.cl", "password123", true);

        // When
        Usuario guardado = repository.save(usuario);

        // Then
        assertNotNull(guardado.getId());
        assertTrue(guardado.getId() > 0);
        assertEquals("Juan Perez", guardado.getNombre());
        assertEquals("juan.perez@duoc.cl", guardado.getEmail());
        assertEquals("password123", guardado.getContrasena()); // O el nombre del getter de tu contraseña
        assertTrue(guardado.isIngresar());
    }

  

    @Test
    @DisplayName("existsByEmail - debe retornar true si el email existe en la BD")
    void debeRetornarTrueSiElEmailExiste() {
        // Given
        repository.save(new Usuario(null, "Maria Lopez", "maria.lopez@duoc.cl", "password123", false));

        // When
        boolean existe = repository.existsByEmail("maria.lopez@duoc.cl");

        // Then
        assertTrue(existe);
    }

    @Test
    @DisplayName("existsByEmail - debe retornar false si el email no existe en la BD")
    void debeRetornarFalseSiElEmailNoExiste() {
        // When
        boolean existe = repository.existsByEmail("inexistente@duoc.cl");

        // Then
        assertFalse(existe);
    }

    @Test
    @DisplayName("findByEmail - debe retornar el usuario correcto si el email existe")
    void debeEncontrarUsuarioPorEmailExistente() {
        // Given
        repository.save(new Usuario(null, "Carlos Ruiz", "carlos.ruiz@duoc.cl", "password123", true));

        // When
        Optional<Usuario> resultado = repository.findByEmail("carlos.ruiz@duoc.cl");

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("Carlos Ruiz", resultado.get().getNombre());
    }

    @Test
    @DisplayName("findByEmail - debe retornar Optional vacío si el email no existe")
    void debeRetornarOptionalVacioCuandoEmailNoExiste() {
        // When
        Optional<Usuario> resultado = repository.findByEmail("no.existe@duoc.cl");

        // Then
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("deleteByEmail - debe eliminar el usuario de la base de datos usando su email")
    void debeEliminarUsuarioPorEmail() {
        // Given
        repository.save(new Usuario(null, "Diego Soto", "diego.soto@duoc.cl", "password123", true));

        // When
        repository.deleteByEmail("diego.soto@duoc.cl");

        // Then
        Optional<Usuario> resultado = repository.findByEmail("diego.soto@duoc.cl");
        assertFalse(resultado.isPresent());
    }
}