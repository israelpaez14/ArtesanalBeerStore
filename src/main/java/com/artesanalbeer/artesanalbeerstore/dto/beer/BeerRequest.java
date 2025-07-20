package com.artesanalbeer.artesanalbeerstore.dto.beer;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeerRequest {
    private String name;
    private String description;
    private UUID beerTypeId;
    private LocalDate releasedAt;
    private Integer alcoholPercentage;
}
