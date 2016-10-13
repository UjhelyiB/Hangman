package hu.bc4hfj.hangman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


public class GameActivity extends AppCompatActivity {
    ArrayList<String> listOfWords = new ArrayList<String>();
    String solutionWord = "";
    String guessedLetters = "";
    String shownWord = "";
    int wrongGuesses = 0;
    boolean gameHasEnded = false;

    public static final int maxNumberOfWrongGuesses = 11;
    public static final String textWon = "Glorious Victory!";
    public static final String textLost = "Humiliating Defeat!";
    public static final String errorMessage = "Please type in one letter!";
    public static final String alreadyTriedLetterWarning = "Already tried that letterrrrrrr!";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game);

        try {
            final TextView tvGuessedLetters = (TextView) findViewById(R.id.tv_guessed_letters);
            final TextView tvShownWord = (TextView) findViewById(R.id.tv_shown_word);
            final EditText etGuess = (EditText) findViewById(R.id.et_guess);
            Button btSendGuess = (Button) findViewById(R.id.bt_SendGuess);
            final TextView tvGameResult = (TextView) findViewById(R.id.tv_game_result);
            final Button btRestartGame = (Button) findViewById(R.id.bt_restart_game);

            final ImageView ivGamePicture = (ImageView) findViewById(R.id.iv_hangman);

            BufferedReader br = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.angol)));
            String readLine = null;

            ivGamePicture.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("@drawable/picture0", null, getPackageName())));


            readLine = br.readLine();
            while (readLine != null){
                listOfWords.add(readLine);
                readLine = br.readLine();
            }

            Random random = new Random();
            solutionWord = listOfWords.get(random.nextInt(listOfWords.size()-1));

            for(int i=0; i< solutionWord.length(); i++){
                shownWord += "_";
            }

            tvShownWord.setText(shownWord);

            btSendGuess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(gameHasEnded == true){
                        return;
                    }

                    if(etGuess.getText().toString().equals("")){
                        etGuess.setError(errorMessage);

                    }else if(guessedLetters.contains(etGuess.getText())){
                        Toast.makeText(GameActivity.this, alreadyTriedLetterWarning, Toast.LENGTH_SHORT).show();

                    }else{
                        guessedLetters += etGuess.getText();


                        if(solutionWord.contains(etGuess.getText())){
                            shownWord = createShownWord(guessedLetters, solutionWord);

                            if(shownWord.equals(solutionWord)){
                                tvGameResult.setText(textWon);
                                gameHasEnded = true;
                                btRestartGame.setVisibility(View.VISIBLE);

                                InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(etGuess.getWindowToken(), 0);
                            }
                        }else{
                            wrongGuesses++;
                            ivGamePicture.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("@drawable/picture"+wrongGuesses, null, getPackageName())));
                            if(wrongGuesses == maxNumberOfWrongGuesses){
                                tvGameResult.setText(textLost);
                                shownWord = createShownWord(solutionWord, solutionWord);
                                gameHasEnded = true;
                                btRestartGame.setVisibility(View.VISIBLE);

                                InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(etGuess.getWindowToken(), 0);
                            }
                        }
                    }


                    tvGuessedLetters.setText(guessedLetters);
                    tvShownWord.setText(shownWord);
                    etGuess.setText("");

                }
            });

            btRestartGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    Intent newGame = new Intent();
                    newGame.setClass(GameActivity.this, GameActivity.class);
                    startActivity(newGame);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public String createShownWord(String guessedLetters, String solutionWord){
        StringBuilder result= new StringBuilder("");

        for(int i=0; i<solutionWord.length(); i++){
            result.append('_');
        }
        for(int i=0; i<guessedLetters.length(); i++) {
            for (int j = 0; j < solutionWord.length(); j++) {
                if (guessedLetters.charAt(i) == solutionWord.charAt(j)) {
                    result.setCharAt(j, guessedLetters.charAt(i));
                }
            }
        }

        return result.toString();
    }
}
