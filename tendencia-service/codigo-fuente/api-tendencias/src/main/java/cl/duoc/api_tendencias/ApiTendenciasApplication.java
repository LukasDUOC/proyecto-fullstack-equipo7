package cl.duoc.api_tendencias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiTendenciasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiTendenciasApplication.class, args);
	}

}
