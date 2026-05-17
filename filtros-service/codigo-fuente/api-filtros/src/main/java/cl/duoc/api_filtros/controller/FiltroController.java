package cl.duoc.api_filtros.controller;

import cl.duoc.api_filtros.dto.ContenidoDTO;
import cl.duoc.api_filtros.service.FiltroService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/filtros")
public class FiltroController {

    @Autowired
    private FiltroService service;

    @GetMapping
    public ResponseEntity<List<ContenidoDTO>> filtrar(

            @RequestParam(required = false) String genero,

            @RequestParam(required = false) String tipo,

            @RequestParam(required = false) String visualizar) {

        return ResponseEntity.ok(
                service.filtrar(
                        genero,
                        tipo,
                        visualizar));
    }
}