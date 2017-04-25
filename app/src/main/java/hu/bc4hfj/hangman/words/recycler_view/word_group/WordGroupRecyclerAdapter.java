package hu.bc4hfj.hangman.words.recycler_view.word_group;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hu.bc4hfj.hangman.R;
import hu.bc4hfj.hangman.words.WordsFragment;

public class WordGroupRecyclerAdapter extends RecyclerView.Adapter<WordGroupRecyclerAdapter.ViewHolder>  {

    private List<WordGroup> wordGroups = new ArrayList<>();
    private Activity activityContext;

    public WordGroupRecyclerAdapter(Activity activityContext) {
        this.activityContext = activityContext;

        for (int i = 65; i < 91; i++) {
            wordGroups.add(new WordGroup(Character.toString((char) i)));
        }
    }

    @Override
    public WordGroupRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_group, parent, false);

        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(final WordGroupRecyclerAdapter.ViewHolder holder,int position) {
        holder.wordsGroup.setText(wordGroups.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return wordGroups.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView wordsGroup;

        public ViewHolder(final View itemView) {
            super(itemView);

            wordsGroup = (TextView) itemView.findViewById(R.id.groupOfWordsTextView);
            wordsGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment wordFragment = new WordsFragment();

                    Bundle b = new Bundle();
                    b.putString(WordGroup.LETTER, wordsGroup.getText().toString());
                    wordFragment.setArguments(b);

                    FragmentTransaction ft = activityContext.getFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.animator.slide_in, R.animator.slide_out, R.animator.slide_in_inverse, R.animator.slide_out_inverse);
                    ft.replace(R.id.wordFragmentsContainer, wordFragment, WordsFragment.TAG);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });
        }
    }

}
