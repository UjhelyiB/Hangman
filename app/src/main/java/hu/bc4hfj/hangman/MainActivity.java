package hu.bc4hfj.hangman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.List;

import hu.bc4hfj.hangman.database.SugarORMManager;
import hu.bc4hfj.hangman.game.GameActivity;
import hu.bc4hfj.hangman.words.WordsActivity;
import hu.bc4hfj.hangman.words.recycler_view.word.Word;

public class MainActivity extends AppCompatActivity {
    private Button bt_newGame;
    private Button bt_words;
    private AlertDialog alertDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initUI();
        checkDB();

        new LoadWordsAsynchronously().execute();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void checkDB() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

        boolean database_has_been_created = sharedPref.getBoolean(getString(R.string.database_has_been_created), false);

        if (!database_has_been_created) {
            setupSugarORM();

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(getString(R.string.database_has_been_created), true);
            editor.apply();
        }
    }

    public void setupSugarORM() {
        alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.loading)
                .setMessage(R.string.loading_words_details)
                .setCancelable(false)
                .create();
        alertDialog.show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SugarORMManager.setContext(getBaseContext());
                SugarORMManager.getInstance().setupDatabase();
                    alertDialog.dismiss();
            }
        }, 300);
    }

    private void initUI() {
        setContentView(R.layout.activity_main);

        bt_newGame = (Button) findViewById(R.id.bt_new_game);
        bt_newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStartGame = new Intent();
                intentStartGame.setClass(MainActivity.this, GameActivity.class);
                startActivity(intentStartGame);
            }
        });

        bt_words = (Button) findViewById(R.id.bt_view_words);
        bt_words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStartWords = new Intent();
                intentStartWords.setClass(MainActivity.this, WordsActivity.class);
                startActivity(intentStartWords);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.application_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.about_option:
                alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.menu_about)
                        .setMessage(R.string.about_details)
                        .setPositiveButton(R.string.ok, null)
                        .create();
                alertDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class LoadWordsAsynchronously extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            SugarORMManager.getAllWords();

            return null;
        }
    }
}