package com.github.flaviobarbosa.Todo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Todo API",
        description = "SpringBoot Todo API",
        contact = @Contact(
            name = "Flavio Barbosa",
            url = "https://github.com/flaviobarbosa",
            email = "flaviombarbosa@gmail.com"
        )
    )
)
public class TodoApplication {

  public static void main(String[] args) {
    SpringApplication.run(TodoApplication.class, args);
  }

}
