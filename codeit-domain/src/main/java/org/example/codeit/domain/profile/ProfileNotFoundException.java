package org.example.codeit.domain.profile;

import hexarch.ResourceNotFoundException;

public class ProfileNotFoundException extends ResourceNotFoundException {

    public ProfileNotFoundException(String attribute, String value) {
        super("Profile",attribute,value);
    }
}
