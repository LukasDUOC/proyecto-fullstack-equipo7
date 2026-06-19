package cl.duoc.api_usuario.controller;


import cl.duoc.api_usuario.dto.UsuarioDTO;
import cl.duoc.api_usuario.exception.GlobalExceptionHandler;
import cl.duoc.api_usuario.exception.RecursoNoEncontradoException;
import cl.duoc.api_usuario.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService service;

    @InjectMocks
    private UsuarioController controller;

    private MockMvc mockMvc;

    private final String BASE_URL = "/api/v1/usuarios";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("GET /api/v1/usuarios - debe retornar 200 con la lista de usuarios")
    void debeRetornar200CuandoSePidenUsuarios() throws Exception {
        when(service.obtenerTodosLosUsuarios()).thenReturn(List.of(
            new UsuarioDTO(1L, "Juan Perez", "juan@duoc.cl", true),
            new UsuarioDTO(2L, "Maria Lopez", "maria@duoc.cl", false)
        ));

        mockMvc.perform(get(BASE_URL))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].nombre").value("Juan Perez"))
               .andExpect(jsonPath("$[0].email").value("juan@duoc.cl"));
    }

    @Test
    @DisplayName("GET /api/v1/usuarios - debe retornar 200 con lista vacía cuando no hay registros")
    void debeRetornar200ConListaVacia() throws Exception {
        when(service.obtenerTodosLosUsuarios()).thenReturn(List.of());

        mockMvc.perform(get(BASE_URL))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("GET /api/v1/usuarios/id/{id} - debe retornar 404 cuando el usuario no existe por id")
    void debeRetornar404CuandoUsuarioNoExistePorId() throws Exception {
        when(service.findById(999L))
            .thenThrow(new RecursoNoEncontradoException("Usuario no encontrado con id: 999"));

        mockMvc.perform(get(BASE_URL + "/id/999"))
               .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/v1/usuarios/email/{email} - debe retornar 200 si el email existe")
    void debeRetornar200CuandoEmailExiste() throws Exception {
        when(service.findByEmail("juan@duoc.cl")).thenReturn(
            new UsuarioDTO(1L, "Juan Perez", "juan@duoc.cl", true)
        );

        mockMvc.perform(get(BASE_URL + "/email/juan@duoc.cl"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.email").value("juan@duoc.cl"))
               .andExpect(jsonPath("$.nombre").value("Juan Perez"));
    }

    
    @Test
    @DisplayName("POST /api/v1/usuarios - debe retornar 201 al crear un usuario válido")
    void debeRetornar201AlCrearUsuario() throws Exception {
        String json = """
            {
                "nombre": "Carlos",
                "email": "carlos@duoc.cl",
                "contrasena": "pass123",
                "ingresar": true
            }
            """;
        when(service.crear(any())).thenReturn(
            new UsuarioDTO(3L, "Carlos", "carlos@duoc.cl", true)
        );

        mockMvc.perform(post(BASE_URL)
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(3))
               .andExpect(jsonPath("$.nombre").value("Carlos"));
    }

    
    @Test
    @DisplayName("POST /api/v1/usuarios - debe retornar 400 cuando fallan las validaciones del DTO")
    void debeRetornar400CuandoNombreOEmailSonInvalidos() throws Exception {
        String json = """
            {
                "nombre": "",
                "email": "correoInvalido",
                "contrasena": "123",
                "ingresar": false
            }
            """;

        mockMvc.perform(post(BASE_URL)
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /api/v1/usuarios/email/{email} - debe retornar 204 al eliminar")
    void debeRetornar204AlEliminarUsuario() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/email/juan@duoc.cl"))
               .andExpect(status().isNoContent());

        verify(service, times(1)).eliminarPorEmail("juan@duoc.cl");
    }
}