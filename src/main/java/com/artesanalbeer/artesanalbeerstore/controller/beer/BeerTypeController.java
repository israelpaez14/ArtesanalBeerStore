package com.artesanalbeer.artesanalbeerstore.controller.beer;

import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerTypeRequest;
import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerTypeResponse;
import com.artesanalbeer.artesanalbeerstore.security.Roles;
import com.artesanalbeer.artesanalbeerstore.service.beer.BeerTypeService;
import com.artesanalbeer.artesanalbeerstore.utils.PaginatedResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/beer-types")
@Tag(name = "BeerTypes", description = "BeerTypes related endpoint")
public class BeerTypeController {
  private final BeerTypeService beerTypeService;

  @GetMapping
  public ResponseEntity<PaginatedResponse<BeerTypeResponse>> getBeerTypes(
      @RequestParam(name = "page", defaultValue = "0") Integer page) {
    return ResponseEntity.ok(beerTypeService.getBeerTypes(page));
  }

  @PostMapping
  @Secured({Roles.ADMIN, Roles.STAFF})
  public ResponseEntity<BeerTypeResponse> createBeerType(
      @Valid @RequestBody BeerTypeRequest beerTypeRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(beerTypeService.createBeerType(beerTypeRequest));
  }

  @PutMapping("{beer-type-id}")
  @Secured({Roles.ADMIN, Roles.STAFF})
  public ResponseEntity<BeerTypeResponse> updateBeerType(
      @PathVariable(name = "beer-type-id") UUID beerTypeId, @RequestBody BeerTypeRequest beerTypeRequest) {
    return ResponseEntity.ok(beerTypeService.updateBeerType(beerTypeId, beerTypeRequest));
  }
}
