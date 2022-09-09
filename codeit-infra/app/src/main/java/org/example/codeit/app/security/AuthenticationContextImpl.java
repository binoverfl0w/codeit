package org.example.codeit.app.security;

import org.example.codeit.app.jpa.profile.entities.ProfileEntity;
import org.example.codeit.domain.profile.Profile;
import org.example.codeit.domain.spi.AuthenticationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AuthenticationContextImpl implements AuthenticationContext {

    @Autowired private AuthenticationManager authenticationManager;
    @Override
    public void login(String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public void setAuthenticatedProfile(Profile profile) {
        SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public Profile getAuthenticatedProfile() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) return null;
        return ((ProfileEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).toDomainProfile();
    }

    @Override
    public boolean ensurePermissions(String... permissions) {
        if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) return false;
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).containsAll(Arrays.stream(permissions).toList());
    }
}
