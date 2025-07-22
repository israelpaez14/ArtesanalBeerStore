package com.artesanalbeer.artesanalbeerstore.service.beer;

import com.artesanalbeer.artesanalbeerstore.common.BeerStoreTest;
import com.artesanalbeer.artesanalbeerstore.entities.beer.BeerType;
import com.artesanalbeer.artesanalbeerstore.exception.NotFoundException;
import com.artesanalbeer.artesanalbeerstore.reposotory.beer.BeerTypeRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class BeerTypeServiceImpTest extends BeerStoreTest {
    @Autowired
    private BeerTypeService beerTypeService;

    @Autowired
    private BeerTypeRepository beerTypeRepository;


    @Test
    public void testThatGetTypeOfFailFails() {
        Assertions.assertThrows(NotFoundException.class, () -> this.beerTypeService.getBeerTypeOrFail("BeerName"));

    }

    @Test
    @Transactional
    public void testThatGetTypeOfFail() {
        BeerType beerType = this.getBearType("Lagger");
        this.beerTypeRepository.save(beerType);
        BeerType beerType2 = this.beerTypeService.getBeerTypeOrFail("Lagger");
        assertThat(beerType2.getId()).isEqualTo(beerType2.getId());

    }

    @Test
    @Transactional
    public void testThatGetTypeByIdOfFail() {
        BeerType beerType = this.getBearType("Lagger");
        this.beerTypeRepository.save(beerType);
        BeerType beerType2 = this.beerTypeService.getBeerTypeByIdOrFail(beerType.getId());
        assertThat(beerType2.getId()).isEqualTo(beerType2.getId());
    }

}
