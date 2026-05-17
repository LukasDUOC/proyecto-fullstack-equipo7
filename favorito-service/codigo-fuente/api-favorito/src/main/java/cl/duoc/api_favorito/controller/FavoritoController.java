package cl.duoc.api_favorito.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.api_favorito.dto.FavoritoCreateDTO;
import cl.duoc.api_favorito.dto.FavoritoDTO;
import cl.duoc.api_favorito.service.FavoritoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/favoritos")
public class FavoritoController {

     @Autowired
    private FavoritoService service;

    @GetMapping
    public ResponseEntity<List<FavoritoDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoritoDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<FavoritoDTO> crear(@Valid @RequestBody FavoritoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.crear(dto));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<FavoritoDTO> actualizarEstado(
            @PathVariable Long id,
            @RequestParam boolean favorito) {

        return ResponseEntity.ok(service.actualizarEstado(id, favorito));
    }

}