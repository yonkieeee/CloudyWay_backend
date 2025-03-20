package org.example.postservice.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotEnvConfig {

    private Dotenv dotenv = Dotenv.configure()
            .load();

    @PostConstruct
    public void init() {
        String accessKey = dotenv.get("S3_ACCESS_KEY");
        String secretKey = dotenv.get("S3_SECRET_KEY");

        if (accessKey != null) {
            System.setProperty("S3_ACCESS_KEY", accessKey);
        } else {
            throw new IllegalArgumentException("S3_ACCESS_KEY not found in .env file");
        }

        if (secretKey != null) {
            System.setProperty("S3_SECRET_KEY", secretKey);
        } else {
            throw new IllegalArgumentException("S3_SECRET_KEY not found in .env file");
        }
    }
}