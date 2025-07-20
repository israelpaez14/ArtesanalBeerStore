package com.artesanalbeer.artesanalbeerstore.service.beer;

import com.artesanalbeer.artesanalbeerstore.entities.beer.BeerType;

public interface BeerTypeService {
    BeerType findBeerTypeOrFail(String beerTypeName);

}
