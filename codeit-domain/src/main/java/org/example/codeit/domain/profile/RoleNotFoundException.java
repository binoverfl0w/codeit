package org.example.codeit.domain.profile;

import hexarch.ResourceNotFoundException;

public class RoleNotFoundException extends ResourceNotFoundException {

    public RoleNotFoundException(String attribute, String value) {
        super("Role", attribute, value);
    }
}
