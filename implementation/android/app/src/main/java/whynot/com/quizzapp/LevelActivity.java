package whynot.com.quizzapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import whynot.com.app.App;

public class LevelActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    Button btnQuestion;
    //todo change to table
    Button btnAns1;
    Button btnAns2;
    Button btnAns3;
    Button btnAns4;
    ProgressBar progress;
    boolean makeProgress = true;
    //todo change to app field
    boolean gameGoing = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_level);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        View mContentView = findViewById(R.id.level_activity);
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        progress = findViewById(R.id.prTime);
        btnQuestion = findViewById(R.id.questionBtn);
        btnQuestion.setClickable(false);
        btnAns1 = findViewById(R.id.ans1Btn);
        btnAns2 = findViewById(R.id.ans2Btn);
        btnAns3 = findViewById(R.id.ans3Btn);
        btnAns4 = findViewById(R.id.ans4Btn);
        int seconds = App.getInstance().getGameTime() * 100;
        progress.setMax(seconds);
        progress.setProgress(seconds);

        animate(btnQuestion, App.getInstance().getQuestion());


        new Handler().postDelayed(() -> {
            setButton(btnAns1, 0);
            setButton(btnAns2, 1);
            setButton(btnAns3, 2);
            setButton(btnAns4, 3);
        }, 250);


        new Thread(() -> {
            while (makeProgress) {
                if (makeProgress) {
                    try {
                        progress.setProgress(progress.getProgress() - 1);
                        makeProgress = progress.getProgress() > 0;
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            endTurn(null, -1);
        }).start();
    }


    void setButton(Button button, int index) {
        button.setOnClickListener(view -> {
            endTurn(button, index);
        });
       animate(button, App.getInstance().getAnswer(index));
    }

    private void animate(Button button, String text) {
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(button, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(button, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                button.setText(text);
                oa2.start();
            }
        });
        oa1.start();
    }

    private void endTurn(Button button, int index) {
        if (gameGoing) {
            gameGoing = false;
            btnQuestion.setClickable(true);
            btnAns1.setClickable(false);
            btnAns2.setClickable(false);
            btnAns3.setClickable(false);
            btnAns4.setClickable(false);
            makeProgress = false;
            //todo if app ready uncomment code
            if (index != -1 && App.getInstance().checkAnswer(index)) {
//            if (index == 0) {
                button.setBackgroundColor(Color.GREEN);
                App.getInstance().setAnswerTime(stopProgress());
                App.getInstance().NextQuestion();
                btnQuestion.setOnClickListener(view -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startActivity(new Intent(this, CategoryActivity.class),
                                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                    } else {
                        startActivity(new Intent(this, CategoryActivity.class));
                    }
                });
            } else {
                if (button != null) {
                    button.setBackgroundColor(Color.RED);
                }
                App.getInstance().endGame();
                btnQuestion.setOnClickListener(view -> {
                    //go to end game
//                    Show  activity to push points
                });
            }
        }
    }

    private int stopProgress() {
        return progress.getProgress();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(new Intent(this, MainActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            } else {
                startActivity(new Intent(this, MainActivity.class));
            }
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.double_back_click, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

}// class LevelActivity
