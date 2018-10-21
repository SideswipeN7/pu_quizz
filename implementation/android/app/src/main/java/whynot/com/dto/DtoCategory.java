package whynot.com.dto;

import java.util.List;

public class DtoCategory {
    private String name;
    private List<DtoQuestion> questions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DtoQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<DtoQuestion> questions) {
        this.questions = questions;
    }
}
