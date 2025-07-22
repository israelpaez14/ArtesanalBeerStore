package com.artesanalbeer.artesanalbeerstore.controller.beer;

import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerResponse;
import com.artesanalbeer.artesanalbeerstore.service.beer.BeerService;
import com.artesanalbeer.artesanalbeerstore.utils.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/beers")
public class BeerController {
    private final BeerService beerService;


    @GetMapping
    public ResponseEntity<PaginatedResponse<BeerResponse>> getBeers(
            @RequestParam(value = "page", defaultValue = "0") Integer page) {
        return ResponseEntity.ok(this.beerService.getBeers(page));
    }

}
