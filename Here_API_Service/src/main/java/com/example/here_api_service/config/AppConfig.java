package com.example.here_api_service.config;

import com.example.here_api_service.components.DotEnvComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private final DotEnvComponent dotEnvComponent;

    @Autowired
    public AppConfig(DotEnvComponent dotEnvComponent) {
        this.dotEnvComponent = dotEnvComponent;
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> {
            String serverPort = dotEnvComponent.getServerPort();
            if(serverPort != null) {
                factory.setPort(Integer.parseInt(serverPort));
            }
        };
    }
}
