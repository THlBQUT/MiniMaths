package fr.ensisa.minimaths;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
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
    private Button retryButton;
    Equation equation;
    private int compteur = 0;
    private String difficulty = "FACILE";
    private boolean isIntroSkip = false;
    private boolean finDePartie = false;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazer);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {this.difficulty= extras.getString("DIFFICULTY");}
        this.equation = new Equation(difficulty);

        this.textView = this.findViewById((R.id.textlazer));
        this.editText = this.findViewById(R.id.textinputlazer);
        this.victory= this.findViewById(R.id.victory);
        this.defeat = this.findViewById(R.id.defeat);
        this.progressBar = this.findViewById(R.id.progressBar);
        this.retryButton = this.findViewById(R.id.lazerBattle_button_retry);
        textView.setText(equation.getEquation());

        Runnable runnable = new Runnable() {
            private int compteuria = 0;
            @Override
            public void run() {
                while(!finDePartie) {
                    compteuria += 1;
                    progressBar.setProgress(progressBar.getProgress() - 5 * compteuria);
                    if(progressBar.getProgress() <= progressBar.getMin()) {
                        finDePartie = true;
                        defeat.getHandler().post(new Runnable() {
                            public void run() {
                                defeat.setVisibility(View.VISIBLE);
                                defeat();
                                retryButton.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread = new Thread(runnable);

        this.editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event != null &&
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (event == null || !event.isShiftPressed()) {
                        try {
                            Integer numberInput = Integer.parseInt(editText.getText().toString());
                            if (equation.getResultat() == numberInput) {
                                editText.setText("");
                                equation = new Equation(difficulty);
                                textView.setText(equation.getEquation());
                                compteur += 1;
                                progressBar.setProgress(progressBar.getProgress() + 5 * compteur);
                                if (progressBar.getProgress() >= progressBar.getMax()) {
                                    change_visibility(View.INVISIBLE);
                                    victory.setVisibility(View.VISIBLE);
                                    retryButton.setVisibility(View.VISIBLE);
                                    finDePartie = true;
                                }
                            } else {
                                Animation animShake = AnimationUtils.loadAnimation(LazerBattle.this, R.anim.shake);
                                editText.startAnimation(animShake);
                            }
                            return true;
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                        }
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

    public void skipIntro(View v){
        if(!this.isIntroSkip) {
            TextView intro1 = this.findViewById((R.id.lazerGame_intro_text1));
            TextView intro2 = this.findViewById((R.id.lazerGame_intro_text2));
            intro1.setVisibility(View.INVISIBLE);
            intro2.setVisibility((View.INVISIBLE));
            change_visibility(View.VISIBLE);
            this.isIntroSkip = true;
            thread.start();
        }
        if(finDePartie){
            change_visibility(View.INVISIBLE);
            this.finish();
        }
    }

    private void defeat(){
        change_visibility(View.INVISIBLE);
        defeat.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.VISIBLE);
    }

    public void retry_button(View v){
        Intent intent = new Intent(this, LazerBattle.class);
        intent.putExtra("DIFFICULTY", this.difficulty);
        startActivity(intent);
    }
}