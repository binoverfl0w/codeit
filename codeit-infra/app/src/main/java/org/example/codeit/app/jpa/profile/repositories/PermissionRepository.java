package org.example.codeit.app.jpa.profile.repositories;

import org.example.codeit.app.jpa.profile.entities.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
    PermissionEntity findByName(String name);
}
