package com.artesanalbeer.artesanalbeerstore.service.beer;

import com.artesanalbeer.artesanalbeerstore.entities.beer.BeerType;
import com.artesanalbeer.artesanalbeerstore.exception.NotFoundException;
import com.artesanalbeer.artesanalbeerstore.reposotory.beer.BeerTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeerTypeServiceImp implements BeerTypeService {

    private final BeerTypeRepository beerTypeRepository;

    @Override
    public BeerType getBeerTypeOrFail(String beerTypeName) {
        return beerTypeRepository.findByName(beerTypeName).orElseThrow(() -> new NotFoundException("Beer Type Not Found"));
    }

    @Override
    public BeerType getBeerTypeByIdOrFail(UUID beerTypeId) {
        return beerTypeRepository.findById(beerTypeId).orElseThrow(() -> new NotFoundException("Beer Type Not Found"));
    }
}
