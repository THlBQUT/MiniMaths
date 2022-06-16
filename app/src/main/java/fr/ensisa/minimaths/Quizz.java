package fr.ensisa.minimaths;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class Quizz extends AppCompatActivity {

    Equation equation;
    private TextView score;
    private ArrayList<Button> buttonList = new ArrayList<>();
    private TextView equationText;
    private int buttonChoose;
    private int vies = 2;
    private int actualScore;
    private String difficulty;
    private Vibrator vibrator;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Animation animSlideIn18;
    private CardView header18;
    private MediaPlayer mp;
    private boolean relativeDifficulty = false;
    private int compteurRelative = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Quizz","Oncreate");
        setContentView(R.layout.activity_quizz);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {this.difficulty= extras.getString(Constantes.ID_DIFFICULTY_NAME_EXTRAS);}
        if(Objects.equals(this.difficulty, Constantes.ID_DIFFICULTY_RELATIVE)) {
            difficulty = Constantes.ID_DIFFICULTY_FACILE;
            relativeDifficulty = true;
        }

        preferences = getSharedPreferences("SHARED_PREF_MAIN", MODE_PRIVATE);
        editor = preferences.edit();
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        header18 = findViewById(R.id.header_quiz);
        animSlideIn18 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
        animSlideIn18.setStartOffset(300);
        header18.startAnimation(animSlideIn18);

        this.equation = new Equation(difficulty);

        score = this.findViewById(R.id.score);
        equationText = this.findViewById(R.id.equation);

        buttonList.add(this.findViewById(R.id.button1));
        buttonList.add(this.findViewById(R.id.button2));
        buttonList.add(this.findViewById(R.id.button3));
        buttonList.add(this.findViewById(R.id.button4));

        soundSetup();

        actualScore = 0;
        game();
    }

    private void game() {
        ImageView img = findViewById(R.id.TV);
        img.setImageResource(R.drawable.tv_equation);
        for(int i = 0; i < 4; i++){
            ((Button) buttonList.get(i)).setBackgroundColor(getColor(R.color.default_quizz));
        }
        this.equation = new Equation(difficulty);
        this.equationText.setText(equation.getEquation());
        this.score.setText(String.valueOf(actualScore));
        buttonChoose = (int) (Math.random() * 4);
        while (buttonChoose > 4) {
            buttonChoose = (int) (Math.random() * 4);
        }
        setButton();
    }

    public void setButton(){
        for(int i = 0; i < 4; i++){
            if(i == buttonChoose){
                ((Button) buttonList.get(buttonChoose)).setText(String.valueOf(equation.getResultat()));
            }
            else{
                int interval = (int) (Math.random() * 25) + 1;
                int operation = (int) (Math.random() * 2);
                if(operation == 1){
                    ((Button) buttonList.get(i)).setText(String.valueOf(equation.getResultat()+interval));
                }
                else {
                    ((Button) buttonList.get(i)).setText(String.valueOf(equation.getResultat()-interval));
                }
            }
        }
    }

    public void checkButton(View view){
        if(view.getId() == buttonList.get(buttonChoose).getId()){
            this.actualScore++;
            compteurRelative++;
            if(relativeDifficulty){
                if(compteurRelative == Constantes.lazerChangeDiffultyToHard || compteurRelative == Constantes.lazerChangeDiffultyToMedium)
                    difficulty = equation.changeDifficultyUp(difficulty);
            }
            Handler handler = new Handler();
            ((Button)view).setBackgroundColor(getColor(R.color.true_quizz));
            ImageView img = findViewById(R.id.TV);
            TextView txt = findViewById(R.id.equation);
            txt.setText("");
            img.setImageResource(R.drawable.tv_changement);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(500);
                    game();
                }
            });
        }
        else{
            if(vies == 0){
                Handler handler = new Handler();
                ImageView img = findViewById(R.id.heart1);
                img.setImageResource(R.drawable.emptyheart);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(500);
                    }
                });
                Intent activityDefeat = new Intent(Quizz.this, QuizzDefeat.class);
                activityDefeat.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, difficulty);
                startActivity(activityDefeat);
                overridePendingTransition(0, R.anim.zoom_exit);

                String nomParametres = "SHARED_PREF_MAIN_QUIZ_MS_" + difficulty;
                if (actualScore > preferences.getInt(nomParametres, 0)){
                    editor.putInt(nomParametres, actualScore);
                    editor.apply();
                }
                mp.stop();
                this.finish();
            }
            else{
                this.vies--;
                if(relativeDifficulty){
                    difficulty = equation.changeDifficultyDown(difficulty);
                }
            }
            if (this.vies == 1) {
                ImageView img = findViewById(R.id.heart3);
                img.setImageResource(R.drawable.emptyheart);
            }
            if (this.vies == 0) {
                ImageView img = findViewById(R.id.heart2);
                img.setImageResource(R.drawable.emptyheart);
            }
            ((Button)view).setBackgroundColor(getColor(R.color.false_quizz));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mp.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.stop();
    }

    public void goToHome(View v){
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
        overridePendingTransition(0, android.R.anim.slide_out_right);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public void goToRanking(View v){
        Intent ranking = new Intent(this, Ranking.class);
        startActivity(ranking);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public void goToSettings(View v){
        Intent settingsIntent = new Intent(this, Settings.class);
        startActivity(settingsIntent);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public void backButton(View v){
        onBackPressed();
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    private void soundSetup(){
        if (preferences.getBoolean("SHARED_PREF_MAIN_MUSIQUE", true)) {
            this.mp = MediaPlayer.create(getApplicationContext(), R.raw.quizmp3);
            this.mp.start();
            this.mp.setLooping(true);
        }
    }
}