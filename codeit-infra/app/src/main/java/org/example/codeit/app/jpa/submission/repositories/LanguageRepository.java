package org.example.codeit.app.jpa.submission.repositories;

import org.example.codeit.app.jpa.submission.entities.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<LanguageEntity, Long> {
    LanguageEntity findByName(String name);
}
