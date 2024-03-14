package com.example.Ecommerce.configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {
    @Value("${server.url}")
    private String url;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(url);
        devServer.setDescription("Server URL");

        Contact contact = new Contact();
        contact.setEmail("E-commerce"); // Email manzilini to'g'ri ko'rsating
        contact.setName("Shahzod");
        contact.setUrl("https://www.bezkoder.com");

        Info info = new Info()
                .title("E-commerce Management API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage tutorials.")
                .termsOfService("https://www.bezkoder.com/terms")
                .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"));

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}

