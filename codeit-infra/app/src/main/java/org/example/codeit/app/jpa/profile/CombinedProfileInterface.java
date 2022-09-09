package org.example.codeit.app.jpa.profile;

import org.example.codeit.domain.spi.ForStoringProfile;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CombinedProfileInterface extends ForStoringProfile, UserDetailsService {
}
