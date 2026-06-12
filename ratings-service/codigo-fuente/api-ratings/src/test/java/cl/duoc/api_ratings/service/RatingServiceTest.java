package cl.duoc.api_ratings.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.api_ratings.dto.RatingCreateDTO;
import cl.duoc.api_ratings.dto.RatingDTO;
import cl.duoc.api_ratings.model.Rating;
import cl.duoc.api_ratings.repository.RatingRepository;
import java.util.List;
import java.util.stream.Collectors;
import cl.duoc.api_ratings.exception.RecursoNoEncontradoException;
import cl.duoc.api_ratings.exception.ServicioNoDisponibleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import cl.duoc.api_ratings.client.UsuarioClient;
import cl.duoc.api_ratings.client.ContenidoClient;

@Service
public class RatingServiceTest{

    private static final Logger log = LoggerFactory.getLogger(RatingService.class);

    

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private ContenidoClient contenidoClient;

    @Autowired
    private RatingRepository repository;

     public List<RatingDTO> findAll() {
        log.info("Consultando todos los ratings");
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

   
    public RatingDTO findById(Long id) {
        Rating r = repository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Rating no encontrado"));

        return toDTO(r);
    }


    public RatingDTO crear(RatingCreateDTO dto) {
        log.info("Creando rating: usuarioId={}, contenidoId={}", dto.getIdUsuario(), dto.getIdContenido());

       
        try {
            usuarioClient.getUsuario(dto.getIdUsuario());
        } catch (Exception e) {
            throw new ServicioNoDisponibleException("Servicio de usuario no disponible");
        }

     
        try {
            contenidoClient.obtenerContenido(dto.getIdContenido());
        } catch (Exception e) {
            throw new ServicioNoDisponibleException("Servicio de contenido no disponible");
        }

          if (repository.existsByIdUsuarioAndIdContenido(
        dto.getIdUsuario(),
        dto.getIdContenido())) {

    throw new RuntimeException(
            "El usuario ya calificó este contenido");
}

        Rating r = new Rating();
        r.setIdUsuario(dto.getIdUsuario());
        r.setIdContenido(dto.getIdContenido());
        r.setPuntuacion(dto.getPuntuacion());

        Rating guardado = repository.save(r);
        
        return toDTO(guardado);
    }

    
    private RatingDTO toDTO(Rating r) {
        return new RatingDTO(
            r.getId(),
            r.getIdUsuario(),
            r.getIdContenido(),
            r.getPuntuacion(),
            r.getFechaRating()
        );
    }

    public RatingDTO actualizar(Long id, RatingCreateDTO dto) {
    Rating r = repository.findById(id)
        .orElseThrow(() -> new RecursoNoEncontradoException("Rating no encontrado"));

    r.setPuntuacion(dto.getPuntuacion());

    Rating actualizado = repository.save(r);

    return toDTO(actualizado);
}

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("Rating no encontrado");
        }
        repository.deleteById(id);
    }
}