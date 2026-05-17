package cl.duoc.api_ratings.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import cl.duoc.api_ratings.dto.ContenidoDTO;

@FeignClient(name = "contenido-client", url = "${contenido.service.url}")
public interface ContenidoClient {

    @GetMapping("/api/v1/contenido/{id}")
    ContenidoDTO obtenerContenido(@PathVariable("id") Long id);
}