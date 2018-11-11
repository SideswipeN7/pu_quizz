package whynot.com.app;

import whynot.com.dto.DtoAnswer;
import whynot.com.game.Game;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class App {
    private static final App ourInstance = new App();
    private static final String[]  CONNECTING = {};
    private static final String[] SENDING_DATA = {};
    private Random random;
    private Game game;


    public static App getInstance() {
        return ourInstance;
    }

    private App() {
        //Most secure random
        random = new SecureRandom(String.valueOf(System.currentTimeMillis()).getBytes());
    }

    public String getRandomConnectingText(){
        return CONNECTING[random.nextInt(CONNECTING.length)];
    }

    public String getRandomPushingDataText(){
        return SENDING_DATA[random.nextInt(SENDING_DATA.length)];
    }

    public List<DtoAnswer> randomizeAnswers(List<DtoAnswer> list){
        Collections.shuffle(list);
        return list;
    }

    public int getRandom(int bounds){
        return  random.nextInt(bounds);
    }

    public Game getGame() {
        return game;
    }

    public void resetGame() {
//        game.setCategories();
//        game.reset();
    }

    public String getCategory() {
        return game.getCategory().getName();
    }

    public String getAnswer(int i) {
        return game.getQuestion().getAnswers().get(i).getText();
    }

    public boolean checkAnswer(int index) {
        return game.getQuestion().getAnswers().get(index).isCorrect();
    }

    public void setAnswerTime(int time) {
        game.addPoints(time);
    }

    public void endGame() {
    game.end();
    }

    public int getGameTime() {
        return game.getQuestionTime();
    }

    public void NextQuestion() {
        game.nextQuestion();
    }
}
