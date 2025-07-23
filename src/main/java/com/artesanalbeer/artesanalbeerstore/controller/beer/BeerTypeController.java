package com.artesanalbeer.artesanalbeerstore.controller.beer;

import com.artesanalbeer.artesanalbeerstore.dto.beer.BeerTypeResponse;
import com.artesanalbeer.artesanalbeerstore.service.beer.BeerTypeService;
import com.artesanalbeer.artesanalbeerstore.utils.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/beer-types")
public class BeerTypeController {
  private final BeerTypeService beerTypeService;

  @GetMapping
  public ResponseEntity<PaginatedResponse<BeerTypeResponse>> getBeerTypes(
      @RequestParam(name = "page", defaultValue = "0") Integer page) {
    return ResponseEntity.ok(beerTypeService.getBeerTypes(page));
  }
}
