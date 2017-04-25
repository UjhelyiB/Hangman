package hu.bc4hfj.hangman.words.recycler_view.word_group;

import com.orm.SugarRecord;

public class WordGroup extends SugarRecord{
    private String name;

    public static final String LETTER = "letter";

    public WordGroup( String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
