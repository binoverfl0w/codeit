package org.example.codeit.app.rest.problem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.codeit.app.rest.PageDTO;
import org.example.codeit.app.rest.View;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter

@JsonView(View.Default.class)
public class CategoriesDTO extends PageDTO {
    @JsonProperty(value = "categories", index = 1)
    private List<CategoryDTO> categories = new ArrayList<>();
}
