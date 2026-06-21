package cl.duoc.api_favorito.controller;


import cl.duoc.api_favorito.dto.FavoritoCreateDTO;
import cl.duoc.api_favorito.dto.FavoritoDTO;
import cl.duoc.api_favorito.exception.GlobalExceptionHandler;
import cl.duoc.api_favorito.exception.RecursoNoEncontradoException;
import cl.duoc.api_favorito.service.FavoritoService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class FavoritoControllerTest {

    @Mock
    private FavoritoService service;

    @InjectMocks
    private FavoritoController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }


    @Test
    @DisplayName("GET /api/v1/favoritos - debe retornar 200 con la lista de favoritos")
    void debeRetornar200CuandoSePidenFavoritos() throws Exception {
        // Given
        when(service.findAll()).thenReturn(List.of(
            new FavoritoDTO(1L, 100L, 500L, true),
            new FavoritoDTO(2L, 200L, 600L, false)
        ));

        // When & Then
        mockMvc.perform(get("/api/v1/favoritos"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].usuarioId").value(100))
               .andExpect(jsonPath("$[0].favorito").value(true));
    }

    @Test
    @DisplayName("GET /api/v1/favoritos - debe retornar 200 con lista vacía cuando no hay registros")
    void debeRetornar200ConListaVacia() throws Exception {
        // Given
        when(service.findAll()).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get("/api/v1/favoritos"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(0));
    }


    @Test
    @DisplayName("GET /api/v1/favoritos/{id} - debe retornar 404 cuando el favorito no existe")
    void debeRetornar404CuandoFavoritoNoExiste() throws Exception {
        // Given
        when(service.findById(999L))
            .thenThrow(new RecursoNoEncontradoException("Favorito no encontrado: 999"));

        // When & Then
        mockMvc.perform(get("/api/v1/favoritos/999"))
               .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("POST /api/v1/favoritos - debe retornar 201 al crear un favorito válido")
    void debeRetornar201AlCrearFavorito() throws Exception {
        // Given
        String json = """
            {
                "usuarioId": 100,
                "contenidoId": 500,
                "favorito": true
            }
            """;
        when(service.crear(any(FavoritoCreateDTO.class))).thenReturn(
            new FavoritoDTO(3L, 100L, 500L, true)
        );

        // When & Then
        mockMvc.perform(post("/api/v1/favoritos")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(3))
               .andExpect(jsonPath("$.usuarioId").value(100));
    }


    @Test
    @DisplayName("PATCH /api/v1/favoritos/{id}/estado - debe retornar 200 al actualizar estado correctamente")
    void debeRetornar200AlActualizarEstado() throws Exception {
        // Given
        when(service.actualizarEstado(eq(1L), eq(false))).thenReturn(
            new FavoritoDTO(1L, 100L, 500L, false)
        );

        // When & Then
        mockMvc.perform(patch("/api/v1/favoritos/1/estado")
               .param("favorito", "false"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.favorito").value(false));
    }
}