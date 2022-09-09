package org.example.codeit.app.jpa.profile.repositories;

import org.example.codeit.app.jpa.profile.entities.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
    ProfileEntity findByUsernameOrEmail(String username, String username1);
    ProfileEntity findByUsername(String username);
}
