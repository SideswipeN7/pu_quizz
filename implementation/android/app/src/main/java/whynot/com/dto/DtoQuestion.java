package whynot.com.dto;

import java.util.List;

public class DtoQuestion {
    private String text;
    private List<DtoAnswer> answers;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<DtoAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<DtoAnswer> answers) {
        this.answers = answers;
    }
}// class DtoQuestion
