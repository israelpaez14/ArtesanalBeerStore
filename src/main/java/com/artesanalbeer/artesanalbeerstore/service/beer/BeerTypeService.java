package com.artesanalbeer.artesanalbeerstore.service.beer;

import com.artesanalbeer.artesanalbeerstore.entities.beer.BeerType;

import java.util.UUID;

public interface BeerTypeService {
    BeerType getBeerTypeOrFail(String beerTypeName);
    BeerType getBeerTypeByIdOrFail(UUID beerTypeId);
}
