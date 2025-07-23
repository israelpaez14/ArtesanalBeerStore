package com.artesanalbeer.artesanalbeerstore.service.beer;

import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerTypeRequest;
import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerTypeResponse;
import com.artesanalbeer.artesanalbeerstore.entities.beer.BeerType;
import com.artesanalbeer.artesanalbeerstore.utils.PaginatedResponse;
import java.util.UUID;

public interface BeerTypeService {
  BeerType getBeerTypeOrFail(String beerTypeName);

  BeerType getBeerTypeByIdOrFail(UUID beerTypeId);

  PaginatedResponse<BeerTypeResponse> getBeerTypes(Integer page);

  BeerTypeResponse createBeerType(BeerTypeRequest beerTypeRequest);

}
