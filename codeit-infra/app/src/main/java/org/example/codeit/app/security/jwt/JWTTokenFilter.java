package org.example.codeit.app.security.jwt;

import org.example.codeit.app.jpa.profile.entities.ProfileEntity;
import org.example.codeit.domain.profile.Profile;
import org.example.codeit.domain.spi.ForStoringProfile;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JWTTokenFilter extends OncePerRequestFilter {

    private final JWTTokenUtil jwtTokenUtil;
    private final ForStoringProfile profileStore;

    public JWTTokenFilter(JWTTokenUtil jwtTokenUtil,
                          ForStoringProfile profileStore) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.profileStore = profileStore;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();
        if (!jwtTokenUtil.validateToken(token)) {
            chain.doFilter(request, response);
            return;
        }

        // Get user identity and set it on the spring security context
        Optional<Profile> profileDomain = profileStore
                .findProfileByUsernameOrEmail(jwtTokenUtil.getUsernameFromToken(token));
        ProfileEntity profile = null;
        if (profileDomain.isPresent()) profile = profileDomain.map(ProfileEntity::fromDomainProfile).get();
        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(
                profile, null,
                profile == null ?
                        List.of() : profile.getAuthorities()
        );
        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

}

