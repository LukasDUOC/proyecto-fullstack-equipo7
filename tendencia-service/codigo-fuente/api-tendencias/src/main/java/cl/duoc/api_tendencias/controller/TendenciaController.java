package cl.duoc.api_tendencias.controller;

import cl.duoc.api_tendencias.dto.TendenciaDTO;
import cl.duoc.api_tendencias.service.TendenciaService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tendencias")
public class TendenciaController {

    @Autowired
    private TendenciaService service;

    @GetMapping("/top-peliculas")
    public ResponseEntity<List<TendenciaDTO>>
    topPeliculas() {

        return ResponseEntity.ok(
                service.topPeliculas());
    }

    @GetMapping("/top-series")
    public ResponseEntity<List<TendenciaDTO>>
    topSeries() {

        return ResponseEntity.ok(
                service.topSeries());
    }

    @GetMapping("/populares")
    public ResponseEntity<List<TendenciaDTO>>
    populares() {

        return ResponseEntity.ok(
                service.populares());
    }
}