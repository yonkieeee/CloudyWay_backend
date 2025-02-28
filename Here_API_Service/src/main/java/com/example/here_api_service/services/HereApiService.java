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

    public String getLocationData(Double latitude, Double longitude, String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String formattedLatLon = String.format(Locale.ENGLISH, "%.6f,%.6f", latitude, longitude);

            String url = String.format("%s?at=%s&q=%s&apiKey=%s",
                    dotEnvComponent.getDiscoverBaseUrl(), formattedLatLon, encodedQuery, HERE_API_KEY);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error retrieving location data";
        }
    }

    public String geocodeLocationData(String houseNumber, String street, String city, String state, String postalCode, String country) {
        try {
            String encodedStreet = URLEncoder.encode(street, StandardCharsets.UTF_8);
            String encodedCountry = URLEncoder.encode(country, StandardCharsets.UTF_8);

            String url = String.format("%s?qq=houseNumber=%s;street=%s;city=%s;state=%s;postalCode=%s;country=%s&apiKey=%s",
                    dotEnvComponent.getGeocodeBaseUrl(), houseNumber, encodedStreet, city, state, postalCode, encodedCountry, HERE_API_KEY);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            rabbitTemplate.convertAndSend("Geocode", "", response.getBody());
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error retrieving geocode data";
        }
    }
}

