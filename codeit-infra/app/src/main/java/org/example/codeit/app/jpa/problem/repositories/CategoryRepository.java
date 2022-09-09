package org.example.codeit.app.jpa.problem.repositories;

import org.example.codeit.app.jpa.problem.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    CategoryEntity findByName(String name);
}
