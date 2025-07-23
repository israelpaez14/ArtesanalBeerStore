package com.artesanalbeer.artesanalbeerstore.reposotory.beer;

import com.artesanalbeer.artesanalbeerstore.entities.beer.BeerType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerTypeRepository extends JpaRepository<BeerType, UUID> {
    Optional<BeerType> findByName(String name);

}
