package com.gamedistribution.gamedistribution.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração global para habilitar o CORS (Cross-Origin Resource Sharing).
 * Essencial para permitir que o frontend (rodando em porta diferente ou como arquivo local)
 * acesse os endpoints da API REST.
 */
@Configuration
public class CorsConfig {

    /**
     * Define as regras de CORS.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Permite acesso de qualquer origem ("*") para todos os endpoints ("/**")
                // e todos os métodos (GET, POST, PUT, DELETE, etc.).
                // EM PRODUÇÃO, substitua "*" pelo domínio exato do seu frontend.
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }
}