package com.example.here_api_service.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeocodePlaceRequestDTO {
    @JsonProperty("placeName")
    private String placeName;

    @JsonProperty("houseNumber")
    private String houseNumber;

    @JsonProperty("street")
    private String street;

    @JsonProperty("city")
    private String city;

    public String getPlaceName() {
        return placeName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }
}
