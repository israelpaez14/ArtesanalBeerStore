package com.artesanalbeer.artesanalbeerstore.service.beer;

import com.artesanalbeer.artesanalbeerstore.common.BeerStoreTest;
import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerResponse;
import com.artesanalbeer.artesanalbeerstore.entities.beer.Beer;
import com.artesanalbeer.artesanalbeerstore.entities.beer.BeerType;
import com.artesanalbeer.artesanalbeerstore.reposotory.beer.BeerRepository;
import com.artesanalbeer.artesanalbeerstore.reposotory.beer.BeerTypeRepository;
import com.artesanalbeer.artesanalbeerstore.utils.PaginatedResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class BeerServiceImpTest extends BeerStoreTest {
    @Autowired
    private BeerService beerService;

    @Autowired
    private BeerRepository beerRepository;
    @Autowired
    private BeerTypeRepository beerTypeRepository;


    @Test
    @Transactional
    void getBeers() {
        BeerType beerType = this.getBearType("Lager");
        Beer beer = this.getBeer(beerType, "Indio");

        beerTypeRepository.save(beerType);
        beerRepository.save(beer);

        PaginatedResponse<BeerResponse> beers = this.beerService.getBeers(0);
        assert beers.getTotalPages() == 1;
        assert beers.getTotalItems() == 1;
        assert beers.getContent().size() == 1;
        assert beers.getContent().get(0).getBeerType().getName().equals(beerType.getName());
        assert beers.getContent().get(0).getId().equals(beer.getId());
    }

    @Test
    @Transactional
    void getBeersByType() {
        BeerType beerType = this.getBearType("Lager");
        BeerType beerType2 = this.getBearType("Ale");
        Beer beer = this.getBeer(beerType, "Indio");
        Beer beer2 = this.getBeer(beerType, "Corona");
        Beer beer3 = this.getBeer(beerType2, "Minerva Stout");

        beerTypeRepository.save(beerType);
        beerTypeRepository.save(beerType2);
        beerRepository.save(beer);
        beerRepository.save(beer2);
        beerRepository.save(beer3);
        PaginatedResponse<BeerResponse> beers = this.beerService.getBeersByBeerType(beerType.getName(), 1);
        assert beers.getTotalPages() == 1;
        assert beers.getTotalItems() == 2;
        assert beers.getContent().size() == 2;
        assert beers.getContent().get(0).getBeerType().getName().equals(beerType.getName());
        assert beers.getContent().get(0).getId().equals(beer.getId());
    }

}