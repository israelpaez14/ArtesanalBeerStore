package com.artesanalbeer.artesanalbeerstore.controller.beer;

import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerRequest;
import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerResponse;
import com.artesanalbeer.artesanalbeerstore.security.Roles;
import com.artesanalbeer.artesanalbeerstore.service.beer.BeerService;
import com.artesanalbeer.artesanalbeerstore.utils.PaginatedResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/beers")
@Tag(name = "Beer", description = "Beers related endpoint")
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
    @Secured({Roles.STAFF, Roles.ADMIN})
    public ResponseEntity<BeerResponse> addBeer(
            @Valid @RequestBody BeerRequest beerRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.beerService.createBeer(beerRequest));
    }

    @PostMapping("{beer-id}/upload-picture")
    @ResponseStatus(HttpStatus.OK)
    @Secured({Roles.STAFF, Roles.ADMIN})
    public void uploadBeerPicture(@PathVariable(name = "beer-id") UUID beerId, @RequestPart MultipartFile file) throws IOException {
        this.beerService.uploadBeerPicture(file, beerId);
    }

    @PutMapping("{beer-id}")
    @Secured({Roles.STAFF, Roles.ADMIN})
    public ResponseEntity<BeerResponse> updateBeer(
            @Valid @RequestBody BeerRequest beerRequest,
            @PathVariable(name = "beer-id") UUID beerId
    ) {
        return ResponseEntity.ok(this.beerService.updateBeer(beerId, beerRequest));
    }
}
