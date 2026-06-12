package cl.duoc.api_busqueda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.api_busqueda.client.ContenidoClient;
import cl.duoc.api_busqueda.dto.BusquedaCreateDTO;
import cl.duoc.api_busqueda.dto.BusquedaDTO;
import cl.duoc.api_busqueda.dto.ContenidoDTO;
import cl.duoc.api_busqueda.exception.RecursoNoEncontradoException;
import cl.duoc.api_busqueda.exception.ServicioNoDisponibleException;
import cl.duoc.api_busqueda.model.Busqueda;
import cl.duoc.api_busqueda.repository.BusquedaRepository;
import feign.FeignException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BusquedaService {

    private static final Logger log = LoggerFactory.getLogger(BusquedaService.class);

    @Autowired
    private BusquedaRepository repository;

    @Autowired
    private ContenidoClient contenidoClient;

    public ContenidoDTO buscar(BusquedaCreateDTO dto) {
        log.info("Inicio búsqueda | contenidoId={} | titulo={}", dto.getContenidoId(), dto.getTitulo());

        ContenidoDTO contenido = null;

        try {
            
            if (dto.getContenidoId() != null && dto.getContenidoId() > 0) {
                log.info("Consultando a api-contenido por ID: {}", dto.getContenidoId());
                contenido = contenidoClient.buscarPorId(dto.getContenidoId());
            }
          
            else if (dto.getTitulo() != null && !dto.getTitulo().isBlank()) {
                log.info("Consultando a api-contenido por Título: {}", dto.getTitulo());

                
                List<ContenidoDTO> resultados = contenidoClient.buscarPorTitulo(dto.getTitulo());

                if (resultados == null || resultados.isEmpty()) {
                    throw new RecursoNoEncontradoException(
                            "No se encontró contenido con el título: " + dto.getTitulo());
                }

                
                contenido = resultados.get(0);
            } else {
                throw new IllegalArgumentException("Debes enviar al menos el ID o el Título");
            }

            // GUARDAR EN HISTORIAL 
            Busqueda b = new Busqueda();
            b.setContenidoId(contenido.getId());
            b.setTitulo(contenido.getTitulo());
            repository.save(b);

            log.info("Búsqueda registrada y retornando contenido completo: {}", contenido.getTitulo());

            
            return contenido;

        } catch (FeignException.NotFound e) {
            log.error("Error 404: El contenido no existe en la API externa");
            throw new RecursoNoEncontradoException("Contenido no encontrado");
        } catch (FeignException e) {
            log.error("Error 503: Falla de comunicación ");
            throw new ServicioNoDisponibleException("api-contenido no disponible");
        }
    }

    public List<BusquedaDTO> findAll() {

        log.info(" Consultando historial de búsquedas");

        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public BusquedaDTO obtenerPorId(Long id) {

        log.info(" Buscando historial por id={}", id);

        Busqueda busqueda = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn(" Búsqueda no encontrada id={}", id);
                    return new RecursoNoEncontradoException("Búsqueda no encontrada: " + id);
                });

        return toDTO(busqueda);
    }

    public List<BusquedaDTO> buscarPorTexto(String termino) {

        log.info(" Buscando en historial por término={}", termino);

        return repository.findByTituloContainingIgnoreCase(termino)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private BusquedaDTO toDTO(Busqueda b) {
        return new BusquedaDTO(
                 b.getId(),
                 b.getContenidoId(), 
                 b.getTitulo());
    }

    public void eliminarBusquedaPorId(Long id) {
        log.info("Eliminando registro de historial id={}", id);
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("No se encontró el registro con ID: " + id);
        }
        repository.deleteById(id);
    }

   
    public void eliminarTodo() {
        log.info("Eliminando todo el historial de búsquedas");
        repository.deleteAll();
    }

}
