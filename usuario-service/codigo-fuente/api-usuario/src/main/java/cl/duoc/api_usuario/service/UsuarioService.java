package cl.duoc.api_usuario.service;

import java.util.List;
import org.springframework.stereotype.Service;

import cl.duoc.api_usuario.dto.UsuarioCreateDTO;
import cl.duoc.api_usuario.dto.UsuarioDTO;
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
        log.info("Obteniendo todos los usuarios");
        return usuarioRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO findByEmail(String email) {
        log.info("Buscando usuario por email: {}", email);
        Usuario u = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
        log.info("Usuario encontrado: {}", u.getEmail());
        return toDTO(u);
    }

    public UsuarioDTO findById(Long id) {
        log.info("Buscando usuario por id: {}", id);
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        log.info("Usuario encontrado: {}", u.getEmail());
        return toDTO(u);
    }

    public UsuarioDTO crear(UsuarioCreateDTO dto) {
        log.info("Guardando usuario: {}", dto.getEmail());
        Usuario u = new Usuario();
        u.setNombre(dto.getNombre());
        u.setEmail(dto.getEmail());
        u.setIngresar(dto.isIngresar());
        Usuario guardado = usuarioRepository.save(u);
        log.info("Usuario guardado exitosamente: {}", guardado.getId());
        return toDTO(guardado);
    }

public void eliminarPorEmail(String email) {
    try {
        Usuario u = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("No existe: " + email));
        usuarioRepository.delete(u);
    } catch (Exception e) {
        
        System.out.println("--- ERROR DETECTADO ---");
        System.out.println(e.getMessage());
        e.printStackTrace(); 
        throw e;
    }
}


    private UsuarioDTO toDTO(Usuario u) {
        return new UsuarioDTO(
                u.getId(),
                u.getNombre(),
                u.getEmail(),
                u.isIngresar());
    }

}
