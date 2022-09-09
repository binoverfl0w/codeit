package org.example.codeit.domain.profile;

import hexarch.ResourceNotFoundException;

public class PermissionNotFoundException extends ResourceNotFoundException {
    public PermissionNotFoundException(String attribute, String value) {
        super("Permission", attribute, value);
    }
}
