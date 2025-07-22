package com.artesanalbeer.artesanalbeerstore.dto.beer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeerRequest {
    @NotNull(message = "The beer name is required")
    @NotBlank(message = "The beer name is required")
    private String name;

    @NotNull(message = "A description is required")
    @NotBlank(message = "A description is required")
    private String description;

    @NotNull(message = "A beer type is required")
    @NotBlank(message = "A beer type is required")
    private UUID beerTypeId;

    @NotNull(message = "A released date is required")
    @NotBlank(message = "A released date is required")
    private LocalDate releasedAt;

    @NotNull(message = "Alcohol percentage is required")
    @NotBlank(message = "Alcohol percentage is required")
    private Integer alcoholPercentage;
}
