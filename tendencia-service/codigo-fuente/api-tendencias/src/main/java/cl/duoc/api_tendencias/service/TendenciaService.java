package cl.duoc.api_tendencias.service;

import cl.duoc.api_tendencias.client.ContenidoClient;
import cl.duoc.api_tendencias.client.RatingClient;
import cl.duoc.api_tendencias.dto.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class TendenciaService {

    @Autowired
    private ContenidoClient contenidoClient;

    @Autowired
    private RatingClient ratingClient;

    
    public List<TendenciaDTO> topPeliculas() {

        return calcularTendencias("Película");
    }

    
    public List<TendenciaDTO> topSeries() {

        return calcularTendencias("Serie");
    }


    public List<TendenciaDTO> populares() {


    List<ContenidoDTO> contenidos =
            contenidoClient.getAll();

    List<RatingDTO> ratings =
            ratingClient.getAll();

    return contenidos.stream()

            
            .filter(c -> ratings.stream()
                    .anyMatch(r ->
                            r.getIdContenido()
                                    .equals(c.getId())))

            .map(c -> {

                List<RatingDTO> ratingsContenido =
                        ratings.stream()
                                .filter(r ->
                                        r.getIdContenido()
                                                .equals(c.getId()))
                                .toList();

                double promedio =
                        ratingsContenido.stream()
                                .map(RatingDTO::getPuntuacion)
                                .mapToDouble(p -> p.doubleValue())
                                .average()
                                .orElse(0);

                return new TendenciaDTO(
                        c.getId(),
                        c.getTitulo(),
                        c.getGenero(),
                        c.getTipo(),
                        BigDecimal.valueOf(promedio)
                                .setScale(1, RoundingMode.HALF_UP),
                        ratingsContenido.size()
                );
            })

            .sorted((a, b) ->
                    b.getCantidadRatings()
                            .compareTo(a.getCantidadRatings()))

            .collect(Collectors.toList());
}

   
    private List<TendenciaDTO> calcularTendencias(String tipo) {

        List<ContenidoDTO> contenidos = contenidoClient.getAll();

        List<RatingDTO> ratings = ratingClient.getAll();

        return contenidos.stream()

                .filter(c -> c.getTipo().equalsIgnoreCase(tipo))

                        
        .filter(c -> ratings.stream()
                .anyMatch(r ->
                        r.getIdContenido()
                                .equals(c.getId())))

                .map(c -> {

                    List<RatingDTO> ratingsContenido = ratings.stream()
                            .filter(r -> r.getIdContenido()
                                    .equals(c.getId()))
                            .toList();

                    double promedio = ratingsContenido.stream()
                            .map(RatingDTO::getPuntuacion)
                            .mapToDouble(p -> p.doubleValue())
                            .average()
                            .orElse(0);

                    return new TendenciaDTO(
                            c.getId(),
                            c.getTitulo(),
                            c.getGenero(),
                            c.getTipo(),
                            BigDecimal.valueOf(promedio)
                                    .setScale(1,
                                            RoundingMode.HALF_UP),
                            ratingsContenido.size());
                })

                .sorted((a, b) -> b.getPromedio()
                        .compareTo(a.getPromedio()))

                .collect(Collectors.toList());
    }
}