package org.example.codeit.app.rest.submission;

import org.example.codeit.app.rest.Response;
import org.example.codeit.app.rest.submission.dto.SubmissionDTO;
import org.example.codeit.domain.api.ForManagingProblem;
import org.example.codeit.domain.api.ForManagingProfile;
import org.example.codeit.domain.api.ForSubmittingCode;
import org.example.codeit.domain.problem.Problem;
import org.example.codeit.domain.profile.Profile;
import org.example.codeit.domain.spi.AuthenticationContext;
import org.example.codeit.domain.submission.Language;
import org.example.codeit.domain.submission.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubmissionController {

    private ForSubmittingCode codeSubmitter;

    @Autowired
    public SubmissionController(ForSubmittingCode codeSubmitter) {
        this.codeSubmitter = codeSubmitter;
    }

    @PostMapping("/problems/{id}/submit")
    public ResponseEntity<Object> submit(@PathVariable Long id, @RequestBody SubmissionDTO submissionDTO) {
        Language language = codeSubmitter.getLanguage(submissionDTO.getLanguageId());
        Profile profile = codeSubmitter.getAuthenticatedProfile();
        Problem problem = codeSubmitter.getProblem(id);
        Submission submission = submissionDTO.toDomainSubmission();
        submission.setLanguage(language);
        submission.setProfile(profile);
        submission.setProblem(problem);
        return Response.handlePost(
                SubmissionDTO.fromDomainSubmission(codeSubmitter.submit(submission))
        );
    }
}
