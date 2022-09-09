package org.example.codeit.app.jpa.submission.entities;

import hexarch.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.codeit.app.jpa.problem.entities.ProblemEntity;
import org.example.codeit.app.jpa.profile.entities.ProfileEntity;
import org.example.codeit.domain.problem.Problem;
import org.example.codeit.domain.profile.Profile;
import org.example.codeit.domain.submission.Language;
import org.example.codeit.domain.submission.Submission;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "submission")
public class SubmissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "source_code", nullable = false)
    private String sourceCode;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "language_id", nullable = false)
    private LanguageEntity language;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_id", nullable = false)
    private ProfileEntity profile;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "problem_id", nullable = false)
    private ProblemEntity problem;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "runtime")
    private double runtime;
    @Column(name = "memory")
    private double memory;

    public static SubmissionEntity fromDomainSubmission(Submission submission) {
        return new SubmissionEntity(
                submission.getId(),
                submission.getSourceCode(),
                LanguageEntity.fromDomainLanguage(submission.getLanguage()),
                ProfileEntity.fromDomainProfile(submission.getProfile()),
                ProblemEntity.fromDomainProblem(submission.getProblem()),
                submission.getStatus(),
                submission.getRuntime(),
                submission.getMemory()
        );
    }

    public Submission toDomainSubmission() {
        return new Submission(
                this.getId(),
                this.getSourceCode(),
                this.getLanguage().toDomainLanguage(),
                this.getProfile().toDomainProfile(),
                this.getProblem().toDomainProblem(),
                this.getStatus(),
                this.getRuntime(),
                this.getMemory()
        );
    }
}
