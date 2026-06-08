package cl.duoc.api_usuario.controller;

import cl.duoc.api_usuario.dto.UsuarioCreateDTO;
import cl.duoc.api_usuario.dto.UsuarioDTO;
import cl.duoc.api_usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuarios", description = "Creacion de usuarios")
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

     @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Listar usuarios", description = "Lista a todos los usuarios registrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok"),
    })
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAll() {
        return ResponseEntity.ok(usuarioService.obtenerTodosLosUsuarios());
    }

    @Operation(summary = "Buscar usuario", description = "Busca un usario por su id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<UsuarioDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @Operation(summary = "Buscar usario", description = "Busca un usario por su correo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioDTO> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(usuarioService.findByEmail(email));
    }

    @Operation(summary = "Registrar un nuevo usuario")
    @ApiResponse(responseCode = "200", description = "Ok")
    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@Valid @RequestBody UsuarioCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.crear(dto));
    }

    @DeleteMapping("/email/{email:.+}")
    public ResponseEntity<Void> eliminar(@PathVariable String email) {
        usuarioService.eliminarPorEmail(email);
        return ResponseEntity.noContent().build();
    }

}