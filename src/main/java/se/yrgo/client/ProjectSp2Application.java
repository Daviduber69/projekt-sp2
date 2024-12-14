package se.yrgo.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "se.yrgo.data")
@EntityScan(basePackages = "se.yrgo.domain")
@ComponentScan(basePackages = "se.yrgo.rest")
public class ProjectSp2Application {

	public static void main(String[] args) {
		SpringApplication.run(ProjectSp2Application.class, args);
	}

}
