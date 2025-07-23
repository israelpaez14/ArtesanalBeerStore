package com.artesanalbeer.artesanalbeerstore.controller.beer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.artesanalbeer.artesanalbeerstore.common.BeerStoreTest;
import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerRequest;
import com.artesanalbeer.artesanalbeerstore.entities.beer.Beer;
import com.artesanalbeer.artesanalbeerstore.entities.beer.BeerType;
import com.artesanalbeer.artesanalbeerstore.reposotory.beer.BeerRepository;
import com.artesanalbeer.artesanalbeerstore.reposotory.beer.BeerTypeRepository;
import com.artesanalbeer.artesanalbeerstore.security.Roles;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BeerControllerTest extends BeerStoreTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private BeerRepository beerRepository;

  @Autowired private BeerTypeRepository beerTypeRepository;

  @Autowired private ObjectMapper objectMapper;

  @Test
  @Transactional
  void getBeers() throws Exception {
    BeerType beerType = this.getBearType("Lager");
    Beer beer = this.getBeer(beerType, "Corona");
    Beer beer2 = this.getBeer(beerType, "Indio");
    Beer beer3 = this.getBeer(beerType, "Maestra Dunkel");

    beerTypeRepository.save(beerType);
    beerRepository.save(beer);
    beerRepository.save(beer2);
    beerRepository.save(beer3);

    mockMvc
        .perform(get("/beers"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.content[0].name").value(beer3.getName()))
        .andExpect(jsonPath("$.content[1].name").value(beer2.getName()))
        .andExpect(jsonPath("$.content[2].name").value(beer.getName()))
        .andExpect(jsonPath("$.page").value(0))
        .andExpect(jsonPath("$.totalItems").value(3));
  }

  @Test
  @Transactional
  void getBeerById() throws Exception {
    BeerType beerType = this.getBearType("Lager");
    Beer beer = this.getBeer(beerType, "Corona");
    beerTypeRepository.save(beerType);
    beerRepository.save(beer);

    mockMvc
        .perform(get("/beers/{id}", beer.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.name").value(beer.getName()))
        .andExpect(jsonPath("$.description").value(beer.getDescription()))
        .andExpect(jsonPath("$.id").value(beer.getId().toString()));
  }

  @Test
  @Transactional
  void getBeerByIdNotFound() throws Exception {
    mockMvc.perform(get("/beers/{id}", UUID.randomUUID())).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void getBeersByBeerType() throws Exception {
    BeerType beerType = this.getBearType("Lager");
    BeerType beerType2 = this.getBearType("Ale");
    Beer beer = this.getBeer(beerType, "Corona");
    Beer beer2 = this.getBeer(beerType, "Indio");
    Beer beer3 = this.getBeer(beerType2, "Stout");

    beerTypeRepository.save(beerType);
    beerTypeRepository.save(beerType2);
    beerRepository.save(beer);
    beerRepository.save(beer2);
    beerRepository.save(beer3);

    mockMvc
        .perform(get("/beers/type/{beerType}", beerType.getName()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.content.length()").value(2));
  }

  @Test
  @Transactional
  void createBeer() throws Exception {
    BeerType beerType = this.getBearType("Lager");
    beerTypeRepository.save(beerType);

    BeerRequest beerRequest =
        BeerRequest.builder()
            .name("Corona")
            .description("A fresh beer, ideal for beaches")
            .beerTypeId(beerType.getId())
            .releasedAt(LocalDate.now())
            .alcoholPercentage(5)
            .build();

    String json = objectMapper.writeValueAsString(beerRequest);

    mockMvc
        .perform(
            post("/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(
                    jwt()
                        .authorities(new SimpleGrantedAuthority(Roles.ADMIN))
                        .jwt(jwt -> jwt.claim("sub", UUID.randomUUID().toString()))))
        .andExpect(status().isCreated());
  }

  @Test
  @Transactional
  void uploadBeerPicture() throws Exception {
    BeerType beerType = this.getBearType("Lager");
    beerTypeRepository.save(beerType);

    Beer beer = this.getBeer(beerType, "Corona");
    beerRepository.save(beer);

    String TESTING_IMAGE_FIXTURE = "/fixtures/images/testing.png";

    InputStream is = getClass().getResourceAsStream(TESTING_IMAGE_FIXTURE);
    MockMultipartFile mockFile = new MockMultipartFile("file", "beer-list.csv", "image/png", is);

    mockMvc
        .perform(
            multipart("/beers/" + beer.getId() + "/upload-picture")
                .file(mockFile)
                .with(
                    jwt()
                        .authorities(new SimpleGrantedAuthority(Roles.ADMIN))
                        .jwt(jwt -> jwt.claim("sub", UUID.randomUUID().toString()))))
        .andExpect(status().isOk());

    Beer updatedBeer = this.beerRepository.findById(beer.getId()).orElse(null);
    Assertions.assertNotNull(updatedBeer);
    assertThat(updatedBeer.getPictureUrl()).isNotNull();
  }

  @Test
  @Transactional
  void updateBeerPicture() throws Exception {
    BeerType beerType = this.getBearType("Lager");
    beerTypeRepository.save(beerType);
    Beer beer = this.getBeer(beerType, "Corona");
    beerRepository.save(beer);

    BeerRequest request =
        BeerRequest.builder()
            .name("Corona Updated")
            .description("A fresh beer, ideal for beaches")
            .beerTypeId(beerType.getId())
            .releasedAt(LocalDate.now())
            .alcoholPercentage(6)
            .build();
    String json = objectMapper.writeValueAsString(request);
    mockMvc
        .perform(
            put("/beers/{beer-id}", beer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(
                    jwt()
                        .authorities(new SimpleGrantedAuthority(Roles.ADMIN))
                        .jwt(jwt -> jwt.claim("sub", UUID.randomUUID().toString()))))
        .andExpect(status().isOk());
    Beer updatedBeer = this.beerRepository.findById(beer.getId()).orElse(null);
    Assertions.assertNotNull(updatedBeer);
    assertThat(updatedBeer.getName()).isEqualTo(request.getName());
  }
}
