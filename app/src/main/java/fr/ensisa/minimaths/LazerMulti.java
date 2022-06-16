package fr.ensisa.minimaths;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import fr.ensisa.minimaths.lazerbattle.DefeatActivity;
import fr.ensisa.minimaths.lazerbattle.LazerBattle;
import fr.ensisa.minimaths.lazerbattle.WinActivity;

public class LazerMulti extends AppCompatActivity {

    private long progress = 50; //0 victoire du joueur gauche et 100 celui du joueur droite
    private TextView textView, combo, combo2;
    private EditText editText;
    private ImageView background, screen, player1, player2, lazerred, lazerblue, lazershock;
    private Equation equation;
    private long compteur1 = 0;
    private long compteur2 = 0;
    private int compteurMax = 0;
    private String difficulty = Constantes.DEFAULT_DIFFICULTY;
    private boolean isIntroSkip = false;
    private boolean finDePartie = false;
    private boolean relativeDifficulty = false;
    private Thread thread;
    private float initialX;
    private MediaPlayer mp;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Vibrator vibrator;

    private String roomName;
    private String role;
    private int determineIfHostOrGuest;// Vaut 1 pour un host et -1 pour un guest
    private DatabaseReference reference;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazer);
        Bundle extras = getIntent().getExtras();

        preferences = getSharedPreferences("SHARED_PREF_MAIN", MODE_PRIVATE);
        editor = preferences.edit();
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        if(extras != null) {
            roomName = extras.getString("ID_PARTY");
            difficulty = extras.getString(Constantes.ID_DIFFICULTY_NAME_EXTRAS);
            role = extras.getString("ROLE");
        }
        if(Objects.equals(this.difficulty, Constantes.ID_DIFFICULTY_RELATIVE)){
            this.difficulty = Constantes.ID_DIFFICULTY_FACILE;
            this.relativeDifficulty = true;
        }
        this.equation = new Equation(difficulty);

        this.textView = this.findViewById((R.id.textlazer));
        this.editText = this.findViewById(R.id.textinputlazer);
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

        soundSetup();

        if(Objects.equals(role, "HOST")){this.determineIfHostOrGuest = 1;}
        else{this.determineIfHostOrGuest = -1;}
        database = FirebaseDatabase.getInstance("https://minimaths-84e80-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = database.getReference("ingame_room/" + roomName);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("progress").getValue() != null)
                    progress = ((long) snapshot.child("progress").getValue());
                if(snapshot.child("comboHost").getValue() != null)
                    compteur1 = ((long) snapshot.child("comboHost").getValue());
                if(snapshot.child("comboGuest").getValue() != null)
                    compteur2 = ((long) snapshot.child("comboGuest").getValue());
                    uiUpdateLazer();
                if (compteur1 >= 3) {
                    combo.setVisibility(View.VISIBLE);
                    Animation animShake = AnimationUtils.loadAnimation(LazerMulti.this, R.anim.shakecombo);
                    combo.startAnimation(animShake);
                    combo.setText(Long.toString(compteur1));
                    changeColorComboButton(combo, compteur1);
                }
                if(compteur2 >= 3){
                    combo2.setVisibility(View.VISIBLE);
                    Animation animShake = AnimationUtils.loadAnimation(LazerMulti.this, R.anim.shakecombo);
                    combo2.startAnimation(animShake);
                    combo2.setText(Long.toString(compteur2));
                    changeColorComboButton(combo2, compteur2);
                }
                if(progress >=100){
                    if(Objects.equals(role, "HOST")) {
                        try {
                            victory();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                        defeat();
                }
                else if(progress <=0){
                    if(Objects.equals(role, "HOST"))
                        defeat();
                    else {
                        try {
                            victory();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (compteur1 > compteurMax)
                    compteurMax = (int)compteur1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                                if(Objects.equals(role, "HOST")){
                                    compteur1 += 1;
                                    progress = progress + Constantes.MULTIPLIER_LAZER_BATTLE_FIGHT * (int) (compteur1 / 2 * determineIfHostOrGuest);
                                }
                                else{
                                    compteur2+=1;
                                    progress = progress + Constantes.MULTIPLIER_LAZER_BATTLE_FIGHT * (int) (compteur2 / 2 * determineIfHostOrGuest);

                                }
                                if(progress >= 100 && !finDePartie && Objects.equals(role, "HOST")
                                || progress <=0 && !finDePartie && Objects.equals(role, "GUEST")) {
                                    victory();
                                    finDePartie = true;
                                }
                                if(relativeDifficulty){
                                    if(Objects.equals(role, "HOST")){
                                        if (compteur1 == Constantes.lazerChangeDiffultyToHard || compteur1 == Constantes.lazerChangeDiffultyToMedium)
                                            difficulty = equation.changeDifficultyUp(difficulty);
                                    }
                                    else{
                                        if (compteur2 == Constantes.lazerChangeDiffultyToHard || compteur2 == Constantes.lazerChangeDiffultyToMedium)
                                            difficulty = equation.changeDifficultyUp(difficulty);
                                    }
                                }
                            } else {
                                Animation animShake = AnimationUtils.loadAnimation(LazerMulti.this, R.anim.shake);
                                editText.startAnimation(animShake);
                                progress = progress - Constantes.MULTIPLIER_LAZER_BATTLE_FIGHT;
                                if(Objects.equals(role, "HOST")){compteur1 = 0;}
                                else{compteur2 = 0;}
                                combo.clearAnimation();
                                combo.setVisibility(View.INVISIBLE);
                                combo2.setVisibility(View.INVISIBLE);
                                if(relativeDifficulty){
                                    difficulty = equation.changeDifficultyDown(difficulty);
                                }
                                if(progress >= 100 && !finDePartie && Objects.equals(role, "GUEST")
                                        || progress <=0 && !finDePartie && Objects.equals(role, "HOST")) {
                                    finDePartie = true;
                                    defeat();
                                }
                            }

                            DatabaseReference progressOnline = reference.child("progress");
                            progressOnline.setValue(progress);
                            if(Objects.equals(role, "HOST")) {
                                DatabaseReference combo1Online = reference.child("comboHost");
                                combo1Online.setValue(compteur1);
                            }
                            else {
                                DatabaseReference combo2Online = reference.child("comboGuest");
                                combo2Online.setValue(compteur2);
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
        mp.stop();
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

    private void defeat(){
        uiUpdateLazer();
        Animation animDead;
        if(Objects.equals(this.role, "HOST"))
            animDead = AnimationUtils.loadAnimation(LazerMulti.this, R.anim.defeat_dead_left_character);
        else
            animDead = AnimationUtils.loadAnimation(LazerMulti.this, R.anim.victory_dead_right_character);
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

                noLazerPrompt();
                Intent activityDefeat = new Intent(LazerMulti.this, DefeatActivity.class);
                activityDefeat.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, difficulty);
                startActivity(activityDefeat);
                overridePendingTransition(0, R.anim.zoom_exit);

                String nomParametresMS = "SHARED_PREF_MAIN_LAZER_MS_" + difficulty;
                if (compteurMax > preferences.getInt(nomParametresMS, 0)){
                    editor.putInt(nomParametresMS, compteurMax);
                    editor.apply();
                }
                mp.stop();
                if(Objects.equals(role, "HOST"))
                    reference.removeValue();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        player1.startAnimation(animDead);
    }

    private  void victory() throws InterruptedException {
        uiUpdateLazer();
        Animation animDead;
        if(Objects.equals(role, "HOST"))
            animDead = AnimationUtils.loadAnimation(LazerMulti.this, R.anim.victory_dead_right_character);
        else
            animDead = AnimationUtils.loadAnimation(LazerMulti.this, R.anim.defeat_dead_left_character);
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
                noLazerPrompt();
                mp.stop();
                Intent activityWin = new Intent(LazerMulti.this, WinActivity.class);
                activityWin.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, difficulty);
                startActivity(activityWin);
                overridePendingTransition(0, R.anim.zoom_exit);

                String nomParametresMS = "SHARED_PREF_MAIN_LAZER_MS_" + difficulty;
                String nomParametresNV = "SHARED_PREF_MAIN_LAZER_NV_" + difficulty;
                if (compteurMax > preferences.getInt(nomParametresMS, 0)){
                    editor.putInt(nomParametresMS, compteurMax);
                    editor.apply();
                }

                if(Objects.equals(role, "HOST"))
                    reference.removeValue();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        player2.startAnimation(animDead);
    }

    public void backButton(View v){
        onBackPressed();
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
        this.finDePartie = true;
        finish();
    }

    public void goToRanking(View v){
        Intent ranking = new Intent(this, Ranking.class);
        startActivity(ranking);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
        finish();
    }

    public void goToHome(View v){
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
        overridePendingTransition(0, android.R.anim.slide_out_right);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
        this.finDePartie = true;
        finish();
    }

    public void goToSettings(View v){
        Intent settingsIntent = new Intent(this, Settings.class);
        startActivity(settingsIntent);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
        finish();
    }

    public void returnHome(View v){
        this.finDePartie = true;
        this.finish();
    }

    public void changeColorComboButton(TextView textView, long compteurCombo){
        switch ((int) compteurCombo){
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
        Animation animLazer = AnimationUtils.loadAnimation(LazerMulti.this, R.anim.lazer_shake);
        this.lazerblue.startAnimation(animLazer);
        this.lazerred.startAnimation(animLazer);
        this.lazershock.startAnimation(animLazer);
    }

    private void stopAnimation(){
        this.lazerred.clearAnimation();
        this.lazershock.clearAnimation();
        this.lazerblue.clearAnimation();
    }

    private void soundSetup(){
        if (preferences.getBoolean("SHARED_PREF_MAIN_MUSIQUE", true)) {
            this.mp = MediaPlayer.create(getApplicationContext(), R.raw.lazermp3);
            this.mp.start();
            this.mp.setLooping(true);
        }
    }
}