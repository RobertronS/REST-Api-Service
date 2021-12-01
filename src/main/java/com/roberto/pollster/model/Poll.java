package com.roberto.pollster.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
public class Poll {
    @NotNull
    public String pollName;
    @NotNull
    public Date startDate;
    public Date completionDate;
    public Boolean isActive;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pollId;
    @OneToMany(mappedBy = "poll")
    private List<Question> questions;

    public Poll(String pollName, Date startDate, Date completionDate, Boolean isActive) {
        this.pollName = pollName;
        this.startDate = startDate;
        this.completionDate = completionDate;
//        this.isActive = isActive;
    }

    public Poll() {
    }

    public Long getPollId() {
        return pollId;
    }

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    public String getPollName() {
        return pollName;
    }

    public void setPollName(String pollName) {
        this.pollName = pollName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Poll{" +
                "pollId=" + pollId +
                ", pollName='" + pollName + '\'' +
                ", startDate=" + startDate +
                ", completionDate=" + completionDate +
                ", isActive=" + isActive +
                ", questions=" + questions +
                '}';
    }
}
