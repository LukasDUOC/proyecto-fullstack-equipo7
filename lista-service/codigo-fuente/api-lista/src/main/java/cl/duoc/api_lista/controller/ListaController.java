package cl.duoc.api_lista.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.api_lista.dto.ListaCreateDTO;
import cl.duoc.api_lista.dto.ListaDTO;
import cl.duoc.api_lista.model.Lista;
import cl.duoc.api_lista.service.ListaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Listas", description = "Gestión de listas del usuario.")
@RestController
@RequestMapping("/api/v1/listas")
public class ListaController {

    private static final Logger log = LoggerFactory.getLogger(ListaController.class);

    @Autowired
    private ListaService service;

    @Operation(summary = "Registrar una nueva Lista")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Lista creada exitosamente."),
        @ApiResponse(responseCode = "400", description = "Datos de entrada NO válidos."),
        @ApiResponse(responseCode = "404", description = "Usuario o contenidos no encontrados."),
        @ApiResponse(responseCode = "503", description = "Servicio externo no disponible.") 
    })
    @PostMapping("/crear")
    public ResponseEntity<ListaDTO> crear( 
        @Valid 
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos para la creación de la lista") 
        @org.springframework.web.bind.annotation.RequestBody ListaCreateDTO dto) { 

        log.info("Request recibido | usuarioId={} | titulo={}", dto.getUsuarioId(), dto.getTitulo());
        
      
        Lista nuevaLista = service.crear(dto);

        ListaDTO respuesta = service.obtenerPorId(nuevaLista.getId());
        
        log.info("Lista creada y mapeada a DTO | id={}", respuesta.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @Operation(summary = "Buscar Lista por Id", description = "Retorna el DTO de la lista correspondiente al Id solicitado junto con sus contenidos.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista encontrada."),
        @ApiResponse(responseCode = "404", description = "Lista NO encontrada.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ListaDTO> obtenerPorId(@PathVariable Long id) {
        log.info("Request: obtener lista por id={}", id);
        ListaDTO respuesta = service.obtenerPorId(id);
        log.info("Response enviada | id={}", id);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Buscar Listas por Id de Usuario", description = "Retorna todas las listas que pertenecen a un usuario específico.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listas obtenidas exitosamente.")
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ListaDTO>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        log.info("Request: listas por usuarioId={}", usuarioId); 
        List<ListaDTO> respuesta = service.obtenerPorUsuario(usuarioId);
        log.info("Response enviada | usuarioId={}", usuarioId); 
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Obtener todas las listas", description = "Retorna el listado global de todas las listas registradas en el sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado global obtenido exitosamente.")
    })
    @GetMapping
    public ResponseEntity<List<ListaDTO>> obtenerTodas() {
        log.info("Request: obtener todas las listas"); 
        List<ListaDTO> listas = service.obtenerTodos();
        return ResponseEntity.ok(listas);
    }

    @Operation(summary = "Actualizar una Lista por Id", description = "Permite modificar el título o los contenidos de una lista existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista actualizada correctamente."),
        @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos."),
        @ApiResponse(responseCode = "404", description = "Lista o contenidos no encontrados.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ListaDTO> actualizarLista(
            @PathVariable Long id,
            @RequestBody ListaCreateDTO dto) { 
        
        log.info("Request: actualizar lista id={}", id); 
        
        
        Lista listaEntity = new Lista();
        listaEntity.setTitulo(dto.getTitulo());
        listaEntity.setContenidosId(dto.getContenidosId());
        
        ListaDTO respuesta = service.actualizarLista(id, listaEntity);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Eliminar un contenido de la lista", description = "Remueve un contenido específico de los arreglos de IDs de una lista.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Contenido eliminado de la lista exitosamente."),
        @ApiResponse(responseCode = "404", description = "Lista o contenido no encontrado en la lista.")
    })
    @DeleteMapping("/{listaId}/contenido/{contenidoId}")
    public ResponseEntity<ListaDTO> eliminarContenido(
            @PathVariable Long listaId,
            @PathVariable Long contenidoId) {
        log.info("Request: eliminar contenidoId={} de listaId={}", contenidoId, listaId); 
        ListaDTO respuesta = service.eliminarContenidoDeLista(listaId, contenidoId);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Eliminar una Lista completa", description = "Elimina permanentemente la lista correspondiente al Id provisto.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Lista eliminada exitosamente (No Content)."),
        @ApiResponse(responseCode = "404", description = "Lista NO encontrada.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Long id) {
        log.info("Request: eliminar lista completa id={}", id); 
        service.eliminarListaPorId(id);
        return ResponseEntity.noContent().build();
    }
}
