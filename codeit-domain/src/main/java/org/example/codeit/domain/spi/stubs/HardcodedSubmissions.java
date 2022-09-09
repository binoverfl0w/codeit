package org.example.codeit.domain.spi.stubs;

import hexarch.Constants.*;
import org.example.codeit.domain.spi.ForCheckingSubmission;
import org.example.codeit.domain.submission.Submission;

import java.util.HashMap;
import java.util.random.RandomGenerator;

public class HardcodedSubmissions implements ForCheckingSubmission {

    HashMap<Long, Submission> submissions = new HashMap<>();

    @Override
    public Submission checkSubmission(Submission submission) {
        submission.setStatus(STATUS.ACCEPTED);
        submission.setMemory(1000);
        submission.setRuntime(1000);
        return submission;
    }
}
