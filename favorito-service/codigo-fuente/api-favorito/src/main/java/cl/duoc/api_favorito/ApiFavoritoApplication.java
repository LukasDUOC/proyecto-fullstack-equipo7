package cl.duoc.api_favorito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "cl.duoc.api_favorito.client")
public class ApiFavoritoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiFavoritoApplication.class, args);
	}

}
