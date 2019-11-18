package de.fahri.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView txtTime;
    private TextView txtEquation;
    private TextView txtScore;
    private Button[] btnAnswers;
    private TextView txtFeedback;
    private Button btnRestartGame;

    private Random random;

    private long gameDuration = 30_000; // Duration of the game in milliseconds
    private int numberQuestionProcessed;
    private int numberRightAnswers;
    private int streakWrongAnswers;
    private int operand1, operand2, result;
    private boolean isGameOver = true;

    public void onAnswerClicked(View view) {
        if (isGameOver)
            return;

        Button answerBtn = (Button)view;
        int answer = Integer.parseInt(answerBtn.getText().toString());

        numberQuestionProcessed += 1;

        if (answer == result)
            rightAnswer();
        else
            wrongAnswer();

        createNewEquation();
    }

    private void rightAnswer() {
        numberRightAnswers += 1;

        setFeedbackText("Right!");
        setScoreText();
    }

    private void wrongAnswer() {
        setFeedbackText("Wrong!");

        streakWrongAnswers += 1;

        if (streakWrongAnswers >= 5) {
            streakWrongAnswers = 0;
            numberRightAnswers -= 2;

            if (numberRightAnswers < 0) {
                numberRightAnswers = 0;
            }
        }

        setScoreText();
    }

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

        Log.i("Init", "Go button initialized");
    }

    private void startGame() {
        isGameOver = false;

        btnRestartGame.setVisibility(View.INVISIBLE);
        setStartFeedbackText();

        initTimer();
        createNewEquation();
        initScore();

        streakWrongAnswers = 0;

        Log.i("Game", "Game started");
    }

    private void setStartFeedbackText() {
        setFeedbackText("Let's Go!");
    }

    private void createNewEquation() {
        operand1 = generateRandomOperand();
        operand2 = generateRandomOperand();
        result = operand1 + operand2;

        setAnswers();

        setEquationText();
    }

    private void setAnswers() {
        int randomRightAnswer = random.nextInt(btnAnswers.length);

        int answer = 0;

        for (int i = 0; i < btnAnswers.length; i++) {
            if (i == randomRightAnswer) {
                answer = result;
            }
            else {
                do {
                    answer = random.nextInt(result) + random.nextInt(20) + 1;
                } while (answer == result);
            }

            btnAnswers[i].setText(Integer.toString(answer));
        }
    }

    private void setEquationText() {
        String equationTxt = String.format("%2d + %2d", operand1, operand2);

        txtEquation.setText(equationTxt);
    }

    private int generateRandomOperand() {
        return random.nextInt(100);
    }

    private void initScore() {
        numberQuestionProcessed = 0;
        numberRightAnswers = 0;

        setScoreText();
    }

    private void setScoreText() {
        String scoreTxt = String.format("%d/%d", numberRightAnswers, numberQuestionProcessed);

        txtScore.setText(scoreTxt);
    }

    private void initGameScreen() {
        initViewVariables();

        random = new Random();

        initRestartButton();
        initAnswerButtons();

        setStartFeedbackText();

        Log.i("Init", "Game screen initialized");
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

        btnRestartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
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

        setFeedbackText("Done!");

        Log.i("Game", "Game Over");
    }

    private void setTimerText(long milliseconds) {
        String timerTxt = String.format("%2d s", milliseconds / 1000);

        txtTime.setText(timerTxt);

        Log.i("Timer Text", "Set to: " + timerTxt);
    }

    private void setFeedbackText(String txt) {
        txtFeedback.setText(txt);
    }
}
