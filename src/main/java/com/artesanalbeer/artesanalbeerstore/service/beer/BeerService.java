package com.artesanalbeer.artesanalbeerstore.service.beer;

import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerResponse;
import com.artesanalbeer.artesanalbeerstore.utils.PaginatedResponse;

public interface BeerService {
    PaginatedResponse<BeerResponse> getBeers(Integer page);
}
