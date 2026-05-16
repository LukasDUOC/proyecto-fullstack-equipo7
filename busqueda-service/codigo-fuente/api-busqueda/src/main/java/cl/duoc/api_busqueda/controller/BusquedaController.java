package cl.duoc.api_busqueda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cl.duoc.api_busqueda.dto.BusquedaCreateDTO;
import cl.duoc.api_busqueda.dto.BusquedaDTO;
import cl.duoc.api_busqueda.dto.ContenidoDTO;
import cl.duoc.api_busqueda.service.BusquedaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/busqueda")
public class BusquedaController {

    private static final Logger log = LoggerFactory.getLogger(BusquedaController.class);

    @Autowired
    private BusquedaService service;

 

   
    @PostMapping("/buscar")
    public ResponseEntity<ContenidoDTO> buscar(@RequestBody BusquedaCreateDTO dto) {

        log.info(" Request recibido en controller | contenidoId={} | titulo={}",
                dto.getContenidoId(), dto.getTitulo());

        ContenidoDTO respuesta = service.buscar(dto);

        log.info(" Response enviado al cliente | contenidoId={}", respuesta.getId());

        return ResponseEntity.ok(respuesta);
    }


    
    @GetMapping
    public ResponseEntity<List<BusquedaDTO>> findAll() {

        log.info(" Request: obtener historial de búsquedas");

        return ResponseEntity.ok(service.findAll());
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<BusquedaDTO> obtenerPorId(@PathVariable Long id) {

        log.info(" Request: buscar historial por id={}", id);

        return ResponseEntity.ok(service.obtenerPorId(id));
    }

  
    @GetMapping("/buscar/{termino}")
    public ResponseEntity<List<BusquedaDTO>> buscarPorTexto(@PathVariable String termino) {

        log.info(" Request: buscar en historial por término={}", termino);

        return ResponseEntity.ok(service.buscarPorTexto(termino));

    }

    @DeleteMapping("/historial/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Long id) {
        service.eliminarBusquedaPorId(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/historial/limpiar")
    public ResponseEntity<Void> limpiarTodo() {
       service.eliminarTodo();
        return ResponseEntity.noContent().build();
    }


}