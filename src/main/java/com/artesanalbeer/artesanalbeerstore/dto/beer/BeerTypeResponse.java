package com.artesanalbeer.artesanalbeerstore.dto.beer;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BeerTypeResponse {
  private UUID id;
  private String name;
  private String description;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
