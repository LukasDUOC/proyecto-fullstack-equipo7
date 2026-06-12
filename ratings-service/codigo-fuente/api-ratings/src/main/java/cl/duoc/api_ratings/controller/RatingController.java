package cl.duoc.api_ratings.controller;

import cl.duoc.api_ratings.dto.RatingCreateDTO;
import cl.duoc.api_ratings.dto.RatingDTO;
import cl.duoc.api_ratings.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.List;

@Tag(name = "Calificaciones", description = "Operaciones de gestión de calificaciones")
@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {

    @Autowired
    private RatingService service;

    // ── GET /api/v1/ratings ──────────────────────────
    @Operation(summary = "Listar todas las calificaciones",
               description = "Retorna la lista completa de calificaciones registradas en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<RatingDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    // ── GET /api/v1/ratings/{id} ─────────────────────
     @Operation(summary = "Buscar rating por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rating encontrada"),
        @ApiResponse(responseCode = "404", description = "Rating no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RatingDTO> getById(
        @Parameter(description = "ID único del rating", required = true)
        @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

 // ── POST /api/v1/ratings ─────────────────────────
    @Operation(summary = "Registrar nuevo rating",
               description = "Crea una nueva calificación en el sistema con los datos proporcionados.")
    @ApiResponse(responseCode = "201", description = "Rating creada exitosamente")
    @PostMapping
public ResponseEntity<?> crear(
        @Valid @RequestBody RatingCreateDTO dto) {

    try {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.crear(dto));

    } catch (RuntimeException e) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
}

// ── PATCH /api/v1/ratings/{id} ─────────────────────
    @Operation(summary = "Actualizar rating existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "404", description = "Rating no encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<RatingDTO> actualizarPuntuacion(
            @Parameter(description = "ID del rating a actualizar", required = true)
            @PathVariable Long id,
            @RequestBody RatingCreateDTO dto) {

        return ResponseEntity.ok(service.actualizar(id, dto));
    }



    // ── DELETE /api/v1/ratings/{id} ──────────────────
    @Operation(summary = "Eliminar rating por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eliminación exitosa"),
        @ApiResponse(responseCode = "404", description = "Rating no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
        @Parameter(description = "ID del rating a eliminar")
        @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}