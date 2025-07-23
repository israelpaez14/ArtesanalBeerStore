package com.artesanalbeer.artesanalbeerstore.controller.beer;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.artesanalbeer.artesanalbeerstore.common.BeerStoreTest;
import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerTypeRequest;
import com.artesanalbeer.artesanalbeerstore.entities.beer.BeerType;
import com.artesanalbeer.artesanalbeerstore.reposotory.beer.BeerTypeRepository;
import com.artesanalbeer.artesanalbeerstore.security.Roles;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BeerTypeControllerTest extends BeerStoreTest {
  @Autowired private MockMvc mockMvc;

  @Autowired private BeerTypeRepository beerTypeRepository;

  @Autowired private ObjectMapper objectMapper;

  @Test
  @Transactional
  void getBeerTypes() throws Exception {
    for (int i = 0; i < 20; i++) {
      BeerType beerType = this.getBearType(UUID.randomUUID().toString());
      beerTypeRepository.save(beerType);
    }
    mockMvc
        .perform(get("/beer-types").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content.length()").value(10))
        .andExpect(jsonPath("$.totalPages").value(2))
        .andExpect(jsonPath("$.totalItems").value(20))
        .andExpect(jsonPath("$.pageSize").value(10));
  }

  @Test
  @Transactional
  void createBeerType() throws Exception {
    BeerTypeRequest beerTypeRequest =
        BeerTypeRequest.builder().name("Pale Ale").description("A golder ale beer").build();

    String beerRequestAsString = objectMapper.writeValueAsString(beerTypeRequest);

    mockMvc
        .perform(
            post("/beer-types")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerRequestAsString)
                .with(
                    jwt()
                        .authorities(new SimpleGrantedAuthority(Roles.ADMIN))
                        .jwt(jwt -> jwt.claim("sub", UUID.randomUUID().toString()))))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("Pale Ale"))
        .andExpect(jsonPath("$.description").value("A golder ale beer"))
        .andExpect(jsonPath("$.createdAt").isNotEmpty());
  }

  @Test
  @Transactional
  void createBeerTypeUnauthenticated() throws Exception {
    BeerTypeRequest beerTypeRequest =
        BeerTypeRequest.builder().name("Pale Ale").description("A golder ale beer").build();

    String beerRequestAsString = objectMapper.writeValueAsString(beerTypeRequest);
    mockMvc
        .perform(
            post("/beer-types")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerRequestAsString))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @Transactional
  void createBeerTypeWithUserRole() throws Exception {
    BeerTypeRequest beerTypeRequest =
        BeerTypeRequest.builder().name("Pale Ale").description("A golder ale beer").build();

    String beerRequestAsString = objectMapper.writeValueAsString(beerTypeRequest);

    mockMvc
        .perform(
            post("/beer-types")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerRequestAsString)
                .with(
                    jwt()
                        .authorities(new SimpleGrantedAuthority(Roles.USER))
                        .jwt(jwt -> jwt.claim("sub", UUID.randomUUID().toString()))))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @Transactional
  void updateBeerType() throws Exception {
    BeerType beerType = this.getBearType("Lager");
    beerTypeRepository.save(beerType);

    BeerTypeRequest beerTypeRequest =
        BeerTypeRequest.builder().name("Lager").description("Updated lager description").build();

    String beerRequestAsString = objectMapper.writeValueAsString(beerTypeRequest);

    mockMvc
        .perform(
            put("/beer-types/{beer-type-id}", beerType.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerRequestAsString)
                .with(
                    jwt()
                        .authorities(new SimpleGrantedAuthority(Roles.ADMIN))
                        .jwt(jwt -> jwt.claim("sub", UUID.randomUUID().toString()))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(beerTypeRequest.getName()))
        .andExpect(jsonPath("$.description").value(beerTypeRequest.getDescription()));
  }

  @Test
  @Transactional
  void updateBeerTypeAsNormalUser() throws Exception {
    BeerType beerType = this.getBearType("Lager");
    beerTypeRepository.save(beerType);

    BeerTypeRequest beerTypeRequest =
        BeerTypeRequest.builder().name("Lager").description("Updated lager description").build();

    String beerRequestAsString = objectMapper.writeValueAsString(beerTypeRequest);

    mockMvc
        .perform(
            put("/beer-types/{beer-type-id}", beerType.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerRequestAsString)
                .with(
                    jwt()
                        .authorities(new SimpleGrantedAuthority(Roles.USER))
                        .jwt(jwt -> jwt.claim("sub", UUID.randomUUID().toString()))))
        .andExpect(status().isUnauthorized());
  }
}
