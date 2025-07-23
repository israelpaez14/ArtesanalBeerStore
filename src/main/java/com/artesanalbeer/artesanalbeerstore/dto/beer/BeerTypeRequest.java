package com.artesanalbeer.artesanalbeerstore.dto.beer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BeerTypeRequest {
  @NotNull(message = "The beer type name is required")
  @NotBlank(message = "The beer type name is required")
  private String name;

  @NotNull(message = "A description for the beer type is required")
  @NotBlank(message = "A description for the beer type is required")
  private String description;
}
