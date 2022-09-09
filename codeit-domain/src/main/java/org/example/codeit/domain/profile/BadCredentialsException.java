package org.example.codeit.domain.profile;

public class BadCredentialsException extends RuntimeException {
    private final static String msg = "A profile with this username and password does not exist.";

    public BadCredentialsException() {
        super(msg);
    }
}
