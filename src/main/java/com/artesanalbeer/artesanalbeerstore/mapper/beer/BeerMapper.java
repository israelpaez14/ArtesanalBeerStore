package com.artesanalbeer.artesanalbeerstore.mapper.beer;

import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerResponse;
import com.artesanalbeer.artesanalbeerstore.entities.beer.Beer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BeerMapper {
    private final BeerTypeMapper beerTypeMapper;

    public BeerResponse toBeerResponse(Beer beer) {
        return BeerResponse.builder()
                .id(beer.getId())
                .name(beer.getName())
                .description(beer.getDescription())
                .beerType(beerTypeMapper.toBeerTypeResponse(beer.getBeerType()))
                .createdAt(beer.getCreatedAt())
                .modifiedAt(beer.getModifiedAt())
                .alcoholPercentage(beer.getAlcoholPercentage())
                .pictureUrl(beer.getPictureUrl())
                .releasedAt(beer.getReleasedAt())
                .build();
    }

}
