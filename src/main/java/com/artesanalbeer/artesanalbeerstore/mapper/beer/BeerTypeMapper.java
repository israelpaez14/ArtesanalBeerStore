package com.artesanalbeer.artesanalbeerstore.mapper.beer;

import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerTypeResponse;
import com.artesanalbeer.artesanalbeerstore.entities.beer.BeerType;
import org.springframework.stereotype.Service;

@Service
public class BeerTypeMapper {
    public BeerTypeResponse toBeerTypeResponse(BeerType beerType) {
        return BeerTypeResponse.builder()
                .id(beerType.getId())
                .name(beerType.getName())
                .description(beerType.getDescription())
                .createdAt(beerType.getCreatedAt())
                .updatedAt(beerType.getUpdatedAt())
                .build();
    }
}
