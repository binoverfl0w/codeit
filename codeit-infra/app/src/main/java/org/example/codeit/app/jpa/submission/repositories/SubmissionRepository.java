package org.example.codeit.app.jpa.submission.repositories;

import org.example.codeit.app.jpa.submission.entities.SubmissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface SubmissionRepository extends JpaRepository<SubmissionEntity, Long> {
    List<SubmissionEntity> findByProfileIdAndProblemId(Long profileId, Long problemId);
}
