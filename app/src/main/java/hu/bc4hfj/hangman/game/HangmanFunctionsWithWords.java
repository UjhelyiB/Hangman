package hu.bc4hfj.hangman.game;

public class HangmanFunctionsWithWords {

    public static boolean checkIfWordEqualsSolutionWord(String shownWord, String solutionWord){
        StringBuilder word = new StringBuilder(shownWord);
        StringBuilder solution = new StringBuilder(solutionWord);

        for(int i=0; i <= word.length(); i+= 2){
            if(solution.charAt(i/2) != word.charAt(i)){
                return false;
            }
        }
        return true;
    }

    public static String createShownWord(String guessedLetters, String solutionWord){
        StringBuilder result= new StringBuilder("");

        for(int i=0; i<solutionWord.length(); i++){
            result.append('_');
        }
        for(int i=0; i<guessedLetters.length(); i++) {
            for (int j = 0; j < solutionWord.length(); j++) {
                if (guessedLetters.charAt(i) == solutionWord.charAt(j)) {
                    result.setCharAt(j, guessedLetters.charAt(i));
                }
            }
        }

        result = fillShownWordWithSpace(result);

        return result.toString();
    }

    private static StringBuilder fillShownWordWithSpace(StringBuilder s) {
        StringBuilder resultString = new StringBuilder("");
        for(int i=0; i< s.length(); i++){
            resultString.append(s.charAt(i));
            if(i != s.length()-1){
                resultString.append(" ");
            }
        }
        return resultString;
    }
}