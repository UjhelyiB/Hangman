package hu.bc4hfj.hangman.game;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hu.bc4hfj.hangman.R;
import hu.bc4hfj.hangman.database.SugarORMManager;
import hu.bc4hfj.hangman.words.recycler_view.word.Word;

public class GameActivity extends AppCompatActivity {
    private String solutionWord = "";
    private String guessedLetters = "";
    private String shownWord = "";
    private int wrongGuesses = 0;
    private TextView tvShownWord;
    private TextView tvGameResult;
    private Button btRestartGame;
    private ImageView ivGamePicture;
    private ProgressBar progressBar;
    private FlowLayout flowLayout;

    private List<LetterButton> letterButtons = new ArrayList<>();

    protected static final int maxNumberOfWrongGuesses = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);

        initUI();
        new RollASolutionWordAsynchronously().execute();
    }

    private void initUI() {
        setContentView(R.layout.activity_game);
        tvShownWord = (TextView) findViewById(R.id.tv_shown_word);
        tvGameResult = (TextView) findViewById(R.id.tv_game_result);
        btRestartGame = (Button) findViewById(R.id.bt_restart_game);
        ivGamePicture = (ImageView) findViewById(R.id.iv_hangman);
        ivGamePicture.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("@drawable/picture0", null, getPackageName())));
        progressBar = (ProgressBar) findViewById(R.id.gameProgressBar);
        flowLayout = (FlowLayout) findViewById(R.id.gameLetterButtonsFlowLayout);


        for (int i=65; i< 91; i++){
            LetterButton letterButton = new LetterButton(this);
            letterButton.setLetter(Character.toString((char) i));
            letterButtons.add(letterButton);

            flowLayout.addView(letterButton);
        }

        btRestartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent newGame = new Intent();
                newGame.setClass(GameActivity.this, GameActivity.class);
                startActivity(newGame);
            }
        });
    }

    private class RollASolutionWordAsynchronously extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... strings) {
            List<Word> allWords = SugarORMManager.getAllWords();

            Random random = new Random();
            solutionWord = allWords.get(random.nextInt(allWords.size() - 1)).getWord().toLowerCase();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            shownWord = HangmanFunctionsWithWords.createShownWord(guessedLetters, solutionWord);

            tvShownWord.setText(shownWord);
            progressBar.setVisibility(View.INVISIBLE);

            for(int i=0; i< letterButtons.size(); i++){
                letterButtons.get(i).setEnabled(true);
            }
        }
    }

    public void refreshUIwithNewLetter(String newLetter){
        if (guessedLetters.contains(newLetter)) {
            Toast.makeText(GameActivity.this, R.string.already_tried_that_letter, Toast.LENGTH_SHORT).show();

        } else {
            guessedLetters += newLetter;

            if (solutionWord.contains(newLetter)) {
                shownWord = HangmanFunctionsWithWords.createShownWord(guessedLetters, solutionWord);

                if (HangmanFunctionsWithWords.checkIfWordEqualsSolutionWord(shownWord, solutionWord)) {
                    gameHasEnded(true);
                }
            } else {
                wrongGuesses++;
                ivGamePicture.setImageDrawable(getResources()
                        .getDrawable(getResources().getIdentifier("@drawable/picture" + wrongGuesses, null, getPackageName())));
                if (wrongGuesses == maxNumberOfWrongGuesses) {
                    gameHasEnded(false);
                }
            }
        }
        tvShownWord.setText(shownWord);
    }

    public void gameHasEnded(boolean playerWon) {
        for(int i=0; i< letterButtons.size(); i++){
            letterButtons.get(i).setEnabled(false);
        }

        if (playerWon) {
            tvGameResult.setText(getText(R.string.you_won));
        } else {
            tvGameResult.setText(getText(R.string.you_lost));
            shownWord = HangmanFunctionsWithWords.createShownWord(solutionWord, solutionWord);
        }

        btRestartGame.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }
}
