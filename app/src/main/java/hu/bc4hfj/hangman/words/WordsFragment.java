package hu.bc4hfj.hangman.words;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import hu.bc4hfj.hangman.R;
import hu.bc4hfj.hangman.words.recycler_view.word.WordsItemTouchHelperCallback;
import hu.bc4hfj.hangman.words.recycler_view.word.WordsRecyclerAdapter;
import hu.bc4hfj.hangman.words.recycler_view.word_group.WordGroup;

public class WordsFragment extends Fragment {
    private String letter;
    public static final String TAG = "WordsFragment";

    private TextView title;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_words, null);

        Bundle extras = getArguments();
        if(extras == null) {
            letter= null;
        } else {
            letter = extras.getString(WordGroup.LETTER);
        }

        RecyclerView wordsRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerWords);
        title = (TextView) rootView.findViewById(R.id.wordsLetter);
        title.setText(getString(R.string.wordsTitle, letter));
        progressBar = (ProgressBar) rootView.findViewById(R.id.wordsProgressBar);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        wordsRecyclerView.setLayoutManager(layoutManager);
        final WordsRecyclerAdapter wordsRecyclerAdapter = new WordsRecyclerAdapter(letter, progressBar);
        wordsRecyclerView.setAdapter(wordsRecyclerAdapter);

        ItemTouchHelper.Callback callback = new WordsItemTouchHelperCallback(wordsRecyclerAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(wordsRecyclerView);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
