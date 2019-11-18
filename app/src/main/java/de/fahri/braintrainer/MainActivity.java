package de.fahri.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txtTime;
    private TextView txtEquation;
    private TextView txtScore;
    private Button[] btnAnswers;
    private TextView txtFeedback;
    private Button btnRestartGame;

    private long gameDuration = 3000; // Duration of the game in milliseconds
    private int numberQuestionProcessed;
    private int numberRightAnswers;
    private boolean isGameOver = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGoScreen();
        initGameScreen();
    }

    private void initGoScreen() {
        Button btnGo = findViewById(R.id.btnGo);
        final ConstraintLayout gameLayout = findViewById(R.id.gameLayout);

        gameLayout.setVisibility(View.INVISIBLE);
        btnGo.setVisibility(View.VISIBLE);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.INVISIBLE);
                gameLayout.setVisibility(View.VISIBLE);
                startGame();
            }
        });
    }

    private void startGame() {
        isGameOver = false;
    }

    private void initGameScreen() {
        initViewVariables();

        initRestartButton();
        initAnswerButtons();
    }

    private void initViewVariables() {
        txtTime = findViewById(R.id.txtTime);
        txtEquation = findViewById(R.id.txtEquation);
        txtScore = findViewById(R.id.txtScore);

        btnAnswers = new Button[4];
        btnAnswers[0] = findViewById(R.id.btnAnswer1);
        btnAnswers[1] = findViewById(R.id.btnAnswer2);
        btnAnswers[2] = findViewById(R.id.btnAnswer3);
        btnAnswers[3] = findViewById(R.id.btnAnswer4);

        txtFeedback = findViewById(R.id.txtFeedback);
        btnRestartGame = findViewById(R.id.btnRestartGame);
    }

    private void initRestartButton() {
        btnRestartGame.setVisibility(View.INVISIBLE);
    }

    private void initAnswerButtons() {
    }

    private void initTimer() {
        new CountDownTimer(gameDuration, 1000) {

            public void onTick(long millisUntilFinished) {
                setTimerText(millisUntilFinished);
            }

            public void onFinish() {
                gameOver();
            }
        }.start();

        Log.i("Init", "Timer initialized");
    }

    private void gameOver() {
        isGameOver = true;
        btnRestartGame.setVisibility(View.VISIBLE);

        Log.i("Game", "Game Over");
    }

    private void setTimerText(long milliseconds) {
        String timerTxt = String.format("%2d s", milliseconds / 1000);

        txtTime.setText(timerTxt);

        Log.i("Timer Text", "Set to: " + timerTxt);
    }
}
