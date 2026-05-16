package cl.duoc.api_favorito.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.duoc.api_favorito.client.ContenidoClient;
import cl.duoc.api_favorito.dto.FavoritoCreateDTO;
import cl.duoc.api_favorito.dto.FavoritoDTO;
import cl.duoc.api_favorito.exception.RecursoNoEncontradoException;
import cl.duoc.api_favorito.model.Favorito;
import cl.duoc.api_favorito.repository.FavoritoRepository;
import feign.FeignException;

@Service
public class FavoritoService {

  private static final Logger log = LoggerFactory.getLogger(FavoritoService.class);

    @Autowired
    private FavoritoRepository repository;

    @Autowired
    private ContenidoClient contenidoClient;

    @Autowired
    private cl.duoc.api_favorito.client.UsuarioClient usuarioClient;

    public List<FavoritoDTO> findAll() {
        log.info("Consultando todos los favoritos");

        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public FavoritoDTO findById(Long id) {
        log.info("Buscando favorito id={}", id);

        Favorito favorito = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Favorito no encontrado: " + id));

        return toDTO(favorito);
    }

    public FavoritoDTO crear(FavoritoCreateDTO dto) {

        log.info("Creando favorito: usuarioId={}, contenidoId={}, favorito={}",
                dto.getUsuarioId(),
                dto.getContenidoId(),
                dto.isFavorito());

        
        try {
            usuarioClient.getUsuario(dto.getUsuarioId());
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException("Usuario no encontrado: " + dto.getUsuarioId());
        } catch (FeignException e) {
            throw new RecursoNoEncontradoException("Servicio de usuarios no disponible");
        }


        try {
            contenidoClient.obtenerContenido(dto.getContenidoId());
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException("Contenido no encontrado: " + dto.getContenidoId());
        } catch (FeignException e) {
            throw new RecursoNoEncontradoException("Servicio de contenido no disponible");
        }

        Favorito favorito = new Favorito();
        favorito.setUsuarioId(dto.getUsuarioId());
        favorito.setContenidoId(dto.getContenidoId());
        favorito.setFavorito(dto.isFavorito());

        Favorito guardado = repository.save(favorito);

        log.info("Favorito creado id={}", guardado.getId());

        return toDTO(guardado);
    }

    public FavoritoDTO actualizarEstado(Long id, boolean estado) {

        log.info("Actualizando favorito id={} a estado={}", id, estado);

        Favorito favorito = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Favorito no encontrado: " + id));

        favorito.setFavorito(estado);

        Favorito guardado = repository.save(favorito);

        return toDTO(guardado);
    }

    private FavoritoDTO toDTO(Favorito f) {
        return new FavoritoDTO(
                f.getId(),
                f.getUsuarioId(),
                f.getContenidoId(),
                f.isFavorito()
        );
    }

}
