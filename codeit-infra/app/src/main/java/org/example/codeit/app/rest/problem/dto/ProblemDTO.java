package org.example.codeit.app.rest.problem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.example.codeit.app.rest.View;
import org.example.codeit.domain.problem.Problem;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@JsonView(View.Default.class)
public class ProblemDTO {
    @JsonProperty(value = "id", index = 1)
    private Long id;
    @JsonProperty(value = "title", index = 2)
    private String title;
    @JsonProperty(value = "body", index = 3)
    private String body;
    @JsonProperty(value = "category", index = 4)
    private CategoryDTO categoryDTO;
    @JsonProperty(value = "difficulty", index = 5)
    private int difficulty;
    @JsonView(View.Privileged.class)
    @JsonProperty(value = "input", index = 6)
    private String input;
    @JsonView(View.Privileged.class)
    @JsonProperty(value = "expected_output", index = 7)
    private String expectedOutput;
    @JsonProperty(value = "createdAt", index = 8)
    private LocalDateTime createdAt;

    public static ProblemDTO fromDomainProblem(Problem problem) {
        return new ProblemDTO(
                problem.getId(),
                problem.getTitle(),
                problem.getBody(),
                CategoryDTO.fromDomainCategory(problem.getCategory()),
                problem.getDifficulty(),
                problem.getInput(),
                problem.getExpectedOutput(),
                problem.getCreatedAt()
        );
    }

    public Problem toDomainProblem() {
        return new Problem(
                this.getId(),
                this.getTitle(),
                this.getBody(),
                this.getCategoryDTO().toDomainCategory(),
                this.getDifficulty(),
                this.getInput(),
                this.getExpectedOutput(),
                this.getCreatedAt() == null ? LocalDateTime.now() : this.getCreatedAt()
        );
    }
}
