package com.hungnv28.core.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                version = "1.0.0",
                title = "Stock Api",
                description = "Api for Stock App"),
        servers = {
                @Server(url = "http://localhost:9099", description = "Local Development Server"),
        }
)
@SecurityScheme(
        name = "Authorization",
        scheme = "Bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER,
        type = SecuritySchemeType.HTTP
)
@Configuration
public class SwaggerConfig {
}
