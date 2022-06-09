package fr.ensisa.minimaths.lazerbattle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import fr.ensisa.minimaths.Constantes;
import fr.ensisa.minimaths.Equation;
import fr.ensisa.minimaths.R;

public class LazerBattle extends AppCompatActivity {

    private int progress = 50; //0 victoire du joueur gauche et 100 celui du joueur droite
    private TextView textView;
    private EditText editText;
    private TextView victory;
    private TextView defeat;
    private Button retryButton;
    private ImageView background;
    private ImageView screen;
    private ImageView player1;
    private ImageView player2;
    private Equation equation;
    private int compteur = 0;
    private String difficulty = Constantes.DEFAULT_DIFFICULTY;
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
        this.retryButton = this.findViewById(R.id.lazerBattle_button_retry);
        this.background = this.findViewById(R.id.background_lazer);
        this.screen = this.findViewById(R.id.screen);
        this.player1 = this.findViewById(R.id.player1);
        this.player2 = this.findViewById(R.id.player2);
        textView.setText(equation.getEquation());

        Runnable runnable = new Runnable() {
            private int compteuria = 0;
            @Override
            public void run() {
                while(!finDePartie) {
                    compteuria += 1;
                    progress = progress - Constantes.MULTIPLIER_LAZER_BATTLE_FIGHT * compteuria;
                    defeat.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            if(progress <= 0) {
                                finDePartie = true;
                                defeat.setVisibility(View.VISIBLE);
                                defeat();
                                retryButton.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    try {
                        Thread.sleep(5000);
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
                                progress = progress + Constantes.MULTIPLIER_LAZER_BATTLE_FIGHT * compteur;
                                if(progress >= 100) {
                                    change_visibility(View.INVISIBLE);
                                    victory.setVisibility(View.VISIBLE);
                                    retryButton.setVisibility(View.VISIBLE);
                                    finDePartie = true;
                                }
                            } else {
                                Animation animShake = AnimationUtils.loadAnimation(LazerBattle.this, R.anim.shake);
                                editText.startAnimation(animShake);
                                progress = progress - Constantes.MULTIPLIER_LAZER_BATTLE_FIGHT * compteur / 2;
                                compteur = 1;
                                if(progress <= 0) {
                                    finDePartie = true;
                                    defeat.setVisibility(View.VISIBLE);
                                    defeat();
                                    retryButton.setVisibility(View.VISIBLE);
                                }
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

    @Override
    protected void onStop() {
        super.onStop();
        finDePartie = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finDePartie = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finDePartie = true;
    }

    protected void change_visibility(int visibility){
        editText.setVisibility(visibility);
        textView.setVisibility(visibility);
        background.setVisibility(visibility);
        screen.setVisibility(visibility);
        player1.setVisibility(visibility);
        player2.setVisibility(visibility);
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