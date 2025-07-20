package com.artesanalbeer.artesanalbeerstore.reposotory.beer;

import com.artesanalbeer.artesanalbeerstore.entities.beer.Beer;
import com.artesanalbeer.artesanalbeerstore.entities.beer.BeerType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
    Page<Beer> findAllByBeerType(BeerType beerType, Pageable pageable);
}
