package cl.duoc.api_lista;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiListaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiListaApplication.class, args);
	}

}
