package cl.duoc.api_usuario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration       // Le dice a Spring que esta clase tiene configuraciones
public class SwaggerConfig {

    @Bean            // Spring ejecuta este método al iniciar y registra el resultado
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API Usuario Service")          // ← cambia por TU servicio
                .version("1.0")
                .description("Registro de usuarios"));
    }
}
