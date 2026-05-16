package cl.duoc.api_recomendaciones.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.api_recomendaciones.dto.RecomendacionCreateDTO;
import cl.duoc.api_recomendaciones.dto.RecomendacionDTO;
import cl.duoc.api_recomendaciones.service.RecomendacionService;

import java.util.List;

@RestController
@RequestMapping("/api/recomendaciones")
public class RecomendacionController {

    @Autowired
    private RecomendacionService service;


    @GetMapping
    public ResponseEntity<List<RecomendacionDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<RecomendacionDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }


    @PostMapping
    public ResponseEntity<RecomendacionDTO> crear(
            @Valid @RequestBody RecomendacionCreateDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.crear(dto));
    }
}