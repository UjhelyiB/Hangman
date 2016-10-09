package hu.bc4hfj.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt_newGame = (Button) findViewById(R.id.bt_new_game);
        bt_newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStartGame = new Intent();
                intentStartGame.setClass(MainActivity.this, GameActivity.class);
                startActivity(intentStartGame);
            }
        });
    }
}