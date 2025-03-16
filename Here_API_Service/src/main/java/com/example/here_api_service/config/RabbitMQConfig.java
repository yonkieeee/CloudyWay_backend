package com.example.here_api_service.config;

import com.example.here_api_service.components.DotEnvComponent;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class RabbitMQConfig {

    private final String rabbitMQURL;

    @Autowired
    public RabbitMQConfig(DotEnvComponent dotEnvComponent) {
        this.rabbitMQURL = dotEnvComponent.getRabbitMQURL();
    }

    @Bean
    public ConnectionFactory connectionFactory() throws URISyntaxException {
        URI rabbitURI = new URI(rabbitMQURL);
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setUri(rabbitURI);
        return factory;
    }
}
