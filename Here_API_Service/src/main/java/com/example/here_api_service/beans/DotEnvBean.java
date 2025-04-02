package com.example.here_api_service.beans;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotEnvBean {

    @Bean
    public Dotenv dotenv(){
        return Dotenv.configure()
                .directory("./") // Відповідно налаштуйте шлях
                .filename(".env")
                .load();
    }
}
