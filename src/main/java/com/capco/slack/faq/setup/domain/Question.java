package com.capco.slack.faq.setup.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Created by Bhushan on 6/19/2017.
 */
public class Question {
    private String category;
    private String topic;
    private String summary;
    private String question;
    private String answer;
    private List<Synonym> synonyms;

    public Question(String category, String topic, String summary, String question, String answer, List<Synonym> synonyms) {
        this.category = category;
        this.topic = topic;
        this.summary = summary;
        this.question = question;
        this.answer = answer;
        this.synonyms = synonyms;
    }

    public String getCategory() {
        return category;
    }

    public String getTopic() {
        return topic;
    }

    public String getSummary() {
        return summary;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public List<Synonym> getSynonyms() {
        return synonyms;
    }

    public static class Synonym{
        private Set<String> synonyms;

        public Synonym(String csv) {
            synonyms = Arrays.stream(csv.split(",")).map(String::trim).collect(toSet());
        }

        public Set<String> getSynonyms() {
            return synonyms;
        }

        @Override
        public String toString() {
            return "Synonym{" +
                    "synonyms=" + synonyms +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Question{" +
                "category='" + category + '\'' +
                ", topic='" + topic + '\'' +
                ", summary='" + summary + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", synonyms=" + synonyms +
                '}';
    }
}
