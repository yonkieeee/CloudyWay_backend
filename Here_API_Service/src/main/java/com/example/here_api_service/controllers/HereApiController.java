package com.example.here_api_service.controllers;

import com.example.here_api_service.schemas.DiscoverRequestDTO;
import com.example.here_api_service.schemas.GeocodeRequestDTO;
import com.example.here_api_service.services.HereApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public String getLocation(@RequestParam String json) {
        try {
            DiscoverRequestDTO request = objectMapper.readValue(json, DiscoverRequestDTO.class);
            return service.getLocationData(request.getLatitude(), request.getLongitude(), request.getQuery());
        } catch (IOException e) {
            e.printStackTrace();
            return "Invalid JSON format";
        }
    }

    @GetMapping("/geocode")
    public String geocodeLocation(@RequestParam String json) {
        try {
            GeocodeRequestDTO request = objectMapper.readValue(json, GeocodeRequestDTO.class);
            return service.geocodeLocationData(request.getHouseNumber(), request.getStreet(), request.getCity(), request.getState(), request.getPostalCode(), request.getCountry());
        } catch (IOException e) {
            e.printStackTrace();
            return "Invalid JSON format";
        }
    }
}
