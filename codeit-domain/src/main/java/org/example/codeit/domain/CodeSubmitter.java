package org.example.codeit.domain;

import hexarch.AccessDeniedException;
import hexarch.Constants.*;
import org.example.codeit.domain.api.ForSubmittingCode;
import org.example.codeit.domain.problem.Problem;
import org.example.codeit.domain.problem.ProblemNotFoundException;
import org.example.codeit.domain.profile.Profile;
import org.example.codeit.domain.spi.*;
import org.example.codeit.domain.submission.Language;
import org.example.codeit.domain.submission.LanguageNotFoundException;
import org.example.codeit.domain.submission.Submission;

import java.util.ArrayList;
import java.util.List;

public class CodeSubmitter implements ForSubmittingCode {

    private ForCheckingSubmission submissionChecker;
    private ForStoringProfile profileStore;
    private ForStoringProblem problemStore;
    private ForStoringSubmission submissionStore;
    private AuthenticationContext authenticationContext;

    public CodeSubmitter(ForStoringProfile profileStore, ForStoringProblem problemStore, ForStoringSubmission submissionStore, ForCheckingSubmission submissionChecker, AuthenticationContext authenticationContext) {
        this.profileStore = profileStore;
        this.problemStore = problemStore;
        this.submissionStore = submissionStore;
        this.submissionChecker = submissionChecker;
        this.authenticationContext = authenticationContext;
    }

    @Override
    public Language getLanguage(Long id) {
        return submissionStore.getLanguageById(id).orElseThrow(() -> new LanguageNotFoundException("id", id.toString()));
    }

    @Override
    public Profile getAuthenticatedProfile() {
        return authenticationContext.getAuthenticatedProfile();
    }

    @Override
    public Problem getProblem(Long id) {
        return problemStore.getProblemById(id).orElseThrow(() -> new ProblemNotFoundException("id", id.toString()));
    }

    @Override
    public Submission submit(Submission submission) {
        Profile profile = getAuthenticatedProfile();
        if (profile == null) throw new AccessDeniedException();
        Submission toSubmit = submissionChecker.checkSubmission(submission);
        List<Submission> existingSubmissions = submissionStore.getExistingSubmission(toSubmit.getProfile().getId(), toSubmit.getProblem().getId());
        if (toSubmit.getStatus().equals(STATUS.ACCEPTED) && existingSubmissions.stream().filter(s -> s.getStatus().equals(STATUS.ACCEPTED)).findFirst().isEmpty()) {
            profile.increaseScore(toSubmit.getProblem().getDifficulty());
            profileStore.storeProfile(profile);
        }
        return submissionStore.storeSubmission(toSubmit);
    }
}
