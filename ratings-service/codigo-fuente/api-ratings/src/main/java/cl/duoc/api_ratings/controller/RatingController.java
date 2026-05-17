package cl.duoc.api_ratings.controller;

import cl.duoc.api_ratings.dto.RatingCreateDTO;
import cl.duoc.api_ratings.dto.RatingDTO;
import cl.duoc.api_ratings.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {

    @Autowired
    private RatingService service;

 
    @GetMapping
    public ResponseEntity<List<RatingDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

  
    @GetMapping("/{id}")
    public ResponseEntity<RatingDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }


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


    @PatchMapping("/{id}")
    public ResponseEntity<RatingDTO> actualizarPuntuacion(
            @PathVariable Long id,
            @RequestBody RatingCreateDTO dto) {

        return ResponseEntity.ok(service.actualizar(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}