package com.example.here_api_service.services;

import com.example.here_api_service.components.DotEnvComponent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Service
public class HereApiService {

    private final RestTemplate restTemplate;
    private final DotEnvComponent dotEnvComponent;
    private final String HERE_API_KEY;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public HereApiService(RestTemplate restTemplate, DotEnvComponent dotEnvComponent, RabbitTemplate rabbitTemplate) {
        this.restTemplate = restTemplate;
        this.dotEnvComponent = dotEnvComponent;
        this.HERE_API_KEY = dotEnvComponent.getHereApiKey();
        this.rabbitTemplate = rabbitTemplate;
    }

    public ResponseEntity<String> getLocationData(Double latitude, Double longitude, String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String formattedLatLon = String.format(Locale.ENGLISH, "%.6f,%.6f", latitude, longitude);

            String url = String.format("%s?at=%s&q=%s&apiKey=%s",
                    dotEnvComponent.getDiscoverBaseUrl(), formattedLatLon, encodedQuery, HERE_API_KEY);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error getting location data");
        }
    }

    public ResponseEntity<String> discoverPlace(String placeName) {
        try {
            String encodedQuery = URLEncoder.encode(placeName, StandardCharsets.UTF_8);

            String url = String.format("%s?q=%s&in=bbox:%s&apiKey=%s",
                    dotEnvComponent.getDiscoverBaseUrl(), encodedQuery, dotEnvComponent.getUkraineLatAndLng(), HERE_API_KEY);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error retrieving place data");
        }
    }

    public ResponseEntity<String> geocodeLocationData(String houseNumber, String street, String city, String state, String postalCode, String country) {
        try {
            String url = String.format("%s?qq=houseNumber=%s;street=%s;city=%s;state=%s;postalCode=%s;country=%s&apiKey=%s",
                    dotEnvComponent.getGeocodeBaseUrl(), houseNumber, street, city, state, postalCode, country, HERE_API_KEY);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            rabbitTemplate.convertAndSend("Geocode", "", response.getBody());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error getting location data");
        }
    }

    public ResponseEntity<String> geocodePlace(String placeName, String houseNumber, String street, String city) {
        try {
            String query = String.format("%s, %s %s, %s", placeName, houseNumber, street, city);

            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

            String url = String.format("%s?q=%s&apiKey=%s",
                    dotEnvComponent.getGeocodeBaseUrl(), encodedQuery, HERE_API_KEY);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error getting location data");
        }
    }
}

