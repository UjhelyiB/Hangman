package hu.bc4hfj.hangman.words;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import hu.bc4hfj.hangman.R;

public class WordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);

        setContentView(R.layout.activity_words);

        Fragment wordGroupsFragment = new WordGroupsFragment();

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.replace(R.id.wordFragmentsContainer, wordGroupsFragment, WordGroupsFragment.TAG);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }
}
