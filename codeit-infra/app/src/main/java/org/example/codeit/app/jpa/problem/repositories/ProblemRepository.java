package org.example.codeit.app.jpa.problem.repositories;

import org.example.codeit.app.jpa.problem.entities.ProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<ProblemEntity, Long> {
}
