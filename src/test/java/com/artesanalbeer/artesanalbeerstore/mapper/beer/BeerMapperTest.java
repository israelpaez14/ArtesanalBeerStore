package com.artesanalbeer.artesanalbeerstore.mapper.beer;

import static org.assertj.core.api.Assertions.assertThat;

import com.artesanalbeer.artesanalbeerstore.common.BeerStoreTest;
import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerRequest;
import com.artesanalbeer.artesanalbeerstore.entities.beer.Beer;
import com.artesanalbeer.artesanalbeerstore.entities.beer.BeerType;
import com.artesanalbeer.artesanalbeerstore.reposotory.beer.BeerTypeRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class BeerMapperTest extends BeerStoreTest {
  @Autowired private BeerMapper beerMapper;

  @Autowired private BeerTypeRepository beerTypeRepository;

  @Test
  @Transactional
  void testToBeer() {

    BeerType beerType = this.getBearType("Lager");
    beerTypeRepository.save(beerType);
    BeerRequest beerRequest =
        BeerRequest.builder()
            .name("Corona")
            .description("Corona, a fresh bear")
            .releasedAt(LocalDate.now())
            .alcoholPercentage(40)
            .beerTypeId(beerType.getId())
            .build();

    Beer beer = beerMapper.toBeer(beerRequest);
    assertThat(beer.getName()).isEqualTo(beerRequest.getName());
    assertThat(beer.getDescription()).isEqualTo(beerRequest.getDescription());
    assertThat(beer.getReleasedAt()).isEqualTo(beerRequest.getReleasedAt());
    assertThat(beer.getAlcoholPercentage()).isEqualTo(beerRequest.getAlcoholPercentage());
    assertThat(beer.getBeerType().getId()).isEqualTo(beerRequest.getBeerTypeId());
  }
}
