package cl.duoc.api_contenido.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.springframework.http.MediaType; 

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import cl.duoc.api_contenido.dto.ContenidoDTO;
import cl.duoc.api_contenido.exception.GlobalExceptionHandler;
import cl.duoc.api_contenido.exception.RecursoNoEncontradoException;
import cl.duoc.api_contenido.service.ContenidoService;

@ExtendWith(MockitoExtension.class)

class ContenidoControllerTest {

    @Mock
    private ContenidoService service;

    @InjectMocks
    private ContenidoController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("GET /api/v1/contenido - Debe retornar 200 OK con la lista de contenidos")
    void debeRetornar200CuandoSePidenContenidos() throws Exception {
        // Given
        when(service.obtenerTodos()).thenReturn(List.of(
                new ContenidoDTO(
                        1L,
                        "Pinocho de Guillermo del Toro",
                        "Animación",
                        "Una reinvención musical en stop-motion del clásico cuento de madera, ambientada en la Italia fascista de los años 30",
                        "117 minutos",
                        "pelicula",
                        "Netflix",
                        LocalDate.parse("2022-12-09"))));

        // When & Then
        mockMvc.perform(get("/api/v1/contenido"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Pinocho de Guillermo del Toro"))
                .andExpect(jsonPath("$[0].genero").value("Animación"));
    }

    @Test
    @DisplayName("GET  - debe retornar 200 con lista vacía cuando no hay registros")
    void debeRetornar200ConListaVacia() throws Exception {
        // Given
        when(service.obtenerTodos()).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get("/api/v1/contenido"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("GET POR ID - debe retornar 200 cuando el contenido existe")
    void debeRetornar200CuandoElContenidoExiste()throws Exception{
        // Given
       
        ContenidoDTO contenidoEsperado = new ContenidoDTO(
                8L,
                "Cars",
                "Animacion",
                "Sinopsis..",
                "148 minutos",
                "pelicula",
                "Disney",
                LocalDate.parse("2010-07-28")
        );

        when(service.obtenerPorId(8L)).thenReturn(contenidoEsperado);

        // When & Then
        mockMvc.perform(get("/api/v1/contenido/8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(8L))
                .andExpect(jsonPath("$.titulo").value("Cars"))
                .andExpect(jsonPath("$.genero").value("Animacion"));


    }
    @Test
    @DisplayName("GET por ID - debe retornar 404 cuando el contenido no existe")
    void debeRetornar404CuandoContenidoNoExiste() throws Exception {
        // Given
        when(service.obtenerPorId(999L))
                .thenThrow(new RecursoNoEncontradoException("Contenido no encontrado: 999"));

        // When & Then
        mockMvc.perform(get("/api/v1/contenido/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Contenido no encontrado: 999"));
    }

    @Test
    @DisplayName("POST  - debe retornar 201 al crear un contenido válido")
    void debeRetornar201AlCrearContenido() throws Exception {
        // Given
        String json = """
                {

                "titulo":"Inception",
                "genero":"Ciencia Ficción",
                "sinopsis":"Un ladrón que roba secretos corporativos a través del uso de la tecnología de compartir sueños recibe la tarea inversa de plantar una idea en la mente de un CEO",
                "duracion":"148 minutos",
                "tipo":"pelicula",
                "visualizar":"HBO Max",
                "fechaLan":"2010-07-28"

                }
                """;
        when(service.crearContenido(any())).thenReturn(
                new ContenidoDTO(7L,
                        "Inception",
                        "Ciencia Ficción",
                        "Un ladrón que roba secretos corporativos a través del uso de la tecnología de compartir sueños recibe la tarea inversa de plantar una idea en la mente de un CEO",
                        "148 minutos",
                        "pelicula",
                        "HBO Max",
                        LocalDate.parse("2010-07-28")));

        // When & Then
        mockMvc.perform(post("/api/v1/contenido")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.titulo").value("Inception"));
    }


    @Test
    @DisplayName("POST - debe retornar 400 cuando el titulo está en blanco")
    void debeRetornar400CuandoTituloEstaVacio() throws Exception {
        // Given — titulo vacío 
        String json = """
            {
                    "titulo": "",
                    "genero": "Ciencia Ficción",
                    "sinopsis": "Un programador es seleccionado...",
                    "duracion": "108 minutos",
                    "tipo": "pelicula",
                    "visualizar": "Netflix",
                    "fechaLan": "2015-04-16"
            }
            """;

        // When & Then
        mockMvc.perform(post("/api/v1/contenido")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isBadRequest());
    }

     @Test
    @DisplayName("PUT /api/v1/contenido/{id} - debe retornar 200 al actualizar un contenido existente")
    void debeRetornar200AlActualizarContenido() throws Exception {
        // Given
        String json = """
                {
                    "titulo": "Inception Actualizado",
                    "genero": "Ciencia Ficción",
                    "sinopsis": "Sinopsis nueva...",
                    "duracion": "148 minutos",
                    "tipo": "pelicula",
                    "visualizar": "Netflix",
                    "fechaLan": "2010-07-28"
                }
                """;

        ContenidoDTO dtoActualizado = new ContenidoDTO(
                1L,
                "Inception Actualizado",
                "Ciencia Ficción",
                "Sinopsis nueva...",
                "148 minutos",
                "pelicula",
                "Netflix",
                LocalDate.parse("2010-07-28")
        );

        when(service.actualizar(eq(1L), any())).thenReturn(dtoActualizado);

        // When & Then
        mockMvc.perform(put("/api/v1/contenido/" + 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Inception Actualizado"));
    }

     @Test
    @DisplayName("DELETE /api/v1/contenido/{id} - debe retornar 24 No Content al eliminar un contenido existente")
    void debeRetornar24AlEliminarContenido() throws Exception {
        // Given
        Long idExistente = 1L;
        doNothing().when(service).eliminar(idExistente);

        // When & Then
        mockMvc.perform(delete("/api/v1/contenido/" + idExistente))
                .andExpect(status().isNoContent());
    }

}
