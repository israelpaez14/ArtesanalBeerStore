package com.artesanalbeer.artesanalbeerstore.dto.beer;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeerRequest {
    private String name;
    private String description;
    private BeerTypeResponse beerType;
    private LocalDate releasedAt;
    private Integer alcoholPercentage;
}
