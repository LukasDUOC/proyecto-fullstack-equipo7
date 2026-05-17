package cl.duoc.api_recomendaciones.client;


import cl.duoc.api_recomendaciones.dto.RatingDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "rating-client", url = "${rating.service.url}")
public interface RatingClient {

    @GetMapping("/api/v1/ratings/{id}")
    RatingDTO getRating(@PathVariable("id") Long id);
}