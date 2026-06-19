package cl.duoc.api_usuario.service;

import cl.duoc.api_usuario.dto.UsuarioCreateDTO;
import cl.duoc.api_usuario.dto.UsuarioDTO;
import cl.duoc.api_usuario.exception.RecursoNoEncontradoException;
import cl.duoc.api_usuario.model.Usuario;
import cl.duoc.api_usuario.repository.UsuarioRepository;

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
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

   

    @Test
    @DisplayName("obtenerTodosLosUsuarios - debe retornar lista de usuarios cuando existen registros")
    void debeRetornarListaDeUsuarios() {
        // Given 
        List<Usuario> usuariosSimulados = List.of(
            new Usuario(1L, "Juan Perez", "juan@duoc.cl", "pass123", true),
            new Usuario(2L, "Maria Lopez", "maria@duoc.cl", "pass456", false)
        );
        when(usuarioRepository.findAll()).thenReturn(usuariosSimulados);

        // When
        List<UsuarioDTO> resultado = usuarioService.obtenerTodosLosUsuarios();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Juan Perez", resultado.get(0).getNombre());
        assertTrue(resultado.get(0).isIngresar());
        verify(usuarioRepository, times(1)).findAll();
    }

    

    @Test
    @DisplayName("findById - debe retornar el DTO correcto cuando el usuario existe")
    void debeRetornarUsuarioPorId() {
        // Given 
        Usuario usuario = new Usuario(1L, "Juan Perez", "juan@duoc.cl", "pass123", true);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        // When
        UsuarioDTO resultado = usuarioService.findById(1L);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Juan Perez", resultado.getNombre());
    }

    @Test
    @DisplayName("findById - debe lanzar RecursoNoEncontradoException cuando el ID no existe")
    void debeLanzarExcepcionCuandoUsuarioNoExistePorId() {
        // Given
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () ->
            usuarioService.findById(999L)
        );
    }

    

    @Test
    @DisplayName("findByEmail - debe retornar el DTO correcto cuando el email existe")
    void debeRetornarUsuarioPorEmail() {
        // Given 
        Usuario usuario = new Usuario(1L, "Juan Perez", "juan@duoc.cl", "pass123", true);
        when(usuarioRepository.findByEmail("juan@duoc.cl")).thenReturn(Optional.of(usuario));

        // When
        UsuarioDTO resultado = usuarioService.findByEmail("juan@duoc.cl");

        // Then
        assertNotNull(resultado);
        assertEquals("juan@duoc.cl", resultado.getEmail());
    }

    

    
    @Test
    @DisplayName("crear - debe persistir y retornar el usuario con ID generado")
    void debeCrearUsuarioCorrectamente() {
        // Given
        UsuarioCreateDTO dto = new UsuarioCreateDTO();
        dto.setNombre("Carlos");
        dto.setEmail("carlos@duoc.cl");
        dto.setContrasena("pass123"); 
        dto.setIngresar(true);

        Usuario guardado = new Usuario(3L, "Carlos", "carlos@duoc.cl", "pass123", true);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(guardado);

        // When
        UsuarioDTO resultado = usuarioService.crear(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
        assertEquals("Carlos", resultado.getNombre());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }


    @Test
    @DisplayName("eliminarPorEmail - debe eliminar el usuario si el email existe")
    void debeEliminarUsuarioExistentePorEmail() {
        // Given 
        Usuario usuario = new Usuario(1L, "Juan Perez", "juan@duoc.cl", "pass123", true);
        when(usuarioRepository.findByEmail("juan@duoc.cl")).thenReturn(Optional.of(usuario));

        // When
        usuarioService.eliminarPorEmail("juan@duoc.cl");

        // Then
        verify(usuarioRepository, times(1)).delete(usuario);
    }
}