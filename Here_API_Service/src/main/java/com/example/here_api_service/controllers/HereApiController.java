package com.example.here_api_service.controllers;

import com.example.here_api_service.schemas.DiscoverPlaceRequestDTO;
import com.example.here_api_service.schemas.DiscoverRequestDTO;
import com.example.here_api_service.schemas.GeocodePlaceRequestDTO;
import com.example.here_api_service.schemas.GeocodeRequestDTO;
import com.example.here_api_service.services.HereApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/locations")
@CrossOrigin(origins = "*")
public class HereApiController {

    private final HereApiService service;
    private final ObjectMapper objectMapper;

    public HereApiController(HereApiService service) {
        this.service = service;
        this.objectMapper = new ObjectMapper();
    }

    @GetMapping
    public ResponseEntity<String> getLocation(@RequestParam String json) {
        try {
            DiscoverRequestDTO request = objectMapper.readValue(json, DiscoverRequestDTO.class);
            return service.getLocationData(request.getLatitude(), request.getLongitude(), request.getQuery());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Invalid JSON format");
        }
    }

    @GetMapping("/discover")
    public ResponseEntity<String> discoverPlace(@RequestParam String json) {
        try {
            DiscoverPlaceRequestDTO request = objectMapper.readValue(json, DiscoverPlaceRequestDTO.class);
            return service.discoverPlace(request.getPlaceName());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Invalid JSON format");
        }
    }

    @GetMapping("/geocode")
    public ResponseEntity<String> geocodeLocation(@RequestParam String json) {
        try {
            GeocodeRequestDTO request = objectMapper.readValue(json, GeocodeRequestDTO.class);
            return service.geocodeLocationData(request.getHouseNumber(), request.getStreet(), request.getCity(), request.getState(), request.getPostalCode(), request.getCountry());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Invalid JSON format");
        }
    }
    @GetMapping("/geocode/places")
    public ResponseEntity<String> geocodePlace(@RequestParam String json) {
        try {
            GeocodePlaceRequestDTO request = objectMapper.readValue(json, GeocodePlaceRequestDTO.class);
            return service.geocodePlace(request.getPlaceName(), request.getHouseNumber(), request.getStreet(), request.getCity());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Invalid JSON format");
        }
    }
}
