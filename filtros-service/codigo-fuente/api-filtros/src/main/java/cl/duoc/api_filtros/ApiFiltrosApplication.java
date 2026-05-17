package cl.duoc.api_filtros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiFiltrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiFiltrosApplication.class, args);
	}

}
