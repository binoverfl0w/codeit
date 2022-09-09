package org.example.codeit.domaintest.stepdefs;

import hexarch.Constants.STATUS;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.codeit.domain.api.ForSubmittingCode;
import org.example.codeit.domain.problem.Category;
import org.example.codeit.domain.problem.Problem;
import org.example.codeit.domain.profile.Profile;
import org.example.codeit.domain.profile.Role;
import org.example.codeit.domain.spi.AuthenticationContext;
import org.example.codeit.domain.spi.ForCheckingSubmission;
import org.example.codeit.domain.spi.ForStoringSubmission;
import org.example.codeit.domain.spi.stubs.TestContext;
import org.example.codeit.domain.submission.Language;
import org.example.codeit.domain.submission.Submission;

import java.util.HashSet;

public class SubmissionStepdefs {

    private AuthenticationContext authenticationContext;
    private TestContext testContext;

    private ForSubmittingCode codeSubmitter;
    private ForCheckingSubmission submissionChecker;
    private ForStoringSubmission submissionStore;

    public SubmissionStepdefs(TestContext testContext, AuthenticationContext authenticationContext, ForSubmittingCode codeSubmitter, ForCheckingSubmission submissionChecker, ForStoringSubmission submissionStore) {
        this.testContext = testContext;
        this.authenticationContext = authenticationContext;
        this.codeSubmitter = codeSubmitter;
        this.submissionChecker = submissionChecker;
        this.submissionStore = submissionStore;
    }

    @Given("^a user$")
    public void givenAUser() {
        authenticationContext.setAuthenticatedProfile(new Profile("Arjan", "arjan", "arjan@gmail.com","test",new Role("ROLE_CODER", new HashSet<>())));
        assert(authenticationContext.getAuthenticatedProfile() != null);
    }

    @When("^he submits his code$")
    public void whenHeSubmitsHisCode() {
        Submission toSubmit = new Submission("#code", new Language("C++"), authenticationContext.getAuthenticatedProfile(), new Problem("Hello World!", "Print Hello World!", new Category("I/O"), 1, "Hello World!", "Hello World!"));
        testContext.submission = codeSubmitter.submit(toSubmit);
    }

    @And("^his code is accepted$")
    public void andHisCodeIsAccepted() {
        assert(testContext.submission.getStatus().equals(STATUS.ACCEPTED));
    }

    @Then("^he receives the problem's points$")
    public void thenHeReceivesTheProblemPoints() {
        assert(authenticationContext.getAuthenticatedProfile().getScore() == testContext.submission.getProblem().getDifficulty());
    }
}
