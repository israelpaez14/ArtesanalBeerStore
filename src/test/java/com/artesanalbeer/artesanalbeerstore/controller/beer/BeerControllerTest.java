package com.artesanalbeer.artesanalbeerstore.controller.beer;

import com.artesanalbeer.artesanalbeerstore.common.BeerStoreTest;
import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerRequest;
import com.artesanalbeer.artesanalbeerstore.entities.beer.Beer;
import com.artesanalbeer.artesanalbeerstore.entities.beer.BeerType;
import com.artesanalbeer.artesanalbeerstore.reposotory.beer.BeerRepository;
import com.artesanalbeer.artesanalbeerstore.reposotory.beer.BeerTypeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BeerControllerTest extends BeerStoreTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private BeerTypeRepository beerTypeRepository;

    @Autowired
    private ObjectMapper objectMapper;

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

        mockMvc.perform(get("/beers"))
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

        mockMvc.perform(get("/beers/{id}", beer.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(beer.getName()))
                .andExpect(jsonPath("$.description").value(beer.getDescription()))
                .andExpect(jsonPath("$.id").value(beer.getId().toString()));
    }

    @Test
    @Transactional
    void getBeerByIdNotFound() throws Exception {
        mockMvc.perform(get("/beers/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
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

        mockMvc.perform(get("/beers/type/{beerType}", beerType.getName())).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].name").value(beer.getName()))
                .andExpect(jsonPath("$.content[1].name").value(beer2.getName()));
    }

    @Test
    @Transactional
    void createBeer() throws Exception {
        BeerType beerType = this.getBearType("Lager");
        beerTypeRepository.save(beerType);

        BeerRequest beerRequest = BeerRequest.builder()
                .name("Corona")
                .description("A fresh beer, ideal for beaches")
                .beerTypeId(beerType.getId())
                .releasedAt(LocalDate.now())
                .alcoholPercentage(5)
                .build();

        String json = objectMapper.writeValueAsString(beerRequest);

        mockMvc.perform(post("/beers").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated());
    }


}
