package fr.ensisa.minimaths;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fr.ensisa.minimaths.lazerbattle.LazerBattle;

public class Quizz_Menu extends AppCompatActivity {

    private Animation animSlideIn3, animZoomIn9, animZoomIn10, animZoomIn11, animZoomIn111;
    private CardView header3;
    private RelativeLayout btn9, btn10, btn11, btn111;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Vibrator vibrator;
    private TextView ms_facile, np_facile, ms_moyen, np_moyen, ms_difficile, np_difficile, ms_auto, np_auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz_menu);

        preferences = getSharedPreferences("SHARED_PREF_MAIN", MODE_PRIVATE);
        editor = preferences.edit();
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        header3 = findViewById(R.id.header_quizmenu);
        btn9 = findViewById(R.id.quiz_facile);
        btn10 = findViewById(R.id.quiz_moyen);
        btn11 = findViewById(R.id.quiz_difficile);
        btn111 = findViewById(R.id.quiz_auto);
        ms_facile = findViewById(R.id.quiz_meilleurScore_facile);
        ms_moyen = findViewById(R.id.quiz_meilleurScore_moyen);
        ms_difficile = findViewById(R.id.quiz_meilleurScore_difficile);
        ms_auto = findViewById(R.id.quiz_meilleurScore_auto);
        np_facile = findViewById(R.id.quiz_nbParties_facile);
        np_moyen = findViewById(R.id.quiz_nbParties_moyen);
        np_difficile = findViewById(R.id.quiz_nbParties_difficile);
        np_auto = findViewById(R.id.quiz_nbParties_auto);

        ms_facile.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_MS_FACILE", 0)));
        ms_moyen.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_MS_MOYEN", 0)));
        ms_difficile.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_MS_DIFFICILE", 0)));
        ms_auto.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_MS_AUTO", 0)));
        np_facile.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_NP_FACILE", 0)));
        np_moyen.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_NP_MOYEN", 0)));
        np_difficile.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_NP_DIFFICILE", 0)));
        np_auto.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_NP_AUTO", 0)));

        animSlideIn3 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
        animZoomIn9 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        animZoomIn10 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        animZoomIn11 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        animZoomIn111 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);

        animSlideIn3.setStartOffset(300);
        animZoomIn9.setStartOffset(600);
        animZoomIn10.setStartOffset(800);
        animZoomIn11.setStartOffset(1000);
        animZoomIn111.setStartOffset(1200);

        header3.startAnimation(animSlideIn3);
        btn9.startAnimation(animZoomIn9);
        btn10.startAnimation(animZoomIn10);
        btn11.startAnimation(animZoomIn11);
        btn111.startAnimation(animZoomIn111);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualiser les scores lorsqu'on retourne sur la page de choix de la difficulté
        ms_facile.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_MS_FACILE", 0)));
        ms_moyen.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_MS_MOYEN", 0)));
        ms_difficile.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_MS_DIFFICILE", 0)));
        ms_auto.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_MS_AUTO", 0)));
        np_facile.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_NP_FACILE", 0)));
        np_moyen.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_NP_MOYEN", 0)));
        np_difficile.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_NP_DIFFICILE", 0)));
        np_auto.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_NP_AUTO", 0)));
    }

    public void goQuizzOnEasy(View v){
        Intent intent = new Intent(this, LoadingGame.class);
        intent.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, Constantes.ID_DIFFICULTY_FACILE);
        intent.putExtra("class", Quizz.class);
        startActivity(intent);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
        editor.putInt("SHARED_PREF_MAIN_QUIZ_NP_FACILE",preferences.getInt("SHARED_PREF_MAIN_QUIZ_NP_FACILE", 0)+1);
        editor.apply();
    }

    public void goQuizzOnMedium(View v){
        Intent intent = new Intent(this, LoadingGame.class);
        intent.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, Constantes.ID_DIFFICULTY_MEDIUM);
        intent.putExtra("class", Quizz.class);
        startActivity(intent);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
        editor.putInt("SHARED_PREF_MAIN_QUIZ_NP_MOYEN",preferences.getInt("SHARED_PREF_MAIN_QUIZ_NP_MOYEN", 0)+1);
        editor.apply();
    }
    public void goQuizzOnHard(View v){
        Intent intent = new Intent(this, LoadingGame.class);
        intent.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, Constantes.ID_DIFFICULTY_DIFFICILE);
        intent.putExtra("class", Quizz.class);
        startActivity(intent);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
        editor.putInt("SHARED_PREF_MAIN_QUIZ_NP_DIFFICILE",preferences.getInt("SHARED_PREF_MAIN_QUIZ_NP_DIFFICILE", 0)+1);
        editor.apply();
    }

    public void goQuizzOnAuto(View v){
        Intent intent = new Intent(this, LoadingGame.class);
        intent.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, Constantes.ID_DIFFICULTY_RELATIVE);
        intent.putExtra("class", Quizz.class);
        startActivity(intent);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
        editor.putInt("SHARED_PREF_MAIN_QUIZ_NP_AUTO",preferences.getInt("SHARED_PREF_MAIN_QUIZ_NP_AUTO", 0)+1);
        editor.apply();
    }

    public void backButton(View v){
        onBackPressed();
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public void goToHome(View v){
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
        overridePendingTransition(0, android.R.anim.slide_out_right);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public void goToSettings(View v){
        Intent settingsIntent = new Intent(this, Settings.class);
        startActivity(settingsIntent);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }
}