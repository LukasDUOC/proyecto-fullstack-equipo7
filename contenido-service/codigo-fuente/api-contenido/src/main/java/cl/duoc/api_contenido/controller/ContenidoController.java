package cl.duoc.api_contenido.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import cl.duoc.api_contenido.dto.ContenidoCreateDTO;
import cl.duoc.api_contenido.dto.ContenidoDTO;

import cl.duoc.api_contenido.service.ContenidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Contenido" , description = "Operaciones de gestion de Contenido")
@RestController
@RequestMapping("/api/v1/contenido")

public class ContenidoController {

    @Autowired
    private ContenidoService service;

    // 1. OBTENER TODOS 

    @Operation(summary = "Listar todo los Contenidos",
        description = "Retorna la lista completa de los contenidos registrados en el sistema. ")
    @ApiResponse (responseCode = "200", description = "Lista obtenida exitosamente. ")
    @GetMapping

    public ResponseEntity<List<ContenidoDTO>> findAll() {
        List<ContenidoDTO> lista = service.obtenerTodos();
        return ResponseEntity.ok(lista); 
    }

    // 2. OBTENER POR ID 

    @Operation(summary = "Buscar contenido por Id",
        description = "Retorna una Lista de los contenidos correspondiente al Id solicitado. ")
        @ApiResponses({
        @ApiResponse (responseCode = "200", description = "Contenido encontrado. "),
        @ApiResponse (responseCode = "404", description = "Contenido NO encontrado. ")
        })
    
    @GetMapping("/{id}")
    public ResponseEntity<ContenidoDTO> findById(@Parameter(
        description = "ID del Contenido",
        required = true) @PathVariable Long id   ) 
        {
        ContenidoDTO contenido = service.obtenerPorId(id);
        return ResponseEntity.ok(contenido); 
    }

    // 3. CREAR 

    @Operation(summary = "Registrar nuevo Contenido")
    @ApiResponses({
    @ApiResponse (responseCode = "201", description = "Contenido Creado exitosamente. "),
    @ApiResponse (responseCode = "400", description = "Datos de entrada NO validos. ")
    })
    @PostMapping
    public ResponseEntity<ContenidoDTO> create(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del Contenido") 
        @org.springframework.web.bind
        .annotation.RequestBody
        ContenidoCreateDTO dto) {
        ContenidoDTO nuevoContenido = service.crearContenido(dto);
        return new ResponseEntity<>(nuevoContenido, HttpStatus.CREATED); 
    }

    // 4. ACTUALIZAR 
    @Operation(summary = "Actualizar Contenido existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "404", description = "Contenido no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada NO validos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ContenidoDTO> update( @Parameter(
        description = "ID del Contenido",
        required = true) @PathVariable Long id  ,
        @Valid
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del Contenido") 
        @org.springframework.web.bind
        .annotation.RequestBody
        ContenidoCreateDTO dto) {
        ContenidoDTO contenidoActualizado = service.actualizar(id, dto);
        return ResponseEntity.ok(contenidoActualizado); 
    }

    // 5. ELIMINAR
    @Operation(summary = "Eliminar Contenido")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Eliminación exitosa"),
        @ApiResponse(responseCode = "404", description = "Contenido no encontrada")
    }) 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(
        description = "ID del Contenido",
        required = true) @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build(); 
    }

    //6. Buscar por titulo
    @Operation(summary = "Buscar Contenido por el titulo")
    @ApiResponses({
    @ApiResponse (responseCode = "200", description = "Contenido encontrado. "),
    @ApiResponse (responseCode = "400", description = "Contenido NO encontrado. ")
    })
    @GetMapping("/buscar/{titulo}")
    public ResponseEntity<List<ContenidoDTO>> buscarPorTitulo(@Parameter(
        description = "Titulo del Contenido",
        required = true) @PathVariable String titulo) {
        return ResponseEntity.ok(service.buscarPorTitulo(titulo));
    }





}
