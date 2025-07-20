package com.artesanalbeer.artesanalbeerstore.service.beer;

import com.artesanalbeer.artesanalbeerstore.common.BeerStoreTest;
import com.artesanalbeer.artesanalbeerstore.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class BeerTypeServiceImpTest extends BeerStoreTest {
    @Autowired
    private BeerTypeService beerTypeService;


    @Test
    public void testThatGetTypeOfFailFails() {
        Assertions.assertThrows(NotFoundException.class, () -> this.beerTypeService.findBeerTypeOrFail("BeerName"));

    }
}