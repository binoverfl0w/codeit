package org.example.codeit.domain.spi;

import hexarch.AbstractPage;
import org.example.codeit.domain.submission.Language;
import org.example.codeit.domain.submission.Submission;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ForStoringSubmission {
    AbstractPage<Submission> getSubmissionsPage(int page, int size);

    Optional<Submission> getSubmissionById(Long id);

    Submission storeSubmission(Submission submission);

    List<Submission> getExistingSubmission(Long profileId, Long problemId);

    void deleteSubmissionById(Long id);

    AbstractPage<Language> getLanguagesPage(int page, int size);

    Optional<Language> getLanguageById(Long id);

    Optional<Language> getLanguageByName(String name);

    Language storeLanguage(Language language);

    void deleteLanguageById(Long id);
}
