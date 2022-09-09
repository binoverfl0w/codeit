package org.example.codeit.domain.api;

import org.example.codeit.domain.problem.Problem;
import org.example.codeit.domain.profile.Profile;
import org.example.codeit.domain.submission.Language;
import org.example.codeit.domain.submission.Submission;

public interface ForSubmittingCode {
    Language getLanguage(Long id);
    Profile getAuthenticatedProfile();
    Problem getProblem(Long id);
    Submission submit(Submission submission);
}
