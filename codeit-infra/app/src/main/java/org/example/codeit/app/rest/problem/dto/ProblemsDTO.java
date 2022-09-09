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
@Setter

@JsonView(View.Default.class)
public class ProblemsDTO extends PageDTO {
    @JsonProperty(value = "problems", index = 1)
    private List<ProblemDTO> problems = new ArrayList<>();
}
