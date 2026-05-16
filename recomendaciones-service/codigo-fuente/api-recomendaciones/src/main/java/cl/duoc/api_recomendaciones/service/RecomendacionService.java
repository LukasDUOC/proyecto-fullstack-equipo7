package cl.duoc.api_recomendaciones.service;

import cl.duoc.api_recomendaciones.client.ContenidoClient;
import cl.duoc.api_recomendaciones.client.RatingClient;
import cl.duoc.api_recomendaciones.dto.ContenidoDTO;
import cl.duoc.api_recomendaciones.dto.RatingDTO;
import cl.duoc.api_recomendaciones.dto.RecomendacionDTO;
import cl.duoc.api_recomendaciones.dto.RecomendacionCreateDTO;
import cl.duoc.api_recomendaciones.exception.RecursoNoEncontradoException;
import cl.duoc.api_recomendaciones.exception.ServicioNoDisponibleException;
import cl.duoc.api_recomendaciones.model.Recomendacion;
import cl.duoc.api_recomendaciones.repository.RecomendacionRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class RecomendacionService {
    
    private static final Logger log = LoggerFactory.getLogger(RecomendacionService.class);

    @Autowired
    private RecomendacionRepository repository;

    @Autowired
    private RatingClient ratingClient;

    @Autowired
    private ContenidoClient contenidoClient;


    public List<RecomendacionDTO> findAll() {

        log.info("Consultando recomendaciones");

        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    public RecomendacionDTO findById(Long id) {

        log.info("Buscando recomendacion id={}", id);

        Recomendacion recomendacion = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "Recomendacion no encontrada: " + id));

        return toDTO(recomendacion);
    }


    public RecomendacionDTO crear(RecomendacionCreateDTO dto) {

        log.info("Generando recomendacion ratingId={}",
                dto.getRatingId());


        RatingDTO rating;

        try {

            log.info("Consultando api-ratings id={}",
                    dto.getRatingId());

            rating = ratingClient.getRating(
                    dto.getRatingId());

            log.info("Puntuacion recibida={}",
                    rating.getPuntuacion());

        } catch (FeignException.NotFound e) {

            throw new RecursoNoEncontradoException(
                    "Rating no encontrado");

        } catch (FeignException e) {

            log.error("Error api-ratings: {}", e.getMessage());

            throw new ServicioNoDisponibleException(
                    "Servicio ratings no disponible");
        }


        ContenidoDTO contenido;

        try {

            log.info("Consultando api-contenido id={}",
                    dto.getContenidoId());

            contenido = contenidoClient.getContenido(
                    dto.getContenidoId());

            log.info("Genero recibido={}",
                    contenido.getGenero());

        } catch (FeignException.NotFound e) {

            throw new RecursoNoEncontradoException(
                    "Contenido no encontrado");

        } catch (FeignException e) {

            log.error("Error api-contenido: {}", e.getMessage());

            throw new ServicioNoDisponibleException(
                    "Servicio contenido no disponible");
        }



        String mensaje;

        if (rating.getPuntuacion() < 2.5) {

            mensaje = "No podemos recomendarte una película porque tu puntuación es menor a 2.5";

        } else {

            mensaje = "Te recomendamos este contenido porque te gusta el género "
                    + contenido.getGenero();
        }



        Recomendacion recomendacion = new Recomendacion();

        recomendacion.setUsuarioId(dto.getUsuarioId());
        recomendacion.setContenidoId(dto.getContenidoId());
        recomendacion.setRatingId(dto.getRatingId());

        recomendacion.setGenero(contenido.getGenero());
        recomendacion.setPuntuacion(rating.getPuntuacion());
        recomendacion.setMensaje(mensaje);


        Recomendacion guardada =
                repository.save(recomendacion);

        log.info("Recomendacion creada id={}",
                guardada.getId());

        return toDTO(guardada);
    }



    private RecomendacionDTO toDTO(Recomendacion r) {

        RecomendacionDTO dto =
                new RecomendacionDTO();

        dto.setId(r.getId());
        dto.setUsuarioId(r.getUsuarioId());
        dto.setContenidoId(r.getContenidoId());

        dto.setGenero(r.getGenero());
        dto.setPuntuacion(r.getPuntuacion());
        dto.setMensaje(r.getMensaje());

        return dto;
    }
}
