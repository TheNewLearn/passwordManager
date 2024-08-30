package com.example.passwordmanager.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {
    @Bean
    public OpenAPI docket(){
        return new OpenAPI()
                .info(apiInfo());
    }

    private Info apiInfo() {
        Contact contact = new Contact();
        contact.name("Fung");
        contact.email("cfwong00875@gmail.com");
        contact.url("https://github.com/TheNewLearn");
        return new Info()
                .title("密碼管理器")
                .description("Spring Boot + React Js")
                .contact(contact)
                .version("v1.0")
                ;

    }
}
