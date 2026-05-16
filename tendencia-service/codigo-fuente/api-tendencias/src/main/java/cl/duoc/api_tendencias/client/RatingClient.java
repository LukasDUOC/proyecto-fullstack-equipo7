package cl.duoc.api_tendencias.client;

import cl.duoc.api_tendencias.dto.RatingDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
        name = "rating-client",
        url = "${ratings.service.url}"
)
public interface RatingClient {

    @GetMapping("/api/ratings")
    List<RatingDTO> getAll();
}