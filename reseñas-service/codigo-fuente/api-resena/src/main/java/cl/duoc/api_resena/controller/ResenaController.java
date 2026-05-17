package cl.duoc.api_resena.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import java.util.List;
import cl.duoc.api_resena.service.ResenaService;
import jakarta.validation.Valid;
import cl.duoc.api_resena.dto.ResenaCreateDTO;
import cl.duoc.api_resena.dto.ResenaDTO;



@RestController
@RequestMapping("/api/v1/resenas")
public class ResenaController {

     @Autowired
    private ResenaService resenaService;


    @GetMapping
    public ResponseEntity<List<ResenaDTO>> getAll() {
        return ResponseEntity.ok(resenaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResenaDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(resenaService.findById(id));
    }


    @GetMapping("/usuario/{usuarioId}")
public ResponseEntity<List<ResenaDTO>> getByUsuario(
        @PathVariable Long usuarioId) {

    return ResponseEntity.ok(
            resenaService.findByUsuario(usuarioId));
}

@GetMapping("/contenido/{contenidoId}")
public ResponseEntity<List<ResenaDTO>> getByContenido(
        @PathVariable Long contenidoId) {

    return ResponseEntity.ok(
            resenaService.findByContenido(contenidoId));
}

    @PostMapping
    public ResponseEntity<ResenaDTO> crear(@Valid @RequestBody ResenaCreateDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resenaService.crear(dto));
    }

 
    @PatchMapping("/{id}")
    public ResponseEntity<ResenaDTO> actualizarPuntuacion(
            @PathVariable Long id,
            @Valid @RequestBody ResenaCreateDTO dto) {

        return ResponseEntity.ok(resenaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        resenaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}