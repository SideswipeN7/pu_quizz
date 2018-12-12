package whynot.com.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;

import whynot.com.communication.Client;
import whynot.com.dto.DtoAnswer;
import whynot.com.dto.DtoCategory;
import whynot.com.dto.DtoGameData;
import whynot.com.game.Game;
import whynot.com.quizzapp.MainActivity;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class App {
    private static final App ourInstance = new App();
    private static final String[] CONNECTING = {"Connecting Test 1", "Connecting Test 2", "Connecting Test 3"};
    private static final String[] CONNECTING_ERROR = {"Connecting ERROR 1", "Connecting ERROR 2", "Connecting ERROR 3"};
    private static final String[] SENDING_DATA = {"Sending Test 1", "Sending Test 2", "Sending Test 3"};
    private static final String[] SENDING_ERROR = {"Sending ERROR 1", "Sending ERROR 2", "Sending ERROR 3"};
    private static final String CONNECTING_TITLE = "Pobieranie danych";
    private static final String SENDING_TITLE = "Wysyłanie";
    private static final String POSITION_TITLE = "Koniec Gry";
    private static final String POSITION_TEXT = "Twoja pozycja to: ";
    private static final String OK_TEXT = "Ok!";
    private Random random_;
    private Game game_;
    private Client client_;
    private List<DtoGameData> topTenList_;
    private AlertDialog dialog_;


    public static App getInstance() {
        return ourInstance;
    }

    private App() {
        //Most secure random_
        random_ = new SecureRandom(String.valueOf(System.currentTimeMillis()).getBytes());
        game_ = new Game();
        client_ = Client.getInstance();
    }
    /******************************************************************/
    /************************ Private Methods *************************/
    /*****************************************************************/
    private String getRandomConnectingText() {
        return CONNECTING[random_.nextInt(CONNECTING.length)];
    }

    private String getRandomConnectingError() {
        return CONNECTING_ERROR[random_.nextInt(CONNECTING_ERROR.length)];
    }

    private String getRandomPushingDataText() {
        return SENDING_DATA[random_.nextInt(SENDING_DATA.length)];
    }

    private String getRandomPushingDataError() {
        return SENDING_ERROR[random_.nextInt(SENDING_ERROR.length)];
    }

    /******************************************************************/
    /************************ Public Methods *************************/
    /*****************************************************************/

    public List<DtoAnswer> randomizeAnswers(List<DtoAnswer> list) {
        Collections.shuffle(list);
        return list;
    }

    public int getRandom(int bounds) {
        return random_.nextInt(bounds);
    }

    public List<DtoGameData> getTopTen() {
        return topTenList_;
    }

    public String showTextDialog(Context context) {
        //create dialog to get user nick and return
        return "";
    }

    /******************************************************************/
    /***************** Public Game Control Methods *******************/
    /*****************************************************************/

    public Game getGame_() {
        return game_;
    }

    public void resetGame() {
//        game_.setCategories();
//        game_.reset();
    }

    public void endGame() {
        game_.end();
    }

    public String getCategory() {
        return game_.getCategory().getName();
    }

    public String getAnswer(int i) {
        return game_.getQuestion().getAnswers().get(i).getText();
    }

    public boolean checkAnswer(int index) {
        return game_.getQuestion().getAnswers().get(index).isCorrect();
    }

    public void setAnswerTime(int time) {
        game_.addPoints(time);
    }

    public int getGameTime() {
        return game_.getQuestionTime();
    }

    public void nextQuestion() {
        game_.nextQuestion();
    }

    /******************************************************************/
    /*********************** Public Client Methods *******************/
    /*****************************************************************/

    public void getData(Context context) {
        dialog_ = ProgressDialog.show(context, CONNECTING_TITLE, getRandomConnectingText(), true);
        client_.getData((List<DtoCategory> list) -> {
            dialog_.dismiss();
            game_.setCategories(list);
        }, (fail) -> {
            dialog_.dismiss();
            //show error
            dialog_ = new AlertDialog.Builder(context)
                    .setTitle(CONNECTING_TITLE)
                    .setMessage(getRandomConnectingError())
                    .setCancelable(false)
                    .setPositiveButton(OK_TEXT, (dialog, which) -> {
                        dialog.dismiss();
                    }).show();
        });
    }

    public void getTopTen(Context context) {
        dialog_ = ProgressDialog.show(context, CONNECTING_TITLE, getRandomConnectingText(), true);
        client_.getTopTen((List<DtoGameData> list) -> {
            dialog_.dismiss();
            topTenList_ = list;
        }, (fail) -> {
            dialog_.dismiss();
            //show error
            dialog_ = new AlertDialog.Builder(context)
                    .setTitle(CONNECTING_TITLE)
                    .setMessage(getRandomConnectingError())
                    .setCancelable(false)
                    .setPositiveButton(OK_TEXT, (dialog, which) -> {
                        dialog.dismiss();
                    }).show();
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void pushResult(Context context, String nick, Consumer<Void> goToMainFunc) {
        dialog_ = ProgressDialog.show(context, SENDING_TITLE, getRandomPushingDataText(), true);
        client_.pushData((Integer position) -> {
            dialog_.dismiss();
            dialog_ = new AlertDialog.Builder(context)
                    .setTitle(POSITION_TITLE)
                    .setMessage(POSITION_TEXT + position)
                    .setCancelable(false)
                    .setPositiveButton(OK_TEXT, (dialog, which) -> {
                        dialog.dismiss();
                        goToMainFunc.accept(null);
                    }).show();
        }, (fail) -> {
            dialog_.dismiss();
            //show error
            dialog_ = new AlertDialog.Builder(context)
                    .setTitle(SENDING_TITLE)
                    .setMessage(getRandomPushingDataError())
                    .setCancelable(false)
                    .setPositiveButton(OK_TEXT, (dialog, which) -> {
                        dialog.dismiss();
                    }).show();
        }, nick, game_.getPoints());
    }

}// class App
