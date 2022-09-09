package org.example.codeit.app.rest.problem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.example.codeit.app.rest.View;
import org.example.codeit.domain.problem.Category;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter

@JsonView(View.Default.class)
public class CategoryDTO {
    @JsonProperty(value = "id", index = 1)
    private Long id;
    @JsonProperty(value = "name", index = 2)
    private String name;

    public static CategoryDTO fromDomainCategory(Category category) {
        return new CategoryDTO(category.getId(), category.getName());
    }

    public Category toDomainCategory() {
        return new Category(this.getId(), this.getName());
    }
}
