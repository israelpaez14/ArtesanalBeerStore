package com.artesanalbeer.artesanalbeerstore.controller.beer;

import com.artesanalbeer.artesanalbeerstore.common.BeerStoreTest;
import com.artesanalbeer.artesanalbeerstore.entities.beer.Beer;
import com.artesanalbeer.artesanalbeerstore.entities.beer.BeerType;
import com.artesanalbeer.artesanalbeerstore.reposotory.beer.BeerRepository;
import com.artesanalbeer.artesanalbeerstore.reposotory.beer.BeerTypeRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
