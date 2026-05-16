package cl.duoc.api_recomendaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiRecomendacionesApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiRecomendacionesApplication.class, args);
    }
}
