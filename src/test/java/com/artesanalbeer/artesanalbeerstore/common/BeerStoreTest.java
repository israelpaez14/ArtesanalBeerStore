package com.artesanalbeer.artesanalbeerstore.common;

import com.artesanalbeer.artesanalbeerstore.entities.beer.Beer;
import com.artesanalbeer.artesanalbeerstore.entities.beer.BeerType;

import java.time.LocalDate;

public class BeerStoreTest {
    protected BeerType getBearType(String name) {
        return BeerType.builder()
                .name(name)
                .description("This is a fresh beer")
                .build();
    }

    protected Beer getBeer(BeerType beerType, String beerName) {
        return Beer.builder()
                .name(beerName)
                .description("This is a fresh beer")
                .beerType(beerType)
                .alcoholPercentage(40)
                .pictureUrl("https://www.linkedin.com/in/indio-lagger/")
                .releasedAt(LocalDate.now())
                .build();
    }
}
