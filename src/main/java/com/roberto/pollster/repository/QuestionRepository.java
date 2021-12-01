package com.roberto.pollster.repository;

import com.roberto.pollster.model.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    List<Question> findAll(Pageable pageable);
}
