package cl.duoc.api_favorito.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.api_favorito.dto.FavoritoCreateDTO;
import cl.duoc.api_favorito.dto.FavoritoDTO;
import cl.duoc.api_favorito.service.FavoritoService;

@Tag(name = "Favoritos", description = "Operaciones de gestión de favoritos")
@RestController
@RequestMapping("/api/v1/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoService service;

    @Operation(summary = "Listar todos los favoritos",
               description = "Retorna la lista completa de elementos marcados como favoritos en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<FavoritoDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

   
    @Operation(summary = "Buscar favorito por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Favorito encontrado"),
        @ApiResponse(responseCode = "404", description = "Favorito no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FavoritoDTO> getById(
            @Parameter(description = "ID único del favorito", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Registrar nuevo favorito")
    @ApiResponse(responseCode = "201", description = "Favorito creado exitosamente")
    @PostMapping
    public ResponseEntity<FavoritoDTO> crear(@Valid @RequestBody FavoritoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.crear(dto));
    }

    
    @Operation(summary = "Actualizar estado de favorito")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estado actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Favorito no encontrado")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<FavoritoDTO> actualizarEstado(
            @Parameter(description = "ID del favorito a modificar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Nuevo estado booleano de favorito", required = true)
            @RequestParam boolean favorito) {

        return ResponseEntity.ok(service.actualizarEstado(id, favorito));
    }
}