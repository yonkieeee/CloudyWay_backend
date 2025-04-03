package org.example.places.dto;

import org.example.places.models.CoordinatesModel;

public record PlaceDTO(String placeName, String city, String county, String street, String houseNumber, CoordinatesModel coordinates) {}
