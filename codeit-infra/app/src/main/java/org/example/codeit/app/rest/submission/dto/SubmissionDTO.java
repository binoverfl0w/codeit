package org.example.codeit.app.rest.submission.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import hexarch.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.codeit.app.rest.View;
import org.example.codeit.app.rest.problem.dto.ProblemDTO;
import org.example.codeit.app.rest.profile.dto.ProfileDTO;
import org.example.codeit.domain.problem.Problem;
import org.example.codeit.domain.profile.Profile;
import org.example.codeit.domain.submission.Language;
import org.example.codeit.domain.submission.Submission;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter

@JsonView(View.Default.class)
public class SubmissionDTO {
    @JsonProperty(value = "id", index = 1)
    private Long id;
    @JsonProperty(value = "source_code", index = 2)
    private String sourceCode;
    @JsonProperty(value = "language", index = 3)
    private LanguageDTO language;
    @JsonProperty(value = "profile", index = 4)
    private ProfileDTO profile;
    @JsonProperty(value = "problem", index = 5)
    private ProblemDTO problem;
    @JsonProperty(value = "status", index = 6)
    private String status;
    @JsonProperty(value = "runtime", index = 7)
    private double runtime;
    @JsonProperty(value = "memory", index = 8)
    private double memory;
    @JsonProperty(value = "language_id", access = JsonProperty.Access.WRITE_ONLY)
    private Long languageId;

    public SubmissionDTO(Long id, String sourceCode, LanguageDTO language, ProfileDTO profile, ProblemDTO problem, String status, double runtime, double memory) {
        this.id = id;
        this.sourceCode = sourceCode;
        this.language = language;
        this.profile = profile;
        this.problem = problem;
        this.status = status;
        this.runtime = runtime;
        this.memory = memory;
    }

    public static SubmissionDTO fromDomainSubmission(Submission submission) {
        return new SubmissionDTO(
                submission.getId(),
                submission.getSourceCode(),
                LanguageDTO.fromDomainLanguage(submission.getLanguage()),
                ProfileDTO.fromDomainProfile(submission.getProfile()),
                ProblemDTO.fromDomainProblem(submission.getProblem()),
                submission.getStatus(),
                submission.getRuntime(),
                submission.getMemory()
        );
    }

    public Submission toDomainSubmission() {
        return new Submission(
                this.getId(),
                this.getSourceCode(),
                this.getLanguage() == null ? null : this.getLanguage().toDomainLanguage(),
                this.getProfile() == null ? null : this.getProfile().toDomainProfile(),
                this.getProblem() == null ? null : this.getProblem().toDomainProblem(),
                this.getStatus(),
                this.getRuntime(),
                this.getMemory()
        );
    }
}
