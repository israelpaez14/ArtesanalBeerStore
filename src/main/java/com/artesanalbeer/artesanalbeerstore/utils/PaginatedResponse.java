package com.artesanalbeer.artesanalbeerstore.utils;

import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponse<T> {
    private List<T> content;
    private int page;
    private int pageSize;
    private int totalPages;
    private int totalItems;

}
