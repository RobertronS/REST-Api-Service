package com.roberto.pollster;

import com.roberto.pollster.model.Poll;
import com.roberto.pollster.repository.PollRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/poll", produces = "application/json")
@CrossOrigin(origins = "*")
public class PollController {

    private final PollRepository pollRepository;

    public PollController(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    @GetMapping
    public ResponseEntity<List<Poll>> getAllPolls(@RequestParam(required = false) String name, @RequestParam(required = false) Integer page) {
        List<Poll> pollList = new ArrayList<>();
        try {
            if (page == null || page < 0) page = 0;
            PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("pollId").descending());
            if (name == null) {
                pollList.addAll(pollRepository.findAll(pageRequest));
            } else {
                pollList.addAll(pollRepository.findByPollName(name, pageRequest));
            }

            return new ResponseEntity<>(pollList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Poll> getTutorialById(@PathVariable("id") long id) {
        Optional<Poll> poll = pollRepository.findById(id);

        return poll.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Poll> createPoll(@RequestBody Poll poll) {
        try {
            Poll _poll = pollRepository.save(poll);
            return new ResponseEntity<>(_poll, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Poll> updatePoll(@PathVariable("id") long id, @RequestBody Poll poll) {
        Optional<Poll> pollData = pollRepository.findById(id);

        if (pollData.isPresent()) {
            Poll _poll = pollData.get();
            _poll.setPollName(poll.getPollName());
            _poll.setStartDate(poll.getStartDate());
            _poll.setCompletionDate(poll.getCompletionDate());
            _poll.setIsActive(poll.getIsActive());
            return new ResponseEntity<>(pollRepository.save(_poll), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePoll(@PathVariable("id") long id) {
        try {
            pollRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
