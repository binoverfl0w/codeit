package org.example.codeit.domain.spi.stubs;

import hexarch.AbstractPage;
import hexarch.AbstractPageImpl;
import org.example.codeit.domain.spi.ForStoringSubmission;
import org.example.codeit.domain.submission.Language;
import org.example.codeit.domain.submission.Submission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

public class InMemorySubmissionStore implements ForStoringSubmission {
    HashMap<Long, Submission> submissions = new HashMap<>();
    HashMap<Long, Language> languages = new HashMap<>();

    public InMemorySubmissionStore() {
        // hardcoded
        languages.put((long) 1, new Language((long) 1,"C++"));
    }

    @Override
    public AbstractPage<Submission> getSubmissionsPage(int page, int size) {
        return new AbstractPageImpl<>(
                submissions.size(),
                submissions.size() / size,
                page,
                submissions.values().stream().skip((long) page * size).limit(size).collect(Collectors.toList())
        );
    }

    @Override
    public Optional<Submission> getSubmissionById(Long id) {
        return Optional.of(submissions.get(id));
    }

    @Override
    public Submission storeSubmission(Submission submission) {
        if (submission.getId() == null) {
            Long id = RandomGenerator.getDefault().nextLong();
            submission.setId(id);
        }
        submissions.put(submission.getId(), submission);
        return submissions.get(submission.getId());
    }

    @Override
    public List<Submission> getExistingSubmission(Long profileId, Long problemId) {
        return submissions.values().stream().filter(s -> s.getProfile().getId().equals(profileId) && s.getProblem().getId().equals(problemId)).collect(Collectors.toList());
    }

    @Override
    public void deleteSubmissionById(Long id) {
        submissions.remove(id);
    }

    @Override
    public AbstractPage<Language> getLanguagesPage(int page, int size) {
        return new AbstractPageImpl<>(
                languages.size(),
                languages.size() / size,
                page,
                languages.values().stream().skip((long) page * size).limit(size).collect(Collectors.toList())
        );
    }

    @Override
    public Optional<Language> getLanguageById(Long id) {
        return Optional.of(languages.get(id));
    }

    @Override
    public Optional<Language> getLanguageByName(String name) {
        return languages.values().stream().filter(l -> l.getName().equals(name)).findFirst();
    }

    @Override
    public Language storeLanguage(Language language) {
        if (language.getId() == null) {
            Long id = RandomGenerator.getDefault().nextLong();
            language.setId(id);
        }
        languages.put(language.getId(), language);
        return languages.get(language.getId());
    }

    @Override
    public void deleteLanguageById(Long id) {
        languages.remove(id);
    }
}
