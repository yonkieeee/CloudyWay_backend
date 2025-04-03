package org.example.places.models;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoordinatesModel {

    @JsonProperty("lat")
    private String lat;

    @JsonProperty("lng")
    private String lng;
}
