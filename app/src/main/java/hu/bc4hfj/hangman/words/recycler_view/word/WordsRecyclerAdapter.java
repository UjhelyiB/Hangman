package hu.bc4hfj.hangman.words.recycler_view.word;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hu.bc4hfj.hangman.R;
import hu.bc4hfj.hangman.database.SugarORMManager;

public class WordsRecyclerAdapter extends
        RecyclerView.Adapter<
                WordsRecyclerAdapter.ViewHolder> implements  WordsTouchHelperAdapter {

    private List<Word> requiredWords = new ArrayList<>();
    private ProgressBar progressBar;

    public WordsRecyclerAdapter(String letter, ProgressBar progressBar) {
        this.progressBar = progressBar;
        new LoadWordsAsynchronously().execute(letter);

    }

    @Override
    public WordsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.word, parent, false);

        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(final WordsRecyclerAdapter.ViewHolder holder,
                                 int position) {
            holder.word.setText(requiredWords.get(position).getWord());


    }

    @Override
    public int getItemCount() {
        return requiredWords.size();
    }

    public void addWord(Word word) {
        word.save();
        requiredWords.add(word);
        notifyDataSetChanged();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(requiredWords, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(requiredWords, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        SugarORMManager.deleteWord(requiredWords.get(position));
        requiredWords.get(position).delete();
        requiredWords.remove(position);
        notifyItemRemoved(position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView word;

        public ViewHolder(final View itemView) {
            super(itemView);

            word = (TextView) itemView.findViewById(R.id.oneWordTextView);
        }
    }

    private class LoadWordsAsynchronously extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            List<Word> allWords = SugarORMManager.getAllWords();

            for(int i=0; i< allWords.size(); i++){
                if(allWords.get(i) != null){
                    if(allWords.get(i).getWord().toUpperCase().startsWith(strings[0])){

                        requiredWords.add(allWords.get(i));
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressBar.setVisibility(View.INVISIBLE);
            notifyDataSetChanged();
        }
    }
}
