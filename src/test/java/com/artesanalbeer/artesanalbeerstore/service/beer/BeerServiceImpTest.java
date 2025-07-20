package com.artesanalbeer.artesanalbeerstore.service.beer;

import com.artesanalbeer.artesanalbeerstore.common.BeerStoreTest;
import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerRequest;
import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerResponse;
import com.artesanalbeer.artesanalbeerstore.entities.beer.Beer;
import com.artesanalbeer.artesanalbeerstore.entities.beer.BeerType;
import com.artesanalbeer.artesanalbeerstore.exception.NotFoundException;
import com.artesanalbeer.artesanalbeerstore.reposotory.beer.BeerRepository;
import com.artesanalbeer.artesanalbeerstore.reposotory.beer.BeerTypeRepository;
import com.artesanalbeer.artesanalbeerstore.utils.PaginatedResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class BeerServiceImpTest extends BeerStoreTest {
    private final String FIXTURES_FOLDER_PATH = "src/test/java/com/artesanalbeer/artesanalbeerstore/fixtures/";


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

    @Test
    @Transactional
    void getBeersByBeerType() {
        BeerType beerType = this.getBearType("Lager");
        Beer beer = this.getBeer(beerType, "Indio");
        beerTypeRepository.save(beerType);
        beerRepository.save(beer);
        BeerResponse searchedBeer = this.beerService.getBeerById(beer.getId());
        assert searchedBeer.getId().equals(beer.getId());
        assert searchedBeer.getName().equals(beer.getName());
        assert searchedBeer.getBeerType().getName().equals(beerType.getName());
        assert searchedBeer.getDescription().equals(beer.getDescription());
    }

    @Test
    @Transactional
    void testDeleteBeer() {
        BeerType beerType = this.getBearType("Lager");
        Beer beer = this.getBeer(beerType, "Indio");
        beerTypeRepository.save(beerType);
        beerRepository.save(beer);
        this.beerService.deleteBeer(beer.getId());
        assert !this.beerRepository.existsById(beer.getId());
    }

    @Test
    @Transactional
    void testDeleteBeerNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> this.beerService.deleteBeer(UUID.randomUUID()));
    }

    @Test
    @Transactional
    void testCreateBeer() throws IOException {
        BeerType beerType = this.getBearType("Lager");
        beerTypeRepository.save(beerType);
        BeerRequest beerRequest = BeerRequest.builder()
                .name("Lager")
                .description("Corona")
                .beerTypeId(beerType.getId())
                .alcoholPercentage(10)
                .releasedAt(LocalDate.now())
                .description("A Corona Bear, Ideal for beaches")
                .build();


        InputStream is = getClass().getResourceAsStream(this.FIXTURES_FOLDER_PATH + "images/test.png");
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "beer-list.csv",
                "image/png",
                is
        );

        BeerResponse beerResponse = this.beerService.createBeer(beerRequest, mockFile);

        assertThat(beerResponse.getId()).isNotNull();
        assertThat(beerResponse.getBeerType().getName()).isEqualTo(beerType.getName());
        assertThat(beerResponse.getBeerType().getDescription()).isEqualTo(beerType.getDescription());
        assertThat(beerResponse.getPictureUrl()).isNotNull();


    }
}