package com.capco.slack.faq.setup.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Bhushan on 6/19/2017.
 */
public class FaqContainer {
    private List<Question> questions;
    private Set<String> dropWords;

    public FaqContainer(){
        this.questions = new ArrayList<>();
        this.dropWords = new HashSet<>();
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public void addDropWord(String dropWord) {
        this.dropWords.add(dropWord);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Set<String> getDropWords() {
        return dropWords;
    }

    @Override
    public String toString() {
        return "FaqContainer{" +
                "questions=" + questions +
                ", dropWords=" + dropWords +
                '}';
    }
}
