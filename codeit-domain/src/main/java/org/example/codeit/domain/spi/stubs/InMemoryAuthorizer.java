package org.example.codeit.domain.spi.stubs;

import hexarch.AccessDeniedException;
import org.example.codeit.domain.profile.BadCredentialsException;
import org.example.codeit.domain.profile.Permission;
import org.example.codeit.domain.profile.Profile;
import org.example.codeit.domain.spi.AuthenticationContext;
import org.example.codeit.domain.spi.ForStoringProfile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class InMemoryAuthorizer implements AuthenticationContext {

    private Profile authenticated;
    private ForStoringProfile profileStore;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public InMemoryAuthorizer(ForStoringProfile profileStore) {
        this.profileStore = profileStore;
    }

    @Override
    public void login(String username, String password) {
        Profile toLogin = profileStore.getProfileByUsername(username).orElseThrow(BadCredentialsException::new);
        if (!passwordEncoder.matches(password, toLogin.getPassword())) throw new BadCredentialsException();
        if (!toLogin.isEnabled() || toLogin.isLocked()) throw new AccessDeniedException();
        authenticated = toLogin;
    }

    @Override
    public void setAuthenticatedProfile(Profile profile) {
        authenticated = profile;
    }

    @Override
    public Profile getAuthenticatedProfile() {
        return authenticated;
    }

    @Override
    public boolean ensurePermissions(String... permissions) {
        if (authenticated == null || authenticated.getRole() == null) return false;
        return authenticated.getRole().getPermissions().stream().map(Permission::getName).collect(Collectors.toSet()).containsAll(Arrays.stream(permissions).toList());
    }
}
