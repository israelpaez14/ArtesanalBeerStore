package com.artesanalbeer.artesanalbeerstore.service.beer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.artesanalbeer.artesanalbeerstore.common.BeerStoreTest;
import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerTypeRequest;
import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerTypeResponse;
import com.artesanalbeer.artesanalbeerstore.entities.beer.BeerType;
import com.artesanalbeer.artesanalbeerstore.exception.NotFoundException;
import com.artesanalbeer.artesanalbeerstore.reposotory.beer.BeerTypeRepository;
import com.artesanalbeer.artesanalbeerstore.utils.PaginatedResponse;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class BeerTypeServiceImpTest extends BeerStoreTest {
  @Autowired private BeerTypeService beerTypeService;

  @Autowired private BeerTypeRepository beerTypeRepository;

  @Test
  public void testThatGetTypeOfFailFails() {
    Assertions.assertThrows(
        NotFoundException.class, () -> this.beerTypeService.getBeerTypeOrFail("BeerName"));
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

  @Test
  @Transactional
  public void testGetBeerTypeList() {
    for (int i = 0; i < 20; i++) {
      BeerType beerType = this.getBearType(UUID.randomUUID().toString());
      beerTypeRepository.save(beerType);
    }
    PaginatedResponse<BeerTypeResponse> beerTypes = this.beerTypeService.getBeerTypes(1);
    assertThat(beerTypes.getTotalPages()).isEqualTo(2);
    assertThat(beerTypes.getTotalItems()).isEqualTo(20);
  }

  @Test
  @Transactional
  public void testCreateBeerType() {
    BeerTypeRequest beerTypeRequest =
        BeerTypeRequest.builder().name("Lager").description("Lager Beer Type").build();

    BeerTypeResponse beerTypeResponse = this.beerTypeService.createBeerType(beerTypeRequest);

    assertThat(beerTypeResponse.getId()).isNotNull();
    assertThat(beerTypeResponse.getName()).isEqualTo(beerTypeRequest.getName());
    assertThat(beerTypeResponse.getDescription()).isEqualTo(beerTypeRequest.getDescription());

    BeerType createdBeerType = this.beerTypeService.getBeerTypeByIdOrFail(beerTypeResponse.getId());
    assertThat(createdBeerType.getId()).isEqualTo(beerTypeResponse.getId());
    assertThat(createdBeerType.getName()).isEqualTo(beerTypeRequest.getName());
    assertThat(createdBeerType.getDescription()).isEqualTo(beerTypeRequest.getDescription());
  }

  @Test
  @Transactional
  public void testUpdateBeerType() {
    BeerType beerType = this.getBearType("Lager");
    beerTypeRepository.save(beerType);
    BeerTypeRequest beerTypeRequest =
        BeerTypeRequest.builder().name("Lager").description("Lager Updated Description").build();

    BeerTypeResponse beerTypeResponse =
        beerTypeService.updateBeerType(beerType.getId(), beerTypeRequest);
    assertThat(beerType.getId()).isEqualTo(beerTypeResponse.getId());
    assertThat(beerTypeResponse.getName()).isEqualTo(beerTypeRequest.getName());
    assertThat(beerTypeResponse.getDescription()).isEqualTo(beerTypeRequest.getDescription());

    BeerType updatedBeerType = this.beerTypeService.getBeerTypeByIdOrFail(beerTypeResponse.getId());
    assertThat(updatedBeerType.getId()).isEqualTo(beerTypeResponse.getId());
    assertThat(updatedBeerType.getName()).isEqualTo(beerTypeRequest.getName());
    assertThat(updatedBeerType.getDescription()).isEqualTo(beerTypeRequest.getDescription());
  }
}
