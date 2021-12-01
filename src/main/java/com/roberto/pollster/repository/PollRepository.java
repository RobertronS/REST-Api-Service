package com.roberto.pollster.repository;

import com.roberto.pollster.model.Poll;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PollRepository extends CrudRepository<Poll, Long> {
    List<Poll> findByPollName(@Param("name") String name, Pageable pageable);
    List<Poll> findAll(Pageable pageable);
}
