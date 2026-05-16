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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.api_contenido.dto.ContenidoCreateDTO;
import cl.duoc.api_contenido.dto.ContenidoDTO;

import cl.duoc.api_contenido.service.ContenidoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/contenido")

public class ContenidoController {

    @Autowired
    private ContenidoService service;

    // 1. OBTENER TODOS 
    @GetMapping
    public ResponseEntity<List<ContenidoDTO>> findAll() {
        List<ContenidoDTO> lista = service.findAllDto();
        return ResponseEntity.ok(lista); // Devuelve HTTP 200 OK
    }

    // 2. OBTENER POR ID 
    @GetMapping("/{id}")
    public ResponseEntity<ContenidoDTO> findById(@PathVariable Long id) {
        ContenidoDTO contenido = service.findDtoById(id);
        return ResponseEntity.ok(contenido); // Devuelve HTTP 200 OK
    }

    // 3. CREAR 
    @PostMapping
    public ResponseEntity<ContenidoDTO> create(@Valid @RequestBody ContenidoCreateDTO dto) {
        ContenidoDTO nuevoContenido = service.crearContenido(dto);
        return new ResponseEntity<>(nuevoContenido, HttpStatus.CREATED); // Devuelve HTTP 201 CREATED
    }

    // 4. ACTUALIZAR 
    @PutMapping("/{id}")
    public ResponseEntity<ContenidoDTO> update(@PathVariable Long id, @Valid @RequestBody ContenidoCreateDTO dto) {
        ContenidoDTO contenidoActualizado = service.update(id, dto);
        return ResponseEntity.ok(contenidoActualizado); // Devuelve HTTP 200 OK
    }

    // 5. ELIMINAR 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build(); // Devuelve HTTP 204 No Content (sin cuerpo de respuesta)
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ContenidoDTO>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(service.buscarPorTitulo(titulo));
    }


}
