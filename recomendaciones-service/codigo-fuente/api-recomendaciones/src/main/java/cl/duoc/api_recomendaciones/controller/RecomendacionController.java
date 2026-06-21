package cl.duoc.api_recomendaciones.controller;


import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.api_recomendaciones.dto.RecomendacionCreateDTO;
import cl.duoc.api_recomendaciones.dto.RecomendacionDTO;
import cl.duoc.api_recomendaciones.service.RecomendacionService;

import java.util.List;

@Tag(name = "Recomendaciones", description = "Operaciones de gestión de recomendaciones")
@RestController
@RequestMapping("/api/v1/recomendaciones")
public class RecomendacionController {

    @Autowired
    private RecomendacionService service;

    @Operation(
        summary = "Listar todas las recomendaciones",
        description = "Retorna la lista completa de sugerencias generadas en el sistema de recomendaciones."
    )
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<RecomendacionDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(
        summary = "Buscar recomendación por ID",
        description = "Obtiene los detalles específicos de una recomendación registrada usando su identificador único."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Recomendación encontrada"),
        @ApiResponse(responseCode = "404", description = "Recomendación no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RecomendacionDTO> getById(
            @Parameter(description = "ID único de la recomendación", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(
        summary = "Registrar nueva recomendación",
        description = "Crea un nuevo registro de recomendación en la base de datos tras validar los campos obligatorios."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Recomendación creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Estructura del JSON o parámetros inválidos")
    })
    @PostMapping
    public ResponseEntity<RecomendacionDTO> crear(
            @Valid @RequestBody RecomendacionCreateDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.crear(dto));
    }
}