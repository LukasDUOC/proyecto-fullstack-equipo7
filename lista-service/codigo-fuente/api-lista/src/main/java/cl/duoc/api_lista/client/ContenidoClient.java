package cl.duoc.api_lista.client;


import cl.duoc.api_lista.dto.ContenidoDTO;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "contenido-client", url = "${contenido.service.url}")
public interface ContenidoClient {


    @GetMapping("/api/v1/contenido/{id}")
    ContenidoDTO buscarPorId(@PathVariable Long id);

    @GetMapping("/api/v1/contenido")
    List<ContenidoDTO> buscarTodos();

    @GetMapping("/api/v1/contenido/buscar")
    List<ContenidoDTO> buscarPorTitulo(@RequestParam String titulo);





 
}
