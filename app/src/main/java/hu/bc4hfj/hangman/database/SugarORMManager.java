package hu.bc4hfj.hangman.database;

import android.content.Context;
import android.util.Log;

import com.orm.SugarApp;
import com.orm.SugarRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import hu.bc4hfj.hangman.R;
import hu.bc4hfj.hangman.words.recycler_view.word.Word;

public class SugarORMManager extends SugarApp {
    private static SugarORMManager instance;
    private static List<Word> allWords;
    private static Context context;

    public SugarORMManager(){
    }

    public static SugarORMManager getInstance(){
        if(instance == null){
            instance = new SugarORMManager();
        }
        return instance;
    }

    public static void setContext(Context c){
        context = c;
    }

    public static List<Word> getAllWords() {
        if(allWords == null){
            allWords = Word.listAll(Word.class);
        }
        return allWords;
    }

    public static void deleteWord(Word w){
        allWords.remove(w);
    }

    public void setupDatabase(){
        BufferedReader br = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.angol)));
        String readLine;

        allWords = new ArrayList<>();
        try {
            readLine = br.readLine();

            while (readLine != null) {
                allWords.add(new Word(readLine));

                readLine = br.readLine();
            }
            SugarRecord.saveInTx(allWords);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
