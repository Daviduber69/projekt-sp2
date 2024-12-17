package se.yrgo.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "se.yrgo.data")
@EntityScan(basePackages = "se.yrgo.domain")
@ComponentScan(basePackages = "se.yrgo.rest")
public class ReviewMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewMicroserviceApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
