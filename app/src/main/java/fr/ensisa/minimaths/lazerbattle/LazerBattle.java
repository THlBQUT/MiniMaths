package fr.ensisa.minimaths.lazerbattle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
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

import java.util.concurrent.TimeUnit;

import fr.ensisa.minimaths.Constantes;
import fr.ensisa.minimaths.Equation;
import fr.ensisa.minimaths.R;

public class LazerBattle extends AppCompatActivity {

    private int progress = 50; //0 victoire du joueur gauche et 100 celui du joueur droite
    private TextView textView;
    private TextView combo;
    private TextView combo2;
    private EditText editText;
    private TextView victory;
    private TextView defeat;
    private Button retryButton;
    private ImageView background;
    private ImageView screen;
    private ImageView player1;
    private ImageView player2;
    private ImageView lazerred;
    private ImageView lazerblue;
    private ImageView lazershock;
    private Equation equation;
    private int compteur = 0;
    private String difficulty = Constantes.DEFAULT_DIFFICULTY;
    private boolean isIntroSkip = false;
    private boolean finDePartie = false;
    private Thread thread;
    private float initialX;

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
        this.combo = this.findViewById(R.id.combo);
        this.combo2 = this.findViewById(R.id.combo2);
        this.lazerred = this.findViewById(R.id.lazerred);
        this.lazerblue = this.findViewById(R.id.lazerblue);
        this.lazershock = this.findViewById(R.id.lazershock);
        textView.setText(equation.getEquation());

        this.initialX =  this.lazerblue.getX() + player1.getLayoutParams().width;
        this.lazerblue.getLayoutParams().width = this.lazerblue.getLayoutParams().width / 2;
        this.lazerblue.setTranslationX(-this.lazerblue.getLayoutParams().width / 2);
        this.lazerred.setX(this.player1.getX());

