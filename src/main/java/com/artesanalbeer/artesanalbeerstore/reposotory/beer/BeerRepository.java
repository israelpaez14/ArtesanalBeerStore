package com.artesanalbeer.artesanalbeerstore.reposotory.beer;

import com.artesanalbeer.artesanalbeerstore.entities.beer.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
