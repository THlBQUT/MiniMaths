package fr.ensisa.minimaths;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class LazerBattle extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView textView;
    private EditText editText;
    private TextView victory;
    private TextView defeat;
    Equation equation;
    private int compteur = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazer);
        Bundle extras = getIntent().getExtras();
        String difficulty = "FACILE";
        this.equation = new Equation(difficulty);

        this.textView = this.findViewById((R.id.textlazer));
        this.editText = this.findViewById(R.id.textinputlazer);
        this.victory= this.findViewById(R.id.victory);
        this.defeat = this.findViewById(R.id.defeat);
        this.progressBar = this.findViewById(R.id.progressBar);
        textView.setText(equation.getEquation());

        Runnable runnable = new Runnable() {
            private int compteuria = 0;
            private boolean bool = true;
            @Override
            public void run() {
                while(bool) {
                    compteuria += 1;
                    progressBar.setProgress(progressBar.getProgress() - 5 * compteuria);
                    if(progressBar.getProgress() <= progressBar.getMin()) {
                        bool = false;
                        change_visibility(View.INVISIBLE);
                        defeat.setVisibility(View.VISIBLE);
                    }
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                }
            }
        };
        Thread thread = new Thread(runnable);
        //thread.start();

        this.editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event != null &&
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (event == null || !event.isShiftPressed()) {
                        if(equation.getResultat() == Integer.parseInt(editText.getText().toString())) {
                            editText.setText("");
                            equation = new Equation(difficulty);
                            textView.setText(equation.getEquation());
                            compteur+=1;
                            progressBar.setProgress(progressBar.getProgress() + 5*compteur);
                            if(progressBar.getProgress() >= progressBar.getMax()) {
                                change_visibility(View.INVISIBLE);
                                victory.setVisibility(View.VISIBLE);
                            }
                        }
                        else{
                            Animation animShake = AnimationUtils.loadAnimation( LazerBattle.this, R.anim.shake);
                            editText.startAnimation(animShake);
                        }
                        return true;
                    }
                }
                return false;
        }});
    }

    protected void change_visibility(int visibility){
        progressBar.setVisibility(visibility);
        editText.setVisibility(visibility);
        textView.setVisibility(visibility);
    }
}