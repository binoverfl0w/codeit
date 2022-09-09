package org.example.codeit.app.jpa.submission;

import hexarch.AbstractPage;
import hexarch.AbstractPageImpl;
import org.example.codeit.app.jpa.submission.entities.LanguageEntity;
import org.example.codeit.app.jpa.submission.entities.SubmissionEntity;
import org.example.codeit.app.jpa.submission.repositories.LanguageRepository;
import org.example.codeit.app.jpa.submission.repositories.SubmissionRepository;
import org.example.codeit.domain.spi.ForStoringSubmission;
import org.example.codeit.domain.submission.Language;
import org.example.codeit.domain.submission.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SubmissionStore implements ForStoringSubmission {

    private SubmissionRepository submissionRepository;
    private LanguageRepository languageRepository;

    @Autowired
    public SubmissionStore(SubmissionRepository submissionRepository, LanguageRepository languageRepository) {
        this.submissionRepository = submissionRepository;
        this.languageRepository = languageRepository;
    }

    @Override
    public AbstractPage<Submission> getSubmissionsPage(int page, int size) {
        Page<SubmissionEntity> submissionsPage = submissionRepository.findAll(PageRequest.of(page, size));
        return new AbstractPageImpl<>(
                submissionsPage.getTotalElements(),
                submissionsPage.getTotalPages(),
                submissionsPage.getNumber(),
                submissionsPage.getContent().stream().map(SubmissionEntity::toDomainSubmission).collect(Collectors.toList())
        );
    }

    @Override
    public Optional<Submission> getSubmissionById(Long id) {
        return submissionRepository.findById(id).map(SubmissionEntity::toDomainSubmission);
    }

    @Override
    public Submission storeSubmission(Submission submission) {
        return submissionRepository.save(SubmissionEntity.fromDomainSubmission(submission)).toDomainSubmission();
    }

    @Override
    public List<Submission> getExistingSubmission(Long profileId, Long problemId) {
        return submissionRepository.findByProfileIdAndProblemId(profileId, problemId).stream().map(SubmissionEntity::toDomainSubmission).collect(Collectors.toList());
    }

    @Override
    public void deleteSubmissionById(Long id) {
        submissionRepository.deleteById(id);
    }

    @Override
    public AbstractPage<Language> getLanguagesPage(int page, int size) {
        Page<LanguageEntity> languagesPage = languageRepository.findAll(PageRequest.of(page, size));
        return new AbstractPageImpl<>(
                languagesPage.getTotalElements(),
                languagesPage.getTotalPages(),
                languagesPage.getNumber(),
                languagesPage.getContent().stream().map(LanguageEntity::toDomainLanguage).collect(Collectors.toList())
        );
    }

    @Override
    public Optional<Language> getLanguageById(Long id) {
        return languageRepository.findById(id).map(LanguageEntity::toDomainLanguage);
    }

    @Override
    public Optional<Language> getLanguageByName(String name) {
        return Optional.of(languageRepository.findByName(name).toDomainLanguage());
    }

    @Override
    public Language storeLanguage(Language language) {
        return languageRepository.save(LanguageEntity.fromDomainLanguage(language)).toDomainLanguage();
    }

    @Override
    public void deleteLanguageById(Long id) {
        languageRepository.deleteById(id);
    }
}
