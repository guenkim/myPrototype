package com.guen.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class SpringdocConfig {

    @Bean
    public OpenAPI baseOpenAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("Backend Project")
                        .version("1.0.0.")
                        .description("기본에 충실하자. 가능한 많은 것들을 다뤄 보자.")
        );
    }
}

