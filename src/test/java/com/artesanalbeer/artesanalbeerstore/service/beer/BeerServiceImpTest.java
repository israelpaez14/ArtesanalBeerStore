package com.artesanalbeer.artesanalbeerstore.service.beer;

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

import java.time.LocalDate;

@SpringBootTest
@ActiveProfiles("test")
class BeerServiceImpTest {
    @Autowired
    private BeerService beerService;

    @Autowired
    private BeerRepository beerRepository;
    @Autowired
    private BeerTypeRepository beerTypeRepository;

    @Test
    @Transactional
    void getBeers() {
        BeerType beerType = BeerType.builder()
                .name("Lagger")
                .description("This is a fresh beer")
                .build();

        Beer beer = Beer.builder()
                .name("Indio")
                .description("This is a fresh beer")
                .beerType(beerType)
                .alcoholPercentage(40)
                .pictureUrl("https://www.linkedin.com/in/indio-lagger/")
                .releasedAt(LocalDate.now())
                .build();
        beerTypeRepository.save(beerType);
        beerRepository.save(beer);

        PaginatedResponse<BeerResponse> beers = this.beerService.getBeers(0);
        assert beers.getTotalPages() == 1;
        assert beers.getTotalItems() == 1;
        assert beers.getContent().size() == 1;
        assert beers.getContent().get(0).getBeerType().getName().equals(beerType.getName());
        assert beers.getContent().get(0).getId().equals(beer.getId());
    }
}