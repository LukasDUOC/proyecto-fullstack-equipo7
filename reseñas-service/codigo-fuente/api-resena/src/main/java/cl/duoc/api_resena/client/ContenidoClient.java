package cl.duoc.api_resena.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.api_resena.dto.ContenidoDTO;

@FeignClient(name = "contenido-client", url = "${contenido.service.url}")
public interface ContenidoClient {
    @GetMapping("/api/v1/contenido/{id}")
    ContenidoDTO getContenido(@PathVariable("id") Long id);
}
