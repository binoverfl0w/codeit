package org.example.codeit.domain.problem;

import hexarch.ResourceNotFoundException;

public class CategoryNotFoundException extends ResourceNotFoundException {
    public CategoryNotFoundException(String attribute, String value) {
        super("Category", attribute, value);
    }
}
