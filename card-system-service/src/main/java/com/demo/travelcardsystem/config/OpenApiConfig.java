package com.demo.travelcardsystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI travelCardOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Al-Naqel Fare Card System API")
                        .description("REST APIs for UAE Smart Travel card registration, recharge, and journey swipe operations.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("UAE Smart Travel Limited")
                                .email("K16410003@gmail.com")));
    }
}
