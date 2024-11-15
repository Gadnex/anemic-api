package net.binarypaper.anemic_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@PropertySource("classpath:META-INF/build-info.properties")
@OpenAPIDefinition(info = @Info(title = "scrum-management-api", description = "${build.description}", version = "${build.version}", contact = @Contact(name = "${build.developer.name}", email = "${build.developer.email}"), license = @License(name = "${build.license.name}", url = "${build.license.url}")))
@EnableRetry
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ModelResolver modelResolver(ObjectMapper objectMapper) {
		return new ModelResolver(objectMapper);
	}

}
