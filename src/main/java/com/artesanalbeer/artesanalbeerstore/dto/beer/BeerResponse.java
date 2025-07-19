package com.artesanalbeer.artesanalbeerstore.dto.beer;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeerResponse {
    private UUID id;
    private String name;
    private String description;
    private BeerTypeResponse beerType;
    private LocalDate releasedAt;
    private Integer alcoholPercentage;
    private String pictureUrl;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
