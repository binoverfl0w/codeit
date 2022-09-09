package org.example.codeit.domain.spi;

import org.example.codeit.domain.profile.Profile;

public interface AuthenticationContext {
    void login(String username, String password);
    void setAuthenticatedProfile(Profile profile);
    Profile getAuthenticatedProfile();
    boolean ensurePermissions(String ...permissions);
}
