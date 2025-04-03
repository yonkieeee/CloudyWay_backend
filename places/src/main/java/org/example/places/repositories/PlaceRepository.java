package org.example.places.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.places.models.PlaceModel;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<PlaceModel, Long>{
    List<PlaceModel> findByPlaceName(String name);
    List<PlaceModel> findAllByCity(String city);
}
