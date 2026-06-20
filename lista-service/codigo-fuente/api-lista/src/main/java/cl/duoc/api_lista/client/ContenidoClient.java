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
    ContenidoDTO findById(@PathVariable Long id);

    @GetMapping("/api/v1/contenido")
    List<ContenidoDTO> findAll();

    @GetMapping("/api/v1/contenido/buscar/{titulo")
    List<ContenidoDTO> buscarPorTitulo(@PathVariable String titulo);





 
}
