package org.example.codeit.domain.spi.stubs;

import hexarch.AccessDeniedException;
import org.example.codeit.domain.profile.Profile;
import org.example.codeit.domain.submission.Submission;

public class TestContext {
    public Submission submission;
    public Profile profile;
    public Exception exception;
}
