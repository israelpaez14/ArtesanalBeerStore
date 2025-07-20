package com.artesanalbeer.artesanalbeerstore.service.beer;

import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerRequest;
import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerResponse;
import com.artesanalbeer.artesanalbeerstore.entities.beer.Beer;
import com.artesanalbeer.artesanalbeerstore.entities.beer.BeerType;
import com.artesanalbeer.artesanalbeerstore.exception.NotFoundException;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerServiceImp implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerTypeService beerTypeService;
    private final BeerMapper beerMapper;

    @Override
    public PaginatedResponse<BeerResponse> getBeers(Integer page) {
        Pageable pageable = PageRequest.of(page, PaginationConfiguration.PAGE_SIZE, Sort.by("name").descending());
        Page<Beer> beersPage = beerRepository.findAll(pageable);
        return getBeerResponsePaginatedResponse(page, beersPage);
    }


    @Override
    public PaginatedResponse<BeerResponse> getBeersByBeerType(String beerTypeName, Integer page) {
        BeerType beerType = this.beerTypeService.getBeerTypeOrFail(beerTypeName);
        Pageable pageable = PageRequest.of(0, PaginationConfiguration.PAGE_SIZE, Sort.by("createdAt").descending());
        Page<Beer> beersPage = beerRepository.findAllByBeerType(beerType, pageable);
        return getBeerResponsePaginatedResponse(page, beersPage);
    }

    private PaginatedResponse<BeerResponse> getBeerResponsePaginatedResponse(Integer page, Page<Beer> beersPage) {
        List<BeerResponse> beersAsResponses = beersPage.getContent().stream().map(beerMapper::toBeerResponse).toList();
        PaginatedResponse<BeerResponse> paginatedResponse = new PaginatedResponse<>();
        paginatedResponse.setContent(beersAsResponses);
        paginatedResponse.setPage(page);
        paginatedResponse.setPageSize(PaginationConfiguration.PAGE_SIZE);
        paginatedResponse.setTotalPages(beersPage.getTotalPages());
        paginatedResponse.setTotalItems((int) beersPage.getTotalElements());
        return paginatedResponse;
    }

    @Override
    public BeerResponse getBeerById(UUID id) {
        Beer beer = beerRepository.findById(id).orElseThrow(() -> new NotFoundException("Beer Not Found"));
        return beerMapper.toBeerResponse(beer);
    }

    @Override
    public void deleteBeer(UUID id) {
        Beer beer = beerRepository.findById(id).orElseThrow(() -> new NotFoundException("Beer Not Found"));
        beerRepository.delete(beer);
    }

    @Override
    public BeerResponse createBeer(BeerRequest beerRequest, MultipartFile picture) {
        Beer beer = beerMapper.toBeer(beerRequest);
        this.beerRepository.save(beer);


        // Upload cover picture
        return beerMapper.toBeerResponse(beer);
    }

    @Override
    public BeerResponse updateBeer(UUID id, BeerRequest beerRequest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
