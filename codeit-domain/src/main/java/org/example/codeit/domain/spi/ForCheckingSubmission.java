package org.example.codeit.domain.spi;

import org.example.codeit.domain.submission.Submission;

public interface ForCheckingSubmission {
    Submission checkSubmission(Submission submission);
}
