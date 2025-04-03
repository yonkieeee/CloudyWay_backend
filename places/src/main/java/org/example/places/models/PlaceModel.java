package org.example.places.models;

import lombok.*;
import jakarta.persistence.*;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "places")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String here_api_id;

    @Column(nullable = false)
    private String placeName;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String county;

    @Column(nullable = false)
    private String street;

    @Column(name = "house_number")
    private String houseNumber;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private CoordinatesModel coordinates;

    @Column
    private String description;

    @Lob
    @Column(columnDefinition = "bytea")
    private byte[] photo;
}

