package cl.duoc.api_recomendaciones.client;


import cl.duoc.api_recomendaciones.dto.ContenidoDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "contenido-client", url = "${contenido.service.url}")
public interface ContenidoClient {

    @GetMapping("/api/v1/contenido/{id}")
    ContenidoDTO getContenido(@PathVariable("id") Long id);
}
