package whynot.com.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.widget.EditText;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import whynot.com.communication.Client;
import whynot.com.dto.DtoAnswer;
import whynot.com.dto.DtoCategory;
import whynot.com.dto.DtoGameData;
import whynot.com.game.Game;
import whynot.com.quizzapp.LevelActivity;
import whynot.com.quizzapp.MainActivity;
import whynot.com.quizzapp.TopTenActivity;

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
    private static final String END_GAME_TITLE = "Koniec Gry";
    private static final String END_GAME_HINT = "Nick";
    private Random random_;
    private Game game_;
    private Client client_;
    private List<DtoGameData> topTenList_ = new ArrayList<>();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showEndGameDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(END_GAME_TITLE);
        // Set up the input
        final EditText input = new EditText(activity);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint(END_GAME_HINT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", (dialog, which) -> {
            String text = input.getText().toString();
            App.getInstance().pushResult(activity, text, LevelActivity::goToMain);
        });

        builder.show();
    }

    /******************************************************************/
    /***************** Public Game Control Methods *******************/
    /*****************************************************************/

    public void resetGame(Activity context) {
        getData(context);
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

    public String getQuestion() {
        return game_.getQuestion().getText();
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

    public void shuffleAnswers() {
        game_.getQuestion().setAnswers(randomizeAnswers(game_.getQuestion().getAnswers()));
    }

    /******************************************************************/
    /*********************** Public Client Methods *******************/
    /*****************************************************************/

    public void getData(Activity activity) {
        dialog_ = ProgressDialog.show(activity, CONNECTING_TITLE, getRandomConnectingText(), true);
        client_.getData((List<DtoCategory> list) -> {
            game_.setCategories(list);
            game_.reset();
            dialog_.dismiss();
            ((MainActivity) activity).goToCategory();
        }, (fail) -> {
            dialog_.dismiss();
            //show error
            dialog_ = new AlertDialog.Builder(activity)
                    .setTitle(CONNECTING_TITLE)
                    .setMessage(getRandomConnectingError())
                    .setCancelable(false)
                    .setPositiveButton(OK_TEXT, (dialog, which) -> dialog.dismiss()).show();
        });
    }

    public void getTopTen(Activity activity) {
        dialog_ = ProgressDialog.show(activity, CONNECTING_TITLE, getRandomConnectingText(), true);
        client_.getTopTen((List<DtoGameData> list) -> {
            dialog_.dismiss();
            topTenList_ = list;
            if (activity instanceof MainActivity) {
                ((MainActivity) activity).goToTopTen();
            } else if (activity instanceof TopTenActivity) {
                ((TopTenActivity) activity).refreshList();
            }
        }, (fail) -> {
            dialog_.dismiss();
            //show error
            dialog_ = new AlertDialog.Builder(activity)
                    .setTitle(CONNECTING_TITLE)
                    .setMessage(getRandomConnectingError())
                    .setCancelable(false)
                    .setPositiveButton(OK_TEXT, (dialog, which) -> dialog.dismiss()).show();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void pushResult(Activity activity, String nick, Consumer<LevelActivity> goToMainFunc) {
        dialog_ = ProgressDialog.show(activity, SENDING_TITLE, getRandomPushingDataText(), true);
        client_.pushData((Integer position) -> {
            dialog_.dismiss();
            dialog_ = new AlertDialog.Builder(activity)
                    .setTitle(POSITION_TITLE)
                    .setMessage(POSITION_TEXT + position)
                    .setCancelable(false)
                    .setPositiveButton(OK_TEXT, (dialog, which) -> {
                        dialog.dismiss();
                        goToMainFunc.accept((LevelActivity) activity);
                    }).show();
        }, (fail) -> {
            dialog_.dismiss();
            //show error
            dialog_ = new AlertDialog.Builder(activity)
                    .setTitle(SENDING_TITLE)
                    .setMessage(getRandomPushingDataError())
                    .setCancelable(false)
                    .setPositiveButton(OK_TEXT, (dialog, which) -> dialog.dismiss()).show();
        }, nick, game_.getPoints());
    }
}// class App
