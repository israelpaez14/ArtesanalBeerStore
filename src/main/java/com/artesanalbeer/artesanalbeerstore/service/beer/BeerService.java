package com.artesanalbeer.artesanalbeerstore.service.beer;

import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerRequest;
import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerResponse;
import com.artesanalbeer.artesanalbeerstore.utils.PaginatedResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface BeerService {
    PaginatedResponse<BeerResponse> getBeers(Integer page);

    PaginatedResponse<BeerResponse> getBeersByBeerType(String beerTypeName, Integer page);

    BeerResponse getBeerById(UUID id);

    BeerResponse createBeer(BeerRequest beerRequest, MultipartFile picture) throws IOException;

    void deleteBeer(UUID id);

    BeerResponse updateBeer(UUID id, BeerRequest beerRequest, MultipartFile picture) throws IOException;
}
