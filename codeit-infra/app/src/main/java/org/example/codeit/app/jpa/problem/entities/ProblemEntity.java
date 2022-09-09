package org.example.codeit.app.jpa.problem.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.codeit.domain.problem.Problem;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "problem")
public class ProblemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "body", nullable = false)
    private String body;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;
    @Column(name = "difficulty", nullable = false)
    private int difficulty;
    @Column(name = "input", nullable = false)
    private String input;
    @Column(name = "expected_output", nullable = false)
    private String expectedOutput;
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    public static ProblemEntity fromDomainProblem(Problem problem) {
        return new ProblemEntity(
                problem.getId(),
                problem.getTitle(),
                problem.getBody(),
                CategoryEntity.fromDomainCategory(problem.getCategory()),
                problem.getDifficulty(), problem.getInput(),
                problem.getExpectedOutput(),
                problem.getCreatedAt()
        );
    }

    public Problem toDomainProblem() {
        return new Problem(
                this.getId(),
                this.getTitle(),
                this.getBody(),
                this.getCategory().toDomainCategory(),
                this.getDifficulty(),
                this.getInput(),
                this.getExpectedOutput(),
                this.getCreatedAt()
        );
    }
}
