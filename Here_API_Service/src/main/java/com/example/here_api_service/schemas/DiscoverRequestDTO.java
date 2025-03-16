package com.example.here_api_service.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DiscoverRequestDTO {
    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("longitude")
    private double longitude;

    @JsonProperty("query")
    private String query;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getQuery() {
        return query;
    }
}
