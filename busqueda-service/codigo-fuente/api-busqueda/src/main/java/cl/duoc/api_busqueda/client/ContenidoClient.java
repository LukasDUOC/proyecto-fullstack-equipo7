package cl.duoc.api_busqueda.client;

import cl.duoc.api_busqueda.dto.ContenidoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "contenido-client", url = "${contenido.service.url}")
public interface ContenidoClient {

    @GetMapping("/api/v1/contenido/{id}")
    ContenidoDTO findById(@PathVariable Long id);

    @GetMapping("/api/v1/contenido")
    List<ContenidoDTO> findAll();

    @GetMapping("/api/v1/contenido/buscar/{titulo}")
    List<ContenidoDTO> buscarPorTitulo(@PathVariable String titulo);
    
}