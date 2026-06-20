package cl.duoc.api_lista.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.api_lista.client.ContenidoClient;
import cl.duoc.api_lista.client.UsuarioClient;
import cl.duoc.api_lista.dto.ContenidoDTO;
import cl.duoc.api_lista.dto.ListaCreateDTO;
import cl.duoc.api_lista.dto.ListaDTO;
import cl.duoc.api_lista.exception.RecursoNoEncontradoException;
import cl.duoc.api_lista.exception.ServicioNoDisponibleException;
import cl.duoc.api_lista.model.Lista;
import cl.duoc.api_lista.repository.ListaRepository;
import feign.FeignException;

@Service
public class ListaService {

    private static final Logger log = LoggerFactory.getLogger(ListaService.class);

    @Autowired
    private ListaRepository repository;

    @Autowired
    private ContenidoClient contenidoClient;

    @Autowired
    private UsuarioClient usuarioClient;

    public Lista crear(ListaCreateDTO dto) {

        log.info("Creando lista | usuarioId={} | titulo={}",
                dto.getUsuarioId(), dto.getTitulo());

        validarUsuario(dto.getUsuarioId());

        List<Long> contenidos = Optional.ofNullable(dto.getContenidosId())
                .orElseThrow(() -> new IllegalArgumentException("Debe enviar al menos un contenido"));

        if (contenidos.isEmpty()) {
            throw new IllegalArgumentException("Debe enviar al menos un contenido");
        }

        contenidos.forEach(this::validarContenido);

        // 3. Guardar entidad
        Lista lista = new Lista();
        lista.setUsuarioId(dto.getUsuarioId());
        lista.setTitulo(dto.getTitulo());
        lista.setContenidosId(contenidos);

        Lista saved = repository.save(lista);

        log.info("Lista creada correctamente | id={}", saved.getId());

        return saved;
    }

    public List<ListaDTO> obtenerTodos() {
        log.info("Obteniendo todas las listas");
        List<Lista> listas = repository.findAll();
        log.info("Total listas encontradas={}", listas.size());

        return listas.stream()
                .map(lista -> {
                    List<ContenidoDTO> contenidos = Optional.ofNullable(lista.getContenidosId())
                            .orElse(List.of())
                            .stream()
                            .map(this::obtenerContenidoSeguro)
                            .toList();
                    return mapToDTO(lista, contenidos);
                })
                .collect(Collectors.toList());
    }

    public ListaDTO obtenerPorId(Long id) {

        log.info("Buscando lista por id={}", id);

        Lista lista = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Lista no encontrada: " + id));

        List<ContenidoDTO> contenidos = Optional.ofNullable(lista.getContenidosId())
                .orElse(List.of())
                .stream()
                .map(this::obtenerContenidoSeguro)
                .toList();

        return mapToDTO(lista, contenidos);
    }

    public List<ListaDTO> obtenerPorUsuario(Long usuarioId) {

        log.info("Buscando listas por usuarioId={}", usuarioId);

        return repository.findByUsuarioId(usuarioId)
                .stream()
                .map(lista -> {

                    List<ContenidoDTO> contenidos = Optional.ofNullable(lista.getContenidosId())
                            .orElse(List.of())
                            .stream()
                            .map(this::obtenerContenidoSeguro)
                            .toList();

                    return mapToDTO(lista, contenidos);
                })
                .toList();
    }

    public ListaDTO eliminarContenidoDeLista(Long listaId, Long contenidoId) {

        log.info("Eliminando contenidoId={} de listaId={}", contenidoId, listaId);

        Lista lista = repository.findById(listaId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Lista no encontrada: " + listaId));

        if (lista.getContenidosId() == null || lista.getContenidosId().isEmpty()) {
            throw new RecursoNoEncontradoException(
                    "La lista no tiene contenidos");
        }

        boolean eliminado = lista.getContenidosId().remove(contenidoId);

        if (!eliminado) {
            throw new RecursoNoEncontradoException(
                    "El contenido no existe en la lista");
        }

        Lista updated = repository.save(lista);

        List<ContenidoDTO> contenidos = updated.getContenidosId()
                .stream()
                .map(this::obtenerContenidoSeguro)
                .toList();

        return mapToDTO(updated, contenidos);
    }

    public void eliminarListaPorId(Long listaId) {

        log.info("Eliminando lista id={}", listaId);

        Lista lista = repository.findById(listaId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Lista no encontrada: " + listaId));

        repository.delete(lista);

        log.info("Lista eliminada correctamente id={}", listaId);
    }

    public ListaDTO actualizarLista(Long listaId, Lista listaActualizada) {

        log.info("Actualizando lista id={}", listaId);

        Lista lista = repository.findById(listaId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Lista no encontrada: " + listaId));

        if (listaActualizada.getTitulo() != null && !listaActualizada.getTitulo().isBlank()) {
            lista.setTitulo(listaActualizada.getTitulo());
        }

        if (listaActualizada.getContenidosId() != null) {

            if (listaActualizada.getContenidosId().isEmpty()) {
                throw new IllegalArgumentException("La lista debe tener al menos un contenido");
            }

            listaActualizada.getContenidosId()
                    .forEach(this::validarContenido);

            lista.setContenidosId(listaActualizada.getContenidosId());
        }

        Lista updated = repository.save(lista);

        List<ContenidoDTO> contenidos = updated.getContenidosId()
                .stream()
                .map(this::obtenerContenidoSeguro)
                .toList();

        log.info("Lista actualizada correctamente id={}", listaId);

        return mapToDTO(updated, contenidos);
    }

    private void validarContenido(Long contenidoId) {
        try {
            contenidoClient.findById(contenidoId);
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException("Contenido no existe: " + contenidoId);
        } catch (FeignException e) {
            log.error("Error comunicando con api-contenido", e);
            throw new ServicioNoDisponibleException("api-contenido no disponible");
        }
    }

    private ContenidoDTO obtenerContenidoSeguro(Long contenidoId) {
        try {
            return contenidoClient.findById(contenidoId);
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException("Contenido no existe: " + contenidoId);
        } catch (FeignException e) {
            log.error("Error comunicando con api-contenido", e);
            throw new ServicioNoDisponibleException("api-contenido no disponible");
        }
    }

    private void validarUsuario(Long usuarioId) {
        try {
            usuarioClient.getById(usuarioId);
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException("Usuario no existe: " + usuarioId);
        } catch (FeignException e) {
            log.error("Error comunicando con api-usuario", e);
            throw new ServicioNoDisponibleException("api-usuario no disponible");
        }
    }

    private ListaDTO mapToDTO(Lista lista, List<ContenidoDTO> contenidos) {
        return new ListaDTO(
                lista.getId(),
                lista.getUsuarioId(),
                lista.getTitulo(),
                contenidos);
    }
}