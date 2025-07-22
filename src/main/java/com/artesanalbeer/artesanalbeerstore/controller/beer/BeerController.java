package com.artesanalbeer.artesanalbeerstore.controller.beer;

import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerRequest;
import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerResponse;
import com.artesanalbeer.artesanalbeerstore.service.beer.BeerService;
import com.artesanalbeer.artesanalbeerstore.utils.PaginatedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @GetMapping("{beer-id}")
    public ResponseEntity<BeerResponse> getBeer(@PathVariable(name = "beer-id") UUID beerId) {
        return ResponseEntity.ok(this.beerService.getBeerById(beerId));
    }

    @GetMapping("type/{beer-type}")
    public ResponseEntity<PaginatedResponse<BeerResponse>> getBeerByBeerType(
            @PathVariable(name = "beer-type") String beerTypeName,
            @RequestParam(name = "page", defaultValue = "0") Integer page
    ) {
        return ResponseEntity.ok(this.beerService.getBeersByBeerType(beerTypeName, page));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BeerResponse> addBeer(
            @Valid @RequestBody BeerRequest beerRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.beerService.createBeer(beerRequest));
    }

}
