package cl.duoc.api_tendencias.controller;

import cl.duoc.api_tendencias.dto.TendenciaDTO;
import cl.duoc.api_tendencias.service.TendenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Tendencias", description = "Endpoints para consultar los contenidos más vistos y populares")
@RestController
@RequestMapping("/api/v1/tendencias")
public class TendenciaController {

    @Autowired
    private TendenciaService service;

    @GetMapping("/top-peliculas")
    @Operation(
        summary = "Obtener top películas",
        description = "Devuelve una lista con las películas que son tendencia actualmente."
    )
    @ApiResponse(responseCode = "200", description = "Lista devuelta exitosamente")
    public ResponseEntity<List<TendenciaDTO>> topPeliculas() {
        return ResponseEntity.ok(service.topPeliculas());
    }

    @GetMapping("/top-series")
    @Operation(
        summary = "Obtener top series",
        description = "Devuelve una lista con las series que son tendencia actualmente."
    )
    @ApiResponse(responseCode = "200", description = "Lista devuelta exitosamente")
    public ResponseEntity<List<TendenciaDTO>> topSeries() {
        return ResponseEntity.ok(service.topSeries());
    }

    @GetMapping("/populares")
    @Operation(
        summary = "Obtener contenidos populares",
        description = "Devuelve un listado mixto de las películas y series más populares."
    )
    @ApiResponse(responseCode = "200", description = "Lista devuelta exitosamente")
    public ResponseEntity<List<TendenciaDTO>> populares() {
        return ResponseEntity.ok(service.populares());
    }
}