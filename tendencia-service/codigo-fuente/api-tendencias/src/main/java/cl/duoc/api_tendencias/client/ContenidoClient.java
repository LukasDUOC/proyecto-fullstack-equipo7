package cl.duoc.api_tendencias.client;

import cl.duoc.api_tendencias.dto.ContenidoDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
        name = "contenido-client",
        url = "${contenido.service.url}"
)
public interface ContenidoClient {

    @GetMapping("/api/v1/contenido")
    List<ContenidoDTO> getAll();
}