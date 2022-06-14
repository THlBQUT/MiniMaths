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

    private Animation animSlideIn3, animZoomIn9, animZoomIn10, animZoomIn11;
    private CardView header3;
    private RelativeLayout btn9, btn10, btn11;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Vibrator vibrator;
    private TextView ms_facile, np_facile, nv_facile, ms_moyen, np_moyen, nv_moyen, ms_difficile, np_difficile, nv_difficile;

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
        ms_facile = findViewById(R.id.quiz_meilleurScore_facile);
        ms_moyen = findViewById(R.id.quiz_meilleurScore_moyen);
        ms_difficile = findViewById(R.id.quiz_meilleurScore_difficile);
        np_facile = findViewById(R.id.quiz_nbParties_facile);
        np_moyen = findViewById(R.id.quiz_nbParties_moyen);
        np_difficile = findViewById(R.id.quiz_nbParties_difficile);
        nv_facile = findViewById(R.id.quiz_nbVictoires_facile);
        nv_moyen = findViewById(R.id.quiz_nbVictoires_moyen);
        nv_difficile = findViewById(R.id.quiz_nbVictoires_difficile);

        ms_facile.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_MS_FACILE", 0)));
        ms_moyen.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_MS_MOYEN", 0)));
        ms_difficile.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_MS_DIFFICILE", 0)));
        np_facile.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_NP_FACILE", 0)));
        np_moyen.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_NP_MOYEN", 0)));
        np_difficile.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_NP_DIFFICILE", 0)));
        nv_facile.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_NV_FACILE", 0)));
        nv_moyen.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_NV_MOYEN", 0)));
        nv_difficile.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_QUIZ_NV_DIFFICILE", 0)));

        animSlideIn3 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
        animZoomIn9 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        animZoomIn10 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        animZoomIn11 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);

        animSlideIn3.setStartOffset(300);
        animZoomIn9.setStartOffset(600);
        animZoomIn10.setStartOffset(800);
        animZoomIn11.setStartOffset(1000);

        header3.startAnimation(animSlideIn3);
        btn9.startAnimation(animZoomIn9);
        btn10.startAnimation(animZoomIn10);
        btn11.startAnimation(animZoomIn11);
    }

    public void goQuizzOnEasy(View v){
        Intent intent = new Intent(this, Quizz.class);
        intent.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, Constantes.ID_DIFFICULTY_FACILE);
        startActivity(intent);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public void goQuizzOnMedium(View v){
        Intent intent = new Intent(this, Quizz.class);
        intent.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, Constantes.ID_DIFFICULTY_MEDIUM);
        startActivity(intent);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }
    public void goQuizzOnHard(View v){
        Intent intent = new Intent(this, Quizz.class);
        intent.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, Constantes.ID_DIFFICULTY_DIFFICILE);
        startActivity(intent);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
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