package com.example.here_api_service.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DiscoverPlaceRequestDTO {
    @JsonProperty("placeName")
    private String placeName;

    public String getPlaceName() {
        return placeName;
    }
}
