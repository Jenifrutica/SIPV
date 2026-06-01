package com.tienda.sipv.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuracion de la documentacion de la API (Swagger UI).
 * Define el titulo y la version que se muestran en /swagger-ui.html.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI sipvOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("SIPV - Sistema de Inventario y Punto de Venta")
                .description("API REST para una tienda de comics y mangas")
                .version("1.0.0"));
    }
}
