package cl.duoc.api_resena.service;

import cl.duoc.api_resena.client.ContenidoClient;
import cl.duoc.api_resena.client.UsuarioClient;
import cl.duoc.api_resena.dto.ContenidoDTO;
import cl.duoc.api_resena.dto.ResenaCreateDTO;
import cl.duoc.api_resena.dto.ResenaDTO;
import cl.duoc.api_resena.dto.UsuarioDTO;
import cl.duoc.api_resena.exception.RecursoNoEncontradoException;
import cl.duoc.api_resena.exception.ServicioNoDisponibleException;
import cl.duoc.api_resena.model.Resena;
import cl.duoc.api_resena.repository.ResenaRepository;

import feign.FeignException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResenaService {

    private static final Logger log =
            LoggerFactory.getLogger(ResenaService.class);

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private ContenidoClient contenidoClient;

    @Autowired
    private ResenaRepository repository;

  
    public List<ResenaDTO> findAll() {
        log.info("Consultando todas las reseñas");
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

   
    public ResenaDTO findById(Long id) {

        log.info("Buscando reseña id={}", id);

        Resena resena = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "Reseña no encontrada: " + id));

        return toDTO(resena);
    }

   
    public ResenaDTO crear(ResenaCreateDTO dto) {

        log.info(
                "Creando reseña: usuarioId={}, contenidoId={}",
                dto.getUsuarioId(),
                dto.getContenidoId()
        );

      
        UsuarioDTO usuario;

        try {
            usuario = usuarioClient.getUsuario(dto.getUsuarioId());

        } catch (FeignException.NotFound e) {

            throw new RecursoNoEncontradoException(
                    "Usuario no encontrado: "
                            + dto.getUsuarioId());

        } catch (FeignException e) {

            throw new ServicioNoDisponibleException(
                    "Servicio de usuarios no disponible");
        }

       
        ContenidoDTO contenido;

        try {

            contenido = contenidoClient.getContenido(dto.getContenidoId());

        } catch (FeignException.NotFound e) {

            throw new RecursoNoEncontradoException(
                    "Contenido no encontrado: "
                            + dto.getContenidoId());

        } catch (FeignException e) {

            throw new ServicioNoDisponibleException(
                    "Servicio de contenidos no disponible");
        }

       
        Resena resena = new Resena();

        resena.setUsuarioId(dto.getUsuarioId());
        resena.setContenidoId(dto.getContenidoId());
        resena.setComentario(dto.getComentario());

     
        Resena guardada = repository.save(resena);

        log.info(
                "Reseña creada id={} usuario={} contenido={}",
                guardada.getId(),
                usuario.getNombre(),
                contenido.getTitulo()
        );

        return toDTO(guardada);
    }


    public void eliminar(Long id) {

        log.info("Eliminando reseña id={}", id);

        Resena resena = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "Reseña no encontrada: " + id));

        repository.delete(resena);

        log.info("Reseña eliminada id={}", id);
    }

  
    public ResenaDTO actualizar(
            Long id,
            ResenaCreateDTO dto) {

        log.info("Actualizando reseña id={}", id);

        Resena resena = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "Reseña no encontrada: " + id));

      
        resena.setComentario(dto.getComentario());

  
        Resena actualizada = repository.save(resena);

        log.info(
                "Reseña actualizada id={} nuevoComentario={}",
                actualizada.getId(),
                actualizada.getComentario()
        );

        return toDTO(actualizada);
    }


    private ResenaDTO toDTO(Resena r) {

        return new ResenaDTO(
                r.getId(),
                r.getComentario(),
                r.getUsuarioId(),
                r.getContenidoId(),
                r.getFechaRating()
        );
    }

   public List<ResenaDTO> findByUsuario(Long usuarioId) {

    List<ResenaDTO> resenas = repository
            .findByUsuarioId(usuarioId)
            .stream()
            .map(this::toDTO)
            .toList();

    if (resenas.isEmpty()) {
        throw new RuntimeException(
                "No existen reseñas para este usuario");
    }

    return resenas;
}

public List<ResenaDTO> findByContenido(Long contenidoId) {

    List<ResenaDTO> resenas = repository
            .findByContenidoId(contenidoId)
            .stream()
            .map(this::toDTO)
            .toList();

    if (resenas.isEmpty()) {
        throw new ServicioNoDisponibleException(
                "No existen reseñas para este contenido");
    }

    return resenas;
}
}