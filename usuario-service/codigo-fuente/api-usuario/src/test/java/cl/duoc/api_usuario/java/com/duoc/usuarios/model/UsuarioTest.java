package cl.duoc.api_usuario.java.com.duoc.usuarios.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cl.duoc.api_usuario.model.Usuario;

public class UsuarioTest {
    @Test
    @DisplayName("Constructor vacío - debe crear una instancia no nula")
    void constructorVacioDebeCrearInstanciaNoNula() {
        Usuario usuario = new Usuario();
        assertNotNull(usuario);
    }

    @Test
    @DisplayName("Constructor completo - debe asignar todos los campos correctamente")
    void constructorCompletoDebeAsignarTodosLosCampos() {
        Usuario usuario = new Usuario(
            1L, "Juan Pérez", "juan.perez@duocuc.cl", "clave123", true
        );

        assertEquals(1L, usuario.getId());
        assertEquals("Juan Pérez", usuario.getNombre());
        assertEquals("juan.perez@duocuc.cl", usuario.getEmail());
        assertEquals("clave123", usuario.getContrasena());
        assertTrue(usuario.isIngresar());
    }

    @Test
    @DisplayName("Setters - debe permitir modificar cada campo individualmente")
    void settersDebenPermitirModificarCampos() {
        Usuario usuario = new Usuario();

        usuario.setId(2L);
        usuario.setNombre("María López");
        usuario.setEmail("maria.lopez@duocuc.cl");
        usuario.setContrasena("securePass456");
        usuario.setIngresar(false);

        assertEquals(2L, usuario.getId());
        assertEquals("María López", usuario.getNombre());
        assertEquals("maria.lopez@duocuc.cl", usuario.getEmail());
        assertEquals("securePass456", usuario.getContrasena());
        assertFalse(usuario.isIngresar());
    }

    @Test
    @DisplayName("equals y hashCode - dos usuarios con los mismos datos deben ser iguales")
    void dosUsuariosConMismosDatosDebenSerIguales() {
        Usuario u1 = new Usuario(1L, "Juan Pérez", "juan.perez@duocuc.cl", "clave123", true);
        Usuario u2 = new Usuario(1L, "Juan Pérez", "juan.perez@duocuc.cl", "clave123", true);

        assertEquals(u1, u2);
        assertEquals(u1.hashCode(), u2.hashCode());
    }

    @Test
    @DisplayName("toString - debe contener el nombre del usuario en la representación")
    void toStringDebeContenerNombreDelUsuario() {
        Usuario usuario = new Usuario(3L, "Carlos Silva", "carlos.silva@duocuc.cl", "admin789", true);

        String texto = usuario.toString();

        assertNotNull(texto);
        assertTrue(texto.contains("Carlos Silva"));
    }    
}
