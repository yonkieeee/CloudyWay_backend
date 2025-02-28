package com.example.here_api_service.components;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DotEnvComponent {

    private final Dotenv dotenv;

    @Autowired
    public DotEnvComponent(Dotenv dotenv) {
        this.dotenv = dotenv;
    }

    public String getServerPort(){
        return dotenv.get("SERVER_PORT");
    }

    public String getSpringApplicationName(){
        return dotenv.get("SPRING_APPLICATION_NAME");
    }

    public String getHereApiKey(){
        return dotenv.get("HERE_API_KEY");
    }

    public String getDiscoverBaseUrl(){
        return dotenv.get("DISCOVER_BASE_URL");
    }

    public String getGeocodeBaseUrl(){
        return dotenv.get("GEOCODE_BASE_URL");
    }

    public String getRabbitMQURL(){
        return dotenv.get("RABBITMQ_URL");
    }
}
