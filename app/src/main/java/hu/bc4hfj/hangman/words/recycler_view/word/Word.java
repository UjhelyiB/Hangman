package hu.bc4hfj.hangman.words.recycler_view.word;

import com.orm.SugarRecord;

public class Word extends SugarRecord{
    private String word;

    public Word(){
    }

    public Word(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }


}
