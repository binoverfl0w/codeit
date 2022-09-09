package org.example.codeit.domain.submission;

import hexarch.Constants.*;
import org.example.codeit.domain.problem.Problem;
import org.example.codeit.domain.profile.Profile;

public class Submission {
    private Long id;
    private String sourceCode;
    private Language language;
    private Profile profile;
    private Problem problem;
    private String status = STATUS.PENDING;
    private double runtime;
    private double memory;

    public Submission(Long id, String sourceCode, Language language, Profile profile, Problem problem, String status, double runtime, double memory) {
        this.id = id;
        this.sourceCode = sourceCode;
        this.language = language;
        this.profile = profile;
        this.problem = problem;
        this.status = status;
        this.runtime = runtime;
        this.memory = memory;
    }

    public Submission(Long id, String sourceCode, Language language, Profile profile, Problem problem, double runtime, double memory) {
        this.id = id;
        this.sourceCode = sourceCode;
        this.language = language;
        this.profile = profile;
        this.problem = problem;
        this.runtime = runtime;
        this.memory = memory;
    }

    public Submission(String sourceCode, Language language, Profile profile, Problem problem, double runtime, double memory) {
        this.sourceCode = sourceCode;
        this.language = language;
        this.profile = profile;
        this.problem = problem;
        this.runtime = runtime;
        this.memory = memory;
    }

    public Submission(String  sourceCode, Language language, Profile profile, Problem problem) {
        this.sourceCode = sourceCode;
        this.language = language;
        this.profile = profile;
        this.problem = problem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getRuntime() {
        return runtime;
    }

    public void setRuntime(double runtime) {
        this.runtime = runtime;
    }

    public double getMemory() {
        return memory;
    }

    public void setMemory(double memory) {
        this.memory = memory;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
