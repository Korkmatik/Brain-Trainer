package de.fahri.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button[] btnAnswers;
    private TextView txtTime;
    private TextView txtResult;
    private TextView txtFeedback;
    private Button btnRestartGame;

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
    }

    private void initGameScreen() {
    }

    private void initTimer() {

    }
}
