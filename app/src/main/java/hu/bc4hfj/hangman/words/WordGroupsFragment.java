package hu.bc4hfj.hangman.words;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.bc4hfj.hangman.R;
import hu.bc4hfj.hangman.words.recycler_view.word_group.WordGroupRecyclerAdapter;

public class WordGroupsFragment extends Fragment {
    public static final String TAG = "WordGroupsFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wordgroups, null);

        RecyclerView wordGroupsRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerWordGroups);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        wordGroupsRecyclerView.setLayoutManager(layoutManager);
        final WordGroupRecyclerAdapter wordGroupRecyclerAdapter = new WordGroupRecyclerAdapter(getActivity());
        wordGroupsRecyclerView.setAdapter(wordGroupRecyclerAdapter);


        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
