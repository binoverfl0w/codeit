package org.example.codeit.domain.submission;

import hexarch.ResourceNotFoundException;

public class LanguageNotFoundException extends ResourceNotFoundException {
    public LanguageNotFoundException(String attribute, String value) {
        super("Lagnuage", attribute, value);
    }
}
