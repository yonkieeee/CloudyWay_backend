package com.example.here_api_service.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Service
public class HereApiService {

    private final RestTemplate restTemplate;
    private final String HERE_API_KEY;
    private final RabbitTemplate rabbitTemplate;
    private final String discoverBaseUrl;
    private final String geocodeBaseUrl;
    private final String bbox;

    @Autowired
    public HereApiService(RestTemplate restTemplate, RabbitTemplate rabbitTemplate, @Value("${here_api_key}") String HERE_API_KEY, @Value("${discover_base_url}") String discoverBaseUrl, @Value("${geocode_base_url}") String geocodeBaseUrl, @Value("${bbox}") String bbox) {
        this.restTemplate = restTemplate;
        this.HERE_API_KEY = HERE_API_KEY;
        this.rabbitTemplate = rabbitTemplate;
        this.discoverBaseUrl = discoverBaseUrl;
        this.geocodeBaseUrl = geocodeBaseUrl;
        this.bbox = bbox;
    }

    public ResponseEntity<String> getLocationData(Double latitude, Double longitude, String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String formattedLatLon = String.format(Locale.ENGLISH, "%.6f,%.6f", latitude, longitude);

            String url = String.format("%s?at=%s&q=%s&apiKey=%s",
                    discoverBaseUrl, formattedLatLon, encodedQuery, HERE_API_KEY);

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
                    discoverBaseUrl, encodedQuery, bbox, HERE_API_KEY);
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
                    geocodeBaseUrl, houseNumber, street, city, state, postalCode, country, HERE_API_KEY);
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
                    geocodeBaseUrl, encodedQuery, HERE_API_KEY);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error getting location data");
        }
    }
}

