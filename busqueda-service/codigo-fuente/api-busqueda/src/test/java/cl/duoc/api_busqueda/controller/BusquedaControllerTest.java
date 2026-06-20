package cl.duoc.api_busqueda.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post; 
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete; 
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

import cl.duoc.api_busqueda.client.ContenidoClient;
import cl.duoc.api_busqueda.dto.BusquedaCreateDTO;
import cl.duoc.api_busqueda.dto.BusquedaDTO;
import cl.duoc.api_busqueda.dto.ContenidoDTO;
import cl.duoc.api_busqueda.exception.GlobalExceptionHandler;
import cl.duoc.api_busqueda.service.BusquedaService;


@ExtendWith(MockitoExtension.class)

public class BusquedaControllerTest {
    @Mock
    private BusquedaService service;

    @InjectMocks
    private BusquedaController controller;

    @Mock
    private ContenidoClient contenidoClient; 

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }
    @Test
    @DisplayName("POST /buscar - debe retornar 200 y el contenido cuando la búsqueda se realiza por ID")
    void debeRetornar200AlBuscarContenidoPorId() throws Exception {
        // GIVEN
        String json = """
                {
                    "contenidoId": 8
                }
                """;

     
        ContenidoDTO contenidoSimulado = new ContenidoDTO(
                8L, 
                "Cars", 
                "Animacion", 
                "Sinopsis..", 
                "148 minutos", 
                "pelicula", 
                "Disney", 
                LocalDate.parse("2010-07-28")
        );

        when(service.buscar(any(BusquedaCreateDTO.class))).thenReturn(contenidoSimulado);

        // WHEN & THEN
        mockMvc.perform(post("/api/v1/busqueda/buscar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(8))
                .andExpect(jsonPath("$.titulo").value("Cars"))
                .andExpect(jsonPath("$.genero").value("Animacion"));
    }
    @Test
    @DisplayName("POST /buscar - debe retornar 200 y el contenido cuando la búsqueda es exitosa")
    void debeRetornar200AlBuscarContenido() throws Exception {
        // GIVEN
        String json = """
                {
                    "titulo": "Cars"
                }
                """;

       
        ContenidoDTO contenidoSimulado = new ContenidoDTO(
                8L, "Cars", "Animacion", "Sinopsis..", "148 minutos", "pelicula", "Disney", LocalDate.parse("2010-07-28")
        );

        when(service.buscar(any(BusquedaCreateDTO.class))).thenReturn(contenidoSimulado);

        // WHEN & THEN
        mockMvc.perform(post("/api/v1/busqueda/buscar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk()) // Corregido a 200 OK (según tu ResponseEntity.ok)
                .andExpect(jsonPath("$.id").value(8))
                .andExpect(jsonPath("$.titulo").value("Cars"))
                .andExpect(jsonPath("$.genero").value("Animacion"));
    }

     @Test
    @DisplayName("GET / - debe retornar 200 y la lista completa del historial")
    void debeRetornarHistorialCompleto() throws Exception {
        // GIVEN
        List<BusquedaDTO> historialSimulado = List.of(
                new BusquedaDTO(1L, 101L, "Cars"),
                new BusquedaDTO(2L, 102L, "Ex Machina")
        );
        when(service.findAll()).thenReturn(historialSimulado);

        // WHEN & THEN
        mockMvc.perform(get("/api/v1/busqueda")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].titulo").value("Cars"));
    }

    @Test
    @DisplayName("GET /{id} - debe retornar un registro específico del historial")
    void debeRetornarHistorialPorId() throws Exception {
        // GIVEN
        BusquedaDTO registroSimulado = new BusquedaDTO(1L, 101L, "Cars");
        when(service.obtenerPorId(1L)).thenReturn(registroSimulado);

        // WHEN & THEN
        mockMvc.perform(get("/api/v1/busqueda/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.contenidoId").value(101))
                .andExpect(jsonPath("$.titulo").value("Cars"));
    }

    @Test
    @DisplayName("DELETE /historial/{id} - debe retornar 204 al eliminar un registro por id")
    void debeEliminarRegistroPorId() throws Exception {
        // GIVEN
        doNothing().when(service).eliminarBusquedaPorId(1L);

        // WHEN & THEN
        mockMvc.perform(delete("/api/v1/busqueda/historial/1"))
                .andExpect(status().isNoContent()); 

        verify(service, times(1)).eliminarBusquedaPorId(1L);
    }

    @Test
    @DisplayName("DELETE /historial/limpiar - debe retornar 204 al limpiar todo el historial")
    void debeLimpiarTodoElHistorial() throws Exception {
        // GIVEN
        doNothing().when(service).eliminarTodo();

        // WHEN & THEN
        mockMvc.perform(delete("/api/v1/busqueda/historial/limpiar"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).eliminarTodo();
    }

    
}
