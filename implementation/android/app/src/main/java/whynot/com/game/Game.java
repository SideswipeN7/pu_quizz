package whynot.com.game;

import android.os.Build;

import java.util.Collections;
import java.util.List;

import whynot.com.app.App;
import whynot.com.dto.DtoCategory;
import whynot.com.dto.DtoQuestion;

public class Game {
    private int points;
    private List<DtoCategory> categories;
    private DtoQuestion previousQuestion;
    private DtoQuestion currentQuestion;
    private DtoCategory currentCategory;

    /******************************************************************/
    /************************ Private Methods *************************/
    /*****************************************************************/

    private void start() {
        //Shuffle data
        Collections.shuffle(categories);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            categories.forEach(q -> {
                Collections.shuffle(q.getQuestions());
                q.getQuestions().forEach(a -> Collections.shuffle(a.getAnswers()));
            });
        } else {
            for (DtoCategory c : categories) {
                Collections.shuffle(c.getQuestions());
                for (DtoQuestion q : c.getQuestions()) {
                    Collections.shuffle(q.getAnswers());
                }
            }
        }
    }

    /******************************************************************/
    /************************ Public Methods *************************/
    /*****************************************************************/

    public void nextQuestion() {
        do {
            if (categories != null) {
                currentCategory = categories.get(App.getInstance().getRandom(categories.size()));
                currentQuestion = currentCategory.getQuestions().get(App.getInstance().getRandom(categories.size() - 1));
            }
        } while (currentQuestion == previousQuestion);
        previousQuestion = currentQuestion;
    }

    public void setCategories(List<DtoCategory> categories) {
        this.categories = categories;
    }

    public int getQuestionTime() {
        return currentQuestion.getText().length() * 15;
    }

    public DtoQuestion getQuestion() {
        return currentQuestion;
    }

    public DtoCategory getCategory() {
        return currentCategory;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getPoints() {
        return points;
    }

    public void reset() {
        points = 0;
        previousQuestion = null;
        currentQuestion = null;
        nextQuestion();
    }
}// class Game
