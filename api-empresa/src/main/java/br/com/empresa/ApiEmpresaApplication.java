package br.com.empresa;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(
		info = @Info(
				title = "Gestão Help Desk",
				version = "1.0",
				description = "Aplicativo para gestão de help desk.",
				contact = @Contact(name = "Carlos Roberto ribeiro Santos Junior", email = "crrsj1@gmail.com")
		)
)
public class ApiEmpresaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiEmpresaApplication.class, args);
	}

}
