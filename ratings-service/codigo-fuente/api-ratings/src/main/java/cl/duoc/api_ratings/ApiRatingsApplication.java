package cl.duoc.api_ratings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiRatingsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRatingsApplication.class, args);
	}

}
