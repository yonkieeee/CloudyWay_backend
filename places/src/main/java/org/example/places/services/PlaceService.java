package org.example.places.services;

import org.example.places.models.PlaceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.places.repositories.PlaceRepository;

import java.util.List;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    @Autowired
    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public List<PlaceModel> getPlaceByPlaceName(String placeName) {
        return placeRepository.findByPlaceName(placeName);
    }

    public List<PlaceModel> getAllPlacesByCity(String city) {
        return placeRepository.findAllByCity(city);
    }

    public void save(PlaceModel place) {
        placeRepository.save(place);
    }
}
