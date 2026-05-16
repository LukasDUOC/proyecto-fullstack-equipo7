package cl.duoc.api_lista.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.api_lista.dto.ListaCreateDTO;
import cl.duoc.api_lista.dto.ListaDTO;
import cl.duoc.api_lista.model.Lista;
import cl.duoc.api_lista.service.ListaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/listas")
public class ListaController {

    private static final Logger log = LoggerFactory.getLogger(ListaController.class);

    @Autowired
    private ListaService service;


    @PostMapping("/crear")
    public ResponseEntity<Lista> crear(@Valid @RequestBody ListaCreateDTO dto) {

        log.info("Request recibido | usuarioId={} | titulo={}",
                dto.getUsuarioId(), dto.getTitulo());

        Lista respuesta = service.crear(dto);

        log.info("Lista creada | id={}", respuesta.getId());

        return ResponseEntity.ok(respuesta);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<ListaDTO> obtenerPorId(@PathVariable Long id) {

        log.info("Request: obtener lista por id={}", id);

        ListaDTO respuesta = service.obtenerPorId(id);

        log.info("Response enviada | id={}", id);

        return ResponseEntity.ok(respuesta);
    }


    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ListaDTO>> obtenerPorUsuario(@PathVariable Long usuarioId) {

        log.info("Request: listas por usuarioId={}", usuarioId);

        List<ListaDTO> respuesta = service.obtenerPorUsuario(usuarioId);

        log.info("Response enviada | usuarioId={}", usuarioId);

        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Long id) {

        service.eliminarListaPorId(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{listaId}/contenido/{contenidoId}")
    public ResponseEntity<ListaDTO> eliminarContenido(
            @PathVariable Long listaId,
            @PathVariable Long contenidoId) {

        ListaDTO respuesta = service.eliminarContenidoDeLista(listaId, contenidoId);

        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListaDTO> actualizarLista(
            @PathVariable Long id,
            @RequestBody Lista lista) {

        ListaDTO respuesta = service.actualizarLista(id, lista);

        return ResponseEntity.ok(respuesta);
    }

}