package org.example.codeit.app.judge0;

import hexarch.Constants.*;
import org.example.codeit.domain.spi.ForCheckingSubmission;
import org.example.codeit.domain.submission.Submission;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;

public class SubmissionChecker implements ForCheckingSubmission {

    private final RestTemplate restTemplate = new RestTemplateBuilder().build();

    @Override
    public Submission checkSubmission(Submission submission) {
        String endpoint = "http://localhost:2358/submissions";
        SubmissionJudgeDTO submissionReq = SubmissionJudgeDTO.builder()
                .sourceCode(submission.getSourceCode())
                .stdin(Base64.getEncoder().encodeToString(submission.getProblem().getInput().getBytes()))
                .expectedOutput(Base64.getEncoder().encodeToString(submission.getProblem().getExpectedOutput().getBytes()))
                .languageId(Math.toIntExact(submission.getLanguage().getId()))
                .build();
        SubmissionJudgeDTO submissionJudgeDTO = this.restTemplate.postForEntity(endpoint + "?base64_encoded=true&wait=true", submissionReq, SubmissionJudgeDTO.class).getBody();
        submission.setSourceCode(new String(Base64.getDecoder().decode(submission.getSourceCode().getBytes()), StandardCharsets.UTF_8));
        if (submissionJudgeDTO != null) {
            String token = submissionJudgeDTO.getToken();
            submissionJudgeDTO = this.restTemplate.getForEntity(endpoint + "/" + token + "?base64_encoded=true&fields=stdout,stderr,status,language_id,memory,time", SubmissionJudgeDTO.class).getBody();
            if (submissionJudgeDTO != null) {
                String desc = submissionJudgeDTO.getStatus().getDescription();
                System.out.println(desc);
                if (desc.equals("Accepted")) {
                    submission.setStatus(STATUS.ACCEPTED);
                    submission.setRuntime(submissionJudgeDTO.getTime());
                    submission.setMemory(submissionJudgeDTO.getMemory());
                } else {
                    submission.setStatus(STATUS.WRONG);
                }
            }
        } else {
            submission.setStatus(STATUS.FAILED);
        }
        return submission;
    }

}
