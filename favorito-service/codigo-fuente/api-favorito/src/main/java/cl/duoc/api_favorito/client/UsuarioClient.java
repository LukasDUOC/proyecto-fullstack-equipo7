package cl.duoc.api_favorito.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import cl.duoc.api_favorito.dto.UsuarioDTO;

@FeignClient(name = "usuarios-client", url = "${usuarios.service.url}")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/id/{id}")
    UsuarioDTO getUsuario(@PathVariable("id") Long id);
}
