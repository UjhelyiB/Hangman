package hu.bc4hfj.hangman.words.recycler_view.word;

public interface WordsTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
