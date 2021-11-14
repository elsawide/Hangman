package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    private TextView resultText;
    private TextView answerText;
    private TextView guessesLeftText;
    private Button mainMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultText = findViewById(R.id.result_text);
        answerText = findViewById(R.id.answer_text);
        guessesLeftText = findViewById(R.id.guesses_left_text);
        mainMenuButton= findViewById(R.id.main_menu_button);

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

        showResult();
    }

    private void showResult(){
        Intent intent = getIntent();
        String result = intent.getStringExtra("RESULT");
        String answer = intent.getStringExtra("ANSWER");
        String guessesLeft = intent.getStringExtra("GUESSESLEFT");

        resultText.setText(result);
        answerText.setText("Ordet var: " + answer);
        guessesLeftText.setText(guessesLeft);

    }

    private void startMainActivity(){
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

}