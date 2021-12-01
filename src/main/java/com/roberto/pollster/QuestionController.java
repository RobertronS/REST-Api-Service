package com.roberto.pollster;

import com.roberto.pollster.model.Poll;
import com.roberto.pollster.model.Question;
import com.roberto.pollster.repository.PollRepository;
import com.roberto.pollster.repository.QuestionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/question", produces = "application/json")
@CrossOrigin(origins = "*")
public class QuestionController {

    private final QuestionRepository questionRepository;
    private final PollRepository pollRepository;

    public QuestionController(QuestionRepository questionRepository, PollRepository pollRepository) {
        this.questionRepository = questionRepository;
        this.pollRepository = pollRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<Question>> getAllQuestions(@RequestParam(required = false) Integer page) {
        List<Question> questionList = new ArrayList<>();
        try {
            if (page == null || page < 0) {
                page = 0;
            }
            System.out.println("page: " + page);
            PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("questionId").descending());
            questionList.addAll(questionRepository.findAll(pageRequest));
            return new ResponseEntity<>(questionList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Question> getTutorialById(@PathVariable("id") long id) {
        Optional<Question> question = questionRepository.findById(id);

        return question.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable("id") long id, @RequestBody Question question) {
        Optional<Question> questionData = questionRepository.findById(id);

        if (questionData.isPresent()) {
            Question _question = questionData.get();
            _question.setQuestionText(question.getQuestionText());
            _question.setDisplayOrder(question.getDisplayOrder());
            if (question.getPoll() != null) {
                Optional<Poll> _poll = pollRepository.findById(question.getPoll().getPollId());
                _poll.ifPresent(_question::setPoll);
            }
            return new ResponseEntity<>(questionRepository.save(_question), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Question> createQuestion(@RequestBody @Valid Question question) {
        System.out.println(question);
        try {
            Optional<Poll> poll = pollRepository.findById(question.getPoll().getPollId());
            if (!poll.isPresent()) {
                return ResponseEntity.unprocessableEntity().build();
            }
            question.setPoll(poll.get());
            Question _question = questionRepository.save(question);
            return new ResponseEntity<>(_question, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteQuestion(@PathVariable("id") long id) {
        try {
            questionRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
