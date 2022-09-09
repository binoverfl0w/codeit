package org.example.codeit.app.jpa.profile.repositories;

import org.example.codeit.app.jpa.profile.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}
