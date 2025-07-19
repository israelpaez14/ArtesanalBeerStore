package com.artesanalbeer.artesanalbeerstore.service.beer;

import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerResponse;
import com.artesanalbeer.artesanalbeerstore.entities.beer.Beer;
import com.artesanalbeer.artesanalbeerstore.mapper.beer.BeerMapper;
import com.artesanalbeer.artesanalbeerstore.reposotory.beer.BeerRepository;
import com.artesanalbeer.artesanalbeerstore.utils.PaginatedResponse;
import com.artesanalbeer.artesanalbeerstore.utils.PaginationConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BeerServiceImp implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public PaginatedResponse<BeerResponse> getBeers(Integer page) {
        Pageable pageable = PageRequest.of(page, PaginationConfiguration.PAGE_SIZE, Sort.by("name").descending());
        Page<Beer> beersPage = beerRepository.findAll(pageable);
        List<BeerResponse> beers = beersPage.getContent().stream().map(beerMapper::toBeerResponse).toList();
        PaginatedResponse<BeerResponse> paginatedResponse = new PaginatedResponse<>();
        paginatedResponse.setContent(beers);
        paginatedResponse.setPage(page);
        paginatedResponse.setPageSize(PaginationConfiguration.PAGE_SIZE);
        paginatedResponse.setTotalPages(beersPage.getTotalPages());
        paginatedResponse.setTotalItems((int) beersPage.getTotalElements());
        return paginatedResponse;
    }
}
