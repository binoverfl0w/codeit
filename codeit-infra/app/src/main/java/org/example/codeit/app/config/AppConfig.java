package org.example.codeit.app.config;

import org.example.codeit.app.jpa.problem.ProblemStore;
import org.example.codeit.app.jpa.problem.repositories.CategoryRepository;
import org.example.codeit.app.jpa.problem.repositories.ProblemRepository;
import org.example.codeit.app.jpa.submission.SubmissionStore;
import org.example.codeit.app.jpa.submission.repositories.LanguageRepository;
import org.example.codeit.app.jpa.submission.repositories.SubmissionRepository;
import org.example.codeit.app.judge0.SubmissionChecker;
import org.example.codeit.app.security.AuthenticationContextImpl;
import org.example.codeit.app.jpa.profile.CombinedProfileInterface;
import org.example.codeit.app.jpa.profile.ProfileStore;
import org.example.codeit.app.jpa.profile.repositories.PermissionRepository;
import org.example.codeit.app.jpa.profile.repositories.ProfileRepository;
import org.example.codeit.app.jpa.profile.repositories.RoleRepository;
import org.example.codeit.domain.CodeSubmitter;
import org.example.codeit.domain.api.ForManagingProblem;
import org.example.codeit.domain.api.ForManagingProfile;
import org.example.codeit.domain.api.ForManagingSubmission;
import org.example.codeit.domain.api.ForSubmittingCode;
import org.example.codeit.domain.problem.ProblemManager;
import org.example.codeit.domain.profile.ProfileManager;
import org.example.codeit.domain.spi.*;
import org.example.codeit.domain.submission.Language;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public CombinedProfileInterface profileStore(ProfileRepository profileRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) { return new ProfileStore(profileRepository, roleRepository, permissionRepository); }

    @Bean
    public ForManagingProfile profileManager(ForStoringProfile profileStore, AuthenticationContext authenticationContext) { return new ProfileManager(profileStore, authenticationContext); }

    @Bean
    public ForStoringProblem problemStore(ProblemRepository problemRepository, CategoryRepository categoryRepository) { return new ProblemStore(problemRepository, categoryRepository); }

    @Bean
    public ForManagingProblem problemManager(ForStoringProblem problemStore, AuthenticationContext authenticationContext) { return new ProblemManager(problemStore, authenticationContext); }

    @Bean
    public ForSubmittingCode codeSubmitter(ForStoringProfile profileStore, ForStoringProblem problemStore, ForStoringSubmission submissionStore, ForCheckingSubmission submissionChecker, AuthenticationContext authenticationContext) { return new CodeSubmitter(profileStore, problemStore, submissionStore, submissionChecker, authenticationContext); }

    @Bean
    public ForCheckingSubmission submissionChecker() { return new SubmissionChecker(); }

    @Bean
    public ForStoringSubmission submissionStore(SubmissionRepository submissionRepository, LanguageRepository languageRepository) { return new SubmissionStore(submissionRepository, languageRepository); }

    @Bean
    public AuthenticationContext authContext() { return new AuthenticationContextImpl(); }
}
