package cl.duoc.api_contenido.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.api_contenido.dto.ContenidoCreateDTO;
import cl.duoc.api_contenido.dto.ContenidoDTO;
import cl.duoc.api_contenido.exception.RecursoNoEncontradoException;
import cl.duoc.api_contenido.model.Contenido;
import cl.duoc.api_contenido.repository.ContenidoRepository;

@Service
public class ContenidoService {

        private static final Logger log = LoggerFactory.getLogger(ContenidoService.class);

        @Autowired
        private ContenidoRepository contenidoRepository;

       
        public ContenidoDTO obtenerPorId(Long id) {

                log.info("Buscando contenido por id={}", id);

                Contenido c = contenidoRepository.findById(id)
                                .orElseThrow(() -> {
                                        log.warn("Contenido no encontrado id={}", id);
                                        return new RecursoNoEncontradoException(
                                                        "No se encontró el contenido con el ID: " + id);
                                });

                log.info("Contenido encontrado id={} titulo={}", c.getId(), c.getTitulo());

                return toDTO(c);
        }

        // CREATE
        public ContenidoDTO crearContenido(ContenidoCreateDTO dto) {

                log.info("Validando duplicidad de título: {}", dto.getTitulo());

               
                if (contenidoRepository.existsByTituloIgnoreCase(dto.getTitulo())) {
                        log.warn("Intento de crear contenido duplicado: {}", dto.getTitulo());
                       
                        throw new RuntimeException("Ya existe un contenido con el título: " + dto.getTitulo());
                }

                log.info("Creando contenido titulo={} tipo={} plataforma={}",
                                dto.getTitulo(), dto.getTipo(), dto.getVisualizar());

                Contenido c = new Contenido();
                c.setTitulo(dto.getTitulo());
                c.setGenero(dto.getGenero());
                c.setSinopsis(dto.getSinopsis());
                c.setDuracion(dto.getDuracion());
                c.setTipo(dto.getTipo());
                c.setVisualizar(dto.getVisualizar());
                c.setFechaLan(dto.getFechaLan());

                Contenido guardado = contenidoRepository.save(c);

                log.info("Contenido creado exitosamente id={}",
                                guardado.getId());

                return toDTO(guardado);
        }

        // ACTUALIZAR

        public ContenidoDTO actualizar(Long id, ContenidoCreateDTO dto) {

                log.info("Actualizando contenido id={}", id);

                Contenido c = contenidoRepository.findById(id)
                                .orElseThrow(() -> {
                                        log.warn("No se puede actualizar, contenido no existe id={}", id);
                                        return new RecursoNoEncontradoException(
                                                        "No se puede actualizar. Contenido no encontrado con ID: "
                                                                        + id);
                                });

                c.setTitulo(dto.getTitulo());
                c.setGenero(dto.getGenero());
                c.setSinopsis(dto.getSinopsis());
                c.setDuracion(dto.getDuracion());
                c.setTipo(dto.getTipo());
                c.setVisualizar(dto.getVisualizar());
                c.setFechaLan(dto.getFechaLan());

                return  toDTO(contenidoRepository.save(c));
        }

     
        public void eliminar (Long id) {

                log.info("Eliminando contenido id={}", id);

                Contenido c = contenidoRepository.findById(id)
                                .orElseThrow(() -> {
                                        log.warn("No se puede eliminar, contenido no existe id={}", id);
                                        return new RecursoNoEncontradoException(
                                                        "No se puede eliminar. Contenido no encontrado con ID: " + id);
                                });

                contenidoRepository.delete(c);

                log.info("Contenido eliminado id={} ", c.getId());
        }

      
        public List<ContenidoDTO> obtenerTodos() {

                log.info("Obteniendo todos los contenidos");

                List<Contenido> lista = contenidoRepository.findAll();

                log.info("Total contenidos encontrados={}", lista.size());

                return lista.stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList());
        }

        public List<ContenidoDTO> buscarPorTitulo(String titulo) {
                log.info("Buscando contenidos que coincidan con: {}", titulo);

                List<Contenido> resultados = contenidoRepository.findByTituloContainingIgnoreCase(titulo);

          
                return resultados.stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList());
        }

        private ContenidoDTO toDTO (Contenido c){
                return new ContenidoDTO (
                        c.getId(),
                        c.getTitulo(),
                        c.getGenero(),
                        c.getSinopsis(),
                        c.getDuracion(),
                        c.getTipo(),
                        c.getVisualizar(),
                        c.getFechaLan()
                        





                );
        }
}