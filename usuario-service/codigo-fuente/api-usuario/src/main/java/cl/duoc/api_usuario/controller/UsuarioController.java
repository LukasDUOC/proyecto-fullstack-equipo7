package cl.duoc.api_usuario.controller;

import cl.duoc.api_usuario.dto.UsuarioCreateDTO;
import cl.duoc.api_usuario.dto.UsuarioDTO;
import cl.duoc.api_usuario.service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAll() {

        List<UsuarioDTO> lista = usuarioService.obtenerTodosLosUsuarios();

        return ResponseEntity.ok(lista);
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<UsuarioDTO> getById(@PathVariable Long id) {

        UsuarioDTO usuario = usuarioService.findById(id);

        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioDTO> getByEmail(@PathVariable String email) {

        UsuarioDTO usuario = usuarioService.findByEmail(email);

        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(
            @Valid @RequestBody UsuarioCreateDTO dto) {

        UsuarioDTO creado = usuarioService.crear(dto);

        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }


    @DeleteMapping("/email/{email:.+}")
    public ResponseEntity<Void> eliminar(@PathVariable String email) {

        usuarioService.eliminarPorEmail(email);

        return ResponseEntity.noContent().build();
    }
}