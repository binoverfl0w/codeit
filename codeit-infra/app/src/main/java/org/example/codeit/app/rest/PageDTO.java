package org.example.codeit.app.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class PageDTO {
    @JsonProperty(value = "totalItems", index = 2)
    private Long totalItems;

    @JsonProperty(value = "currentPage", index = 3)
    private Integer currentPage;

    @JsonProperty(value = "totalPages", index = 4)
    private Integer totalPages;
}
