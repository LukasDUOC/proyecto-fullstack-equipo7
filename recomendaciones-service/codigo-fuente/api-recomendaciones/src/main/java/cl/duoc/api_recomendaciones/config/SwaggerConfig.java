package cl.duoc.api_recomendaciones.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API recomendacion Service")
                        .version("1.0")
                        .description("Microservicio de recomendaciones de peliculas/series. "
                                + "Permite crear y consultar recomendaciones del catálogo."));
    }
}