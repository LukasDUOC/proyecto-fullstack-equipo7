package cl.duoc.api_busqueda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cl.duoc.api_busqueda.dto.BusquedaCreateDTO;
import cl.duoc.api_busqueda.dto.BusquedaDTO;
import cl.duoc.api_busqueda.dto.ContenidoDTO;
import cl.duoc.api_busqueda.service.BusquedaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Busqueda" , description = "Operaciones de gestión e historial de búsquedas de contenido")
@RestController
@RequestMapping("/api/v1/busqueda")
public class BusquedaController {

    private static final Logger log = LoggerFactory.getLogger(BusquedaController.class);

    @Autowired
    private BusquedaService service;

    // CREAR
    @Operation(summary = "Hacer una busqueda del Contenido")
    @ApiResponses({
    @ApiResponse (responseCode = "200", description = "La busqueda ah si exitosa. "),
    @ApiResponse (responseCode = "400", description = "Datos de entrada NO validos. ")
    })
   
    @PostMapping("/buscar")
    public ResponseEntity<ContenidoDTO> buscar(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del Contenido") 
        @org.springframework.web.bind
        .annotation.RequestBody
        BusquedaCreateDTO dto) {


        ContenidoDTO respuesta = service.buscar(dto);

        log.info("contenidoId={}", respuesta.getId());

        return ResponseEntity.ok(respuesta);
    }

    // TODOS
    @Operation(summary = "Listar todo el historial de busquedas", description = "Retorna la lista completa de los contenidos registrados en el sistema. ")
    @ApiResponse (responseCode = "200", description = "Historial obtenido exitosamente. ")
    
    @GetMapping
    public ResponseEntity<List<BusquedaDTO>> findAll() {

        log.info("  obtener historial de búsquedas");

        return ResponseEntity.ok(service.findAll());
    }

    //POR ID
    @Operation(summary = "Buscar contenido por Id",description = "Retorna un único registro del historial correspondiente al ID solicitado. ")
    @ApiResponses({
    @ApiResponse (responseCode = "200", description = "Contenido encontrado. "),
    @ApiResponse (responseCode = "404", description = "Contenido NO encontrado. ")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BusquedaDTO> obtenerPorId(@Parameter(
        description = "ID del Contenido",
        required = true)@PathVariable Long id) {

        log.info(" buscar historial por id={}", id);

        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @Operation(summary = "Buscar Historial por termino de texto",
        description = "Retorna una lista de registros del historial que coinciden con el término buscado.")
    @ApiResponses({
    @ApiResponse (responseCode = "200", description = "Historial encontrado. "),
    @ApiResponse (responseCode = "404", description = "No se encontraron registros con ese término. ")
    })
    
    @GetMapping("/buscar/{termino}")
    public ResponseEntity<List<BusquedaDTO>> buscarPorTexto(@Parameter(
        description = "Termino para buscar el Historial",
        required = true) @PathVariable String termino) {

        log.info("  buscar en historial por término={}", termino);

        return ResponseEntity.ok(service.buscarPorTexto(termino));

    }
    @Operation(summary = "Eliminar un registro del historial por ID  ")
    @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Eliminación exitosa"),
    @ApiResponse(responseCode = "404", description = "Registro de historial no encontrado")
    }) 
    @DeleteMapping("/historial/{id}")
    public ResponseEntity<Void> eliminarPorId(@Parameter(
        description = "ID para borrar el Historial",
        required = true)@PathVariable Long id) {
        service.eliminarBusquedaPorId(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Eliminar TODO el Historial.  ")
    @ApiResponse(responseCode = "204", description = "Eliminación exitosa")
    
    @DeleteMapping("/historial/limpiar")
    public ResponseEntity<Void> limpiarTodo() {
       service.eliminarTodo();
        return ResponseEntity.noContent().build();
    }


}