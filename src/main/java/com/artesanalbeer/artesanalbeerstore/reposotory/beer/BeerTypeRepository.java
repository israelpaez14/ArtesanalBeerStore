package com.artesanalbeer.artesanalbeerstore.reposotory.beer;

import com.artesanalbeer.artesanalbeerstore.entities.beer.BeerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerTypeRepository extends JpaRepository<BeerType, UUID> {
}
