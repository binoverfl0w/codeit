package org.example.codeit.domain.problem;

import hexarch.ResourceNotFoundException;

public class ProblemNotFoundException extends ResourceNotFoundException {
    public ProblemNotFoundException(String attribute, String value) {
        super("Problem", attribute, value);
    }
}
