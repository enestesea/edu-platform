package org.example.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.example.entities.AnswerOption;

import java.util.List;
import java.util.UUID;

public interface AnswerOptionRepository extends JpaRepository<AnswerOption, UUID> {
    List<AnswerOption> findByQuestionId(UUID questionId);
}
