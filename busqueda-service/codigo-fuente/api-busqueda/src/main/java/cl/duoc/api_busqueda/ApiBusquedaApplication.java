package cl.duoc.api_busqueda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiBusquedaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiBusquedaApplication.class, args);
	}

}
