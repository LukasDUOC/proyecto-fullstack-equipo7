package cl.duoc.api_contenido.config;




import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration       // Le dice a Spring que esta clase tiene configuraciones
public class SwaggerConfig {

    @Bean            // Spring ejecuta este método al iniciar y registra el resultado
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API Contenido Service")          // ← cambia por TU servicio
                .version("1.0")
                .description("Microservicio de Gestión Peliculas/Series" +
                    "Permite crear, consultar, actualizar y eliminar contenido del catálogo."
                ));
    }
}