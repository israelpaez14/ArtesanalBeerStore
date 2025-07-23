package com.artesanalbeer.artesanalbeerstore.mapper.beer;

import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerRequest;
import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerResponse;
import com.artesanalbeer.artesanalbeerstore.entities.beer.Beer;
import com.artesanalbeer.artesanalbeerstore.service.beer.BeerTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BeerMapper {
  private final BeerTypeMapper beerTypeMapper;
  private final BeerTypeService beerTypeService;

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

  public Beer toBeer(BeerRequest beerRequest) {
    return Beer.builder()
        .name(beerRequest.getName())
        .description(beerRequest.getDescription())
        .beerType(this.beerTypeService.getBeerTypeByIdOrFail(beerRequest.getBeerTypeId()))
        .alcoholPercentage(beerRequest.getAlcoholPercentage())
        .releasedAt(beerRequest.getReleasedAt())
        .build();
  }
}
