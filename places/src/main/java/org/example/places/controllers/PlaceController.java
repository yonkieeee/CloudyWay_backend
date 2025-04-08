package org.example.places.controllers;

import org.example.places.dto.PlaceDTO;
import org.example.places.models.PlaceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.places.services.PlaceService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/places")
@CrossOrigin(origins = "*")
public class PlaceController {

    private final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    public ResponseEntity<List<PlaceDTO>> getPlace(@RequestParam String placeName) {
        try {
            List<PlaceModel> places = placeService.getPlaceByPlaceName(placeName);
            List<PlaceDTO> response = places.stream()
                    .map(placeModel -> new PlaceDTO(placeModel.getPlaceName(),
                            placeModel.getCity(),
                            placeModel.getCounty(),
                            placeModel.getStreet(),
                            placeModel.getHouseNumber(),
                            placeModel.getCoordinates()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{city}")
    public ResponseEntity<List<PlaceDTO>> getCities(@PathVariable String city) {
        try {
            List<PlaceModel> places = placeService.getAllPlacesByCity(city);
            List<PlaceDTO> response = places.stream()
                    .map(placeModel -> new PlaceDTO(placeModel.getPlaceName(),
                            placeModel.getCity(),
                            placeModel.getCounty(),
                            placeModel.getStreet(),
                            placeModel.getHouseNumber(),
                            placeModel.getCoordinates()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/addPlace")
    public ResponseEntity<?> addPlace(@RequestBody PlaceModel place) {
        try {
            placeService.save(place);
            return ResponseEntity.ok("Place added successfully");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}