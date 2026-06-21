package cl.duoc.api_ratings.controller;


import cl.duoc.api_ratings.dto.RatingCreateDTO;
import cl.duoc.api_ratings.dto.RatingDTO;
import cl.duoc.api_ratings.service.RatingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RatingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RatingService service;

    @InjectMocks
    private RatingController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("GET /ratings debe retornar 200")
    void debeListarRatings() throws Exception {

        // Arrange
        RatingDTO dto = new RatingDTO(
                1L,
                10L,
                20L,
                new BigDecimal("4.5"),
                LocalDateTime.now()
        );

        when(service.findAll()).thenReturn(List.of(dto));

        // Act + Assert
        mockMvc.perform(get("/api/v1/ratings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName("GET /ratings/{id} debe retornar 200")
    void debeBuscarPorId() throws Exception {

        // Arrange
        RatingDTO dto = new RatingDTO(
                1L,
                10L,
                20L,
                new BigDecimal("5.0"),
                LocalDateTime.now()
        );

        when(service.findById(1L)).thenReturn(dto);

        // Act + Assert
        mockMvc.perform(get("/api/v1/ratings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.idUsuario").value(10));
    }

    @Test
    @DisplayName("POST /ratings debe retornar 201")
    void debeCrearRating() throws Exception {

        // Arrange
        RatingCreateDTO request = new RatingCreateDTO();
        request.setIdUsuario(1L);
        request.setIdContenido(2L);
        request.setPuntuacion(new BigDecimal("4.0"));

        RatingDTO response = new RatingDTO(
                1L,
                1L,
                2L,
                new BigDecimal("4.0"),
                LocalDateTime.now()
        );

        when(service.crear(request)).thenReturn(response);

        // Act + Assert
        mockMvc.perform(post("/api/v1/ratings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("PATCH /ratings/{id} debe retornar 200")
    void debeActualizarRating() throws Exception {

        // Arrange
        RatingCreateDTO request = new RatingCreateDTO();
        request.setPuntuacion(new BigDecimal("5.0"));

        RatingDTO response = new RatingDTO(
                1L,
                1L,
                2L,
                new BigDecimal("5.0"),
                LocalDateTime.now()
        );

        when(service.actualizar(1L, request))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(patch("/api/v1/ratings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.puntuacion").value(5.0));
    }

    @Test
    @DisplayName("DELETE /ratings/{id} debe retornar 204")
    void debeEliminarRating() throws Exception {

        // Arrange
        doNothing().when(service).eliminar(1L);

        // Act + Assert
        mockMvc.perform(delete("/api/v1/ratings/1"))
                .andExpect(status().isNoContent());
    }
}