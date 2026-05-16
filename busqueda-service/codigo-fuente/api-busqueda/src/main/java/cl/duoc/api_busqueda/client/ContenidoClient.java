package cl.duoc.api_busqueda.client;

import cl.duoc.api_busqueda.dto.ContenidoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "contenido-client", url = "${contenido.service.url}")
public interface ContenidoClient {

    @GetMapping("/api/v1/contenido/{id}")
    ContenidoDTO buscarPorId(@PathVariable Long id);

    @GetMapping("/api/v1/contenido")
    List<ContenidoDTO> buscarTodos();

    @GetMapping("/api/v1/contenido/buscar")
    List<ContenidoDTO> buscarPorTitulo(@RequestParam("titulo") String titulo);
    
}