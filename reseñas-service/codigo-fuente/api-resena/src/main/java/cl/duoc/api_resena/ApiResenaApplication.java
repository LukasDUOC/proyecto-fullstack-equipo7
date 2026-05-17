package cl.duoc.api_resena;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiResenaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiResenaApplication.class, args);
	}

}
