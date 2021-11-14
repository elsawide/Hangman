package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    public final String[] words = {
            "ABSTRACT", "ASSERT", "BOOLEAN", "BREAK", "BYTE",
            "CASE", "CATCH", "CHAR", "CLASS", "CONST",
            "CONTINUE", "DEFAULT", "DOUBLE", "DO", "ELSE",
            "ENUM", "EXTENDS", "FALSE", "FINAL", "FINALLY",
            "FLOAT", "FOR", "GOTO", "IF", "IMPLEMENTS",
            "IMPORT", "INSTANCEOF", "INT", "INTERFACE",
            "LONG", "NATIVE", "NEW", "NULL", "PACKAGE",
            "PRIVATE", "PROTECTED", "PUBLIC", "RETURN",
            "SHORT", "STATIC", "STRICTFP", "SUPER", "SWITCH",
            "SYNCHRONIZED", "THIS", "THROW", "THROWS",
            "TRANSIENT", "TRUE", "TRY", "VOID", "VOLATILE", "WHILE"
    };
    private int guessesLeft;
    private String word;
    private String hiddenWord;
    private String guessedLetters;
    private ImageView hangmanImage;
    private TextView wordTV;
    private TextView guessesLeftTV;
    private TextView guessedLettersTV;
    private EditText guessET;
    private Button guessButton;
    private boolean win;
    public static final Random RANDOM = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        word = words[RANDOM.nextInt(words.length)];
        hiddenWord = hideWord(word);
        guessedLetters = "";
        guessesLeft = 6;

        hangmanImage = findViewById(R.id.hangman_image);
        wordTV = findViewById(R.id.word_tv);
        guessesLeftTV = findViewById(R.id.guesses_left_tv);
        guessedLettersTV = findViewById(R.id.guessed_letters_tv);
        guessET = findViewById(R.id.guess_et);
        guessButton = findViewById(R.id.guess_button);

        //startGame();

        wordTV.setText(hiddenWord);

        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guess = guessET.getText().toString().toUpperCase();
                if(!guess.isEmpty() && !letterGuessed(guess.charAt(0))){
                    guess(guess.charAt(0));
                }


            }
        });


    }

    public void startGame(){
        word = words[RANDOM.nextInt(words.length)];
        hiddenWord = hideWord(word);
        guessedLetters = "";
        guessesLeft = 10;
    }

    private String hideWord(String word){

        String hidingWord = "";
        for(int i = 0; i < word.length(); i++){
            hidingWord += "_";
        }
        return hidingWord;
    }

    private void guess(char letter){
        String letterString = String.valueOf(letter);
        if(word.contains(letterString)) {
            Log.i("info", "wtf");
            StringBuilder wordBuilder = new StringBuilder(hiddenWord);

            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == letter) {
                    wordBuilder.setCharAt(i, letter);
                }
            }
            hiddenWord = wordBuilder.toString();
            wordTV.setText(hiddenWord);
            if(!hiddenWord.contains("_")){
                gameFinished(true);
            }
        } else {
            guessedLetters += letter;
            guessedLettersTV.setText(guessedLetters);
            guessesLeft--;
            //String guessesLeftString = String.valueOf(guessesLeft) + " försök kvar";
            guessesLeftTV.setText(String.valueOf(guessesLeft) + " försök kvar");
            updateImage(guessesLeft);
            if(guessesLeft == 0){
                gameFinished(false);
            }
        }
        guessET.setText("");
    }

    private void gameFinished(boolean win){
        String result = "";
        if(win){
            result = "Du vann!";
            Log.i("info", "win");
        } else {
            result = "Du förlorade!";
            Log.i("info", "lose");
        }
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("RESULT", result);
        intent.putExtra("ANSWER", word);
        intent.putExtra("GUESSESLEFT", guessesLeftTV.getText());
        startActivity(intent);
    }

    public void updateImage(int number ){

            int resImg = getResources().getIdentifier("hangman_" + number, "drawable",
                    getPackageName());
            hangmanImage.setImageResource(resImg);

    }

    private boolean letterGuessed(char letter){
        String letterString = String.valueOf(letter);
        if(guessedLetters.contains(letterString)){
            return true;
        }
        return false;
    }
}