        Runnable runnable = new Runnable() {
            private int compteuria = 0;
            @Override
            public void run() {
                while(!finDePartie) {
                    try {
                        Thread.sleep(Constantes.SPEED_ANSWER_IA);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int ia_answer = (int) (Math.random() * 100);
                    if (ia_answer <= Constantes.PERCENTAGE_GOOD_ANSWER_IA) {
                        compteuria += 1;
                        progress = progress - Constantes.MULTIPLIER_LAZER_BATTLE_FIGHT * (int) (compteuria / 2);
                    } else {
                        progress = progress - Constantes.MULTIPLIER_LAZER_BATTLE_FIGHT;
                        compteuria = 0;
                    }
                    if (defeat.getHandler() != null) {
                        defeat.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                uiUpdateLazer();
                                if (compteuria >= 3) {
                                    combo2.setVisibility(View.VISIBLE);
                                    Animation animShake = AnimationUtils.loadAnimation(LazerBattle.this, R.anim.shakecombo);
                                    combo2.startAnimation(animShake);
                                    combo2.setText(Integer.toString(compteuria));
                                    changeColorComboButton(combo2, compteuria);
                                } else {
                                    combo2.clearAnimation();
                                    combo2.setVisibility(View.INVISIBLE);
                                }
                                if (progress <= 0 && !finDePartie) {
                                    finDePartie = true;
                                    defeat();
                                    return;
                                }
                            }
                        });
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
                            editText.setText("");
                            if (equation.getResultat() == numberInput) {
                                equation = new Equation(difficulty);
                                textView.setText(equation.getEquation());
                                compteur += 1;
                                if(compteur >= 3){
                                    combo.setVisibility(View.VISIBLE);
                                    Animation animShake = AnimationUtils.loadAnimation(LazerBattle.this, R.anim.shakecombo);
                                    combo.startAnimation(animShake);
                                    combo.setText(Integer.toString(compteur));
                                    changeColorComboButton(combo, compteur);
                                }
                                progress = progress + Constantes.MULTIPLIER_LAZER_BATTLE_FIGHT * (int) (compteur / 2);
                                if(progress >= 100 && !finDePartie) {
                                    victory();
                                    finDePartie = true;
                                }
                            } else {
                                Animation animShake = AnimationUtils.loadAnimation(LazerBattle.this, R.anim.shake);
                                editText.startAnimation(animShake);
                                progress = progress - Constantes.MULTIPLIER_LAZER_BATTLE_FIGHT;
                                compteur = 0;
                                combo.clearAnimation();
                                combo.setVisibility(View.INVISIBLE);
                                combo2.setVisibility(View.INVISIBLE);
                                if(progress <= 0 && !finDePartie) {
                                    finDePartie = true;
                                    defeat.setVisibility(View.VISIBLE);
                                    defeat();
                                    retryButton.setVisibility(View.VISIBLE);
                                }
                            }
                            uiUpdateLazer();
                            return true;
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
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
        clearGame();
    }

    @Override
    protected void onPause() {
        super.onPause();
        clearGame();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearGame();
    }

    private void clearGame(){
        finDePartie = true;
        this.finish();
    }

    protected void change_visibility(int visibility){
        editText.setVisibility(visibility);
        textView.setVisibility(visibility);
        background.setVisibility(visibility);
        screen.setVisibility(visibility);
        player1.setVisibility(visibility);
        player2.setVisibility(visibility);
        combo.clearAnimation();
        combo2.clearAnimation();
        combo.setVisibility(View.INVISIBLE);
        combo2.setVisibility(View.INVISIBLE);
        lazershock.setVisibility(visibility);
        lazerred.setVisibility(visibility);
        lazerblue.setVisibility(visibility);
    }

    public void skipIntro(View v){
        if(!this.isIntroSkip) {
            TextView intro2 = this.findViewById((R.id.lazerGame_intro_text2));
            TextView intro1 = this.findViewById((R.id.lazerGame_intro_text1));
            intro1.setVisibility(View.INVISIBLE);
            intro2.setVisibility((View.INVISIBLE));
            change_visibility(View.VISIBLE);
            this.isIntroSkip = true;
            thread.start();
            playAnimation();
        }
        if(finDePartie){
            change_visibility(View.INVISIBLE);
            this.finish();
        }
    }

    private void defeat(){
        uiUpdateLazer();
        TextView intro2 = this.findViewById((R.id.lazerGame_intro_text2));
        Animation animDead = AnimationUtils.loadAnimation(LazerBattle.this, R.anim.defeat_dead_left_character);
        animDead.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                stopAnimation();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                change_visibility(View.INVISIBLE);
                defeat.setVisibility(View.VISIBLE);
                retryButton.setVisibility(View.VISIBLE);
                intro2.setText("Cliquer pour retourner à l'accueil");
                intro2.setVisibility(View.VISIBLE);
                noLazerPrompt();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        player1.startAnimation(animDead);
    }

    private  void victory() throws InterruptedException {
        uiUpdateLazer();
        TextView intro2 = this.findViewById((R.id.lazerGame_intro_text2));
        Animation animDead = AnimationUtils.loadAnimation(LazerBattle.this, R.anim.victory_dead_right_character);
        animDead.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                stopAnimation();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                intro2.setText("Cliquer pour retourner à l'accueil");
                intro2.setVisibility(View.VISIBLE);
                change_visibility(View.INVISIBLE);
                victory.setVisibility(View.VISIBLE);
                retryButton.setVisibility(View.VISIBLE);
                noLazerPrompt();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        player2.startAnimation(animDead);
    }

    public void retry_button(View v){
        Intent intent = new Intent(this, LazerBattle.class);
        intent.putExtra("DIFFICULTY", this.difficulty);
        startActivity(intent);
        this.finish();
    }

    public void returnHome(View v){
        this.finDePartie = true;
        this.finish();
    }

    public void changeColorComboButton(TextView textView, int compteurCombo){
        switch (compteurCombo){
            case 3:
                textView.setTextColor(Color.parseColor("#0000FF"));
                break;
            case 4:
                textView.setTextColor(Color.parseColor("#00FF00"));
                break;
            case 5:
                textView.setTextColor(Color.parseColor("#FAFD0F"));
                break;
            case 6:
                textView.setTextColor(Color.parseColor("#FF6600"));
                break;
            default:
                textView.setTextColor(Color.parseColor("#FF0000"));
                break;
        }
    }

    private void uiUpdateLazer(){
        stopAnimation();
        if(progress>=100){
            this.lazerblue.getLayoutParams().width =  this.lazerred.getLayoutParams().width;
            this.lazerblue.setTranslationX(initialX - this.player1.getLayoutParams().width);
            this.lazershock.setVisibility(View.INVISIBLE);
            this.lazerred.setVisibility(View.INVISIBLE);
        }
        else if(progress<=0){
            this.lazerblue.setVisibility(View.INVISIBLE);
            this.lazershock.setVisibility(View.INVISIBLE);        }
        else {
            int previousWidthLazerBlue = this.lazerblue.getLayoutParams().width;
            this.lazerblue.getLayoutParams().width = (int) (this.lazerred.getLayoutParams().width * this.progress / 100);
            this.lazerblue.setX((float) (this.lazerred.getX()));
//            this.lazerblue.setTranslationX(-this.lazerred.getLayoutParams().width * this.progress / 200);
            if(progress <=5)
                this.lazershock.setX((int) this.player1.getX() + this.player1.getLayoutParams().width/2);
            else if(progress>=95)
                this.lazershock.setX((int) this.player2.getX() - this.player1.getLayoutParams().width/2);
            else
                this.lazershock.setX((float) (this.player1.getX() + this.player1.getLayoutParams().width/2 + this.lazerred.getLayoutParams().width * this.progress / 100 - this.lazerblue.getLayoutParams().width * 0.15));
        }
        if(!finDePartie)
            playAnimation();
    }

    private void noLazerPrompt(){
        lazershock.setVisibility(View.INVISIBLE);
    }

    private void playAnimation(){
        Animation animLazer = AnimationUtils.loadAnimation(LazerBattle.this, R.anim.lazer_shake);
        this.lazerblue.startAnimation(animLazer);
        this.lazerred.startAnimation(animLazer);
        this.lazershock.startAnimation(animLazer);
    }

    private void stopAnimation(){
        this.lazerred.clearAnimation();
        this.lazershock.clearAnimation();
        this.lazerblue.clearAnimation();
    }
}