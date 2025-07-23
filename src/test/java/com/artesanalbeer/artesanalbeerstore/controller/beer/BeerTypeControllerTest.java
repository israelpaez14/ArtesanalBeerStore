package com.artesanalbeer.artesanalbeerstore.controller.beer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.artesanalbeer.artesanalbeerstore.common.BeerStoreTest;
import com.artesanalbeer.artesanalbeerstore.entities.beer.BeerType;
import com.artesanalbeer.artesanalbeerstore.reposotory.beer.BeerTypeRepository;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BeerTypeControllerTest extends BeerStoreTest {
  @Autowired private MockMvc mockMvc;

  @Autowired private BeerTypeRepository beerTypeRepository;

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
}
