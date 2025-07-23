package com.artesanalbeer.artesanalbeerstore.service.beer;

import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerTypeRequest;
import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerTypeResponse;
import com.artesanalbeer.artesanalbeerstore.entities.beer.BeerType;
import com.artesanalbeer.artesanalbeerstore.exception.NotFoundException;
import com.artesanalbeer.artesanalbeerstore.mapper.beer.BeerTypeMapper;
import com.artesanalbeer.artesanalbeerstore.reposotory.beer.BeerTypeRepository;
import com.artesanalbeer.artesanalbeerstore.utils.PaginatedResponse;
import com.artesanalbeer.artesanalbeerstore.utils.PaginationConfiguration;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BeerTypeServiceImp implements BeerTypeService {

  private final BeerTypeRepository beerTypeRepository;
  private final BeerTypeMapper beerTypeMapper;

  @Override
  public BeerType getBeerTypeOrFail(String beerTypeName) {
    return beerTypeRepository
        .findByName(beerTypeName)
        .orElseThrow(() -> new NotFoundException("Beer Type Not Found"));
  }

  @Override
  public BeerType getBeerTypeByIdOrFail(UUID beerTypeId) {
    return beerTypeRepository
        .findById(beerTypeId)
        .orElseThrow(() -> new NotFoundException("Beer Type Not Found"));
  }

  @Override
  public PaginatedResponse<BeerTypeResponse> getBeerTypes(Integer page) {
    Pageable pageable =
        PageRequest.of(page, PaginationConfiguration.PAGE_SIZE, Sort.by("name").descending());
    Page<BeerType> beerTypePage = beerTypeRepository.findAll(pageable);

    List<BeerTypeResponse> beerTypeResponses =
        beerTypePage.stream().map(this.beerTypeMapper::toBeerTypeResponse).toList();

    PaginatedResponse<BeerTypeResponse> paginatedResponse = new PaginatedResponse<>();
    paginatedResponse.setContent(beerTypeResponses);
    paginatedResponse.setPage(page);
    paginatedResponse.setTotalItems((int) beerTypePage.getTotalElements());
    paginatedResponse.setTotalPages(beerTypePage.getTotalPages());
    paginatedResponse.setPageSize(PaginationConfiguration.PAGE_SIZE);
    return paginatedResponse;
  }

  @Override
  public BeerTypeResponse createBeerType(BeerTypeRequest beerTypeRequest) {
    BeerType beerType =
        BeerType.builder()
            .name(beerTypeRequest.getName())
            .description(beerTypeRequest.getDescription())
            .build();
    return beerTypeMapper.toBeerTypeResponse(beerTypeRepository.save(beerType));
  }
}
