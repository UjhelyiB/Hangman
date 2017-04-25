package hu.bc4hfj.hangman.game;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

import org.apmem.tools.layouts.FlowLayout;

import hu.bc4hfj.hangman.R;
import tyrantgit.explosionfield.ExplosionField;

public class LetterButton extends Button {
    private static final int WIDTH = 38;
    private static final int HEIGHT = 38;
    private static final int MARGIN = 4;
    private String letter;
    private GameActivity gameActivity;
    private ExplosionField explosionField;
    private LetterButton thisLetterButton;

    public LetterButton(final GameActivity gameActivity) {
        super(gameActivity);
        thisLetterButton = this;
        this.gameActivity = gameActivity;

        setupUI();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                gameActivity.refreshUIwithNewLetter(letter.toLowerCase());

                explosionField.explode(thisLetterButton);

                setEnabled(false);
            }
        });
    }

    private void setupUI() {
        explosionField = ExplosionField.attach2Window(gameActivity);

        FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, WIDTH, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, HEIGHT, getResources().getDisplayMetrics())
        );
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN, getResources().getDisplayMetrics());

        setEnabled(false);
        layoutParams.setMargins(margin, margin/2, margin, margin/2);
        setLayoutParams(layoutParams);
        setBackground(getResources().getDrawable(R.drawable.text_view_background));
        setTextColor(Color.parseColor(getContext().getString(R.string.color_white)));
    }

    public void  setLetter(String letter){
        this.letter = letter;


        this.setText(letter);
    }
}
