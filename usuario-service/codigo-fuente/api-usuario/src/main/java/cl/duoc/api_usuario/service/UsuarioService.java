package cl.duoc.api_usuario.service;

import java.util.List;
import org.springframework.stereotype.Service;

import cl.duoc.api_usuario.dto.UsuarioCreateDTO;
import cl.duoc.api_usuario.dto.UsuarioDTO;
import cl.duoc.api_usuario.exception.RecursoNoEncontradoException;
import cl.duoc.api_usuario.model.Usuario;
import cl.duoc.api_usuario.repository.UsuarioRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.stream.Collectors;




@Service
public class UsuarioService {
    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        log.info("Consultando todos los usuarios");

        return usuarioRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO findByEmail(String email) {
        log.info("Buscando usuario email={}", email);

        Usuario u = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con email: " + email));

        return toDTO(u);
    }

    public UsuarioDTO findById(Long id) {
        log.info("Buscando usuario id={}", id);

        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + id));

        return toDTO(u);
    }

    public UsuarioDTO crear(UsuarioCreateDTO dto) {
        log.info("Creando usuario: nombre={}, email={}, ingresar={}", 
                dto.getNombre(), 
                dto.getEmail(), 
                dto.isIngresar());

        Usuario u = new Usuario();
        u.setNombre(dto.getNombre());
        u.setEmail(dto.getEmail());
        u.setContrasena(dto.getContrasena());
        u.setIngresar(dto.isIngresar());

        Usuario guardado = usuarioRepository.save(u);

        log.info("Usuario creado id={}", guardado.getId());

        return toDTO(guardado);
    }

    public void eliminarPorEmail(String email) {
        log.info("Eliminando usuario email={}", email);

        Usuario u = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con email: " + email));

        usuarioRepository.delete(u);
    }

    private UsuarioDTO toDTO(Usuario u) {
        return new UsuarioDTO(
                u.getId(),
                u.getNombre(),
                u.getEmail(),
                u.isIngresar()
        );
    }
}