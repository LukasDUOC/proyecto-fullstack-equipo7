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
        private ContenidoRepository _contenidoRepository;

       
        public ContenidoDTO findDtoById(Long id) {

                log.info("Buscando contenido por id={}", id);

                Contenido c = _contenidoRepository.findById(id)
                                .orElseThrow(() -> {
                                        log.warn("Contenido no encontrado id={}", id);
                                        return new RecursoNoEncontradoException(
                                                        "No se encontró el contenido con el ID: " + id);
                                });

                log.info("Contenido encontrado id={} titulo={}", c.getId(), c.getTitulo());

                return new ContenidoDTO(
                                c.getId(),
                                c.getTitulo(),
                                c.getGenero(),
                                c.getSinopsis(),
                                c.getDuracion(),
                                c.getTipo(),
                                c.getVisualizar(),
                                c.getFechaLan());
        }

        // CREATE
        public ContenidoDTO crearContenido(ContenidoCreateDTO dto) {

                log.info("Validando duplicidad de título: {}", dto.getTitulo());

               
                if (_contenidoRepository.existsByTitulo(dto.getTitulo())) {
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

                Contenido guardado = _contenidoRepository.save(c);

                log.info("Contenido creado exitosamente id={} titulo={}",
                                guardado.getId(), guardado.getTitulo());

                return new ContenidoDTO(
                                guardado.getId(),
                                guardado.getTitulo(),
                                guardado.getGenero(),
                                guardado.getSinopsis(),
                                guardado.getDuracion(),
                                guardado.getTipo(),
                                guardado.getVisualizar(),
                                guardado.getFechaLan());
        }

        public ContenidoDTO update(Long id, ContenidoCreateDTO dto) {

                log.info("Actualizando contenido id={}", id);

                Contenido c = _contenidoRepository.findById(id)
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

                Contenido actualizado = _contenidoRepository.save(c);

                log.info("Contenido actualizado id={} titulo={}",
                                actualizado.getId(), actualizado.getTitulo());

                return new ContenidoDTO(
                                actualizado.getId(),
                                actualizado.getTitulo(),
                                actualizado.getGenero(),
                                actualizado.getSinopsis(),
                                actualizado.getDuracion(),
                                actualizado.getTipo(),
                                actualizado.getVisualizar(),
                                actualizado.getFechaLan());
        }

     
        public void delete(Long id) {

                log.info("Eliminando contenido id={}", id);

                Contenido c = _contenidoRepository.findById(id)
                                .orElseThrow(() -> {
                                        log.warn("No se puede eliminar, contenido no existe id={}", id);
                                        return new RecursoNoEncontradoException(
                                                        "No se puede eliminar. Contenido no encontrado con ID: " + id);
                                });

                _contenidoRepository.delete(c);

                log.info("Contenido eliminado id={} titulo={}", c.getId(), c.getTitulo());
        }

      
        public List<ContenidoDTO> findAllDto() {

                log.info("Obteniendo todos los contenidos");

                List<Contenido> lista = _contenidoRepository.findAll();

                log.info("Total contenidos encontrados={}", lista.size());

                return lista.stream().map(c -> new ContenidoDTO(
                                c.getId(),
                                c.getTitulo(),
                                c.getGenero(),
                                c.getSinopsis(),
                                c.getDuracion(),
                                c.getTipo(),
                                c.getVisualizar(),
                                c.getFechaLan())).collect(Collectors.toList());
        }

        public List<ContenidoDTO> buscarPorTitulo(String titulo) {
                log.info("Buscando contenidos que coincidan con: {}", titulo);

                List<Contenido> resultados = _contenidoRepository.findByTituloContainingIgnoreCase(titulo);

          
                return resultados.stream().map(c -> new ContenidoDTO(
                                c.getId(),
                                c.getTitulo(),
                                c.getGenero(),
                                c.getSinopsis(),
                                c.getDuracion(),
                                c.getTipo(),
                                c.getVisualizar(),
                                c.getFechaLan())).collect(Collectors.toList());
        }
}