package fr.ensisa.minimaths;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import fr.ensisa.minimaths.Constantes;
import fr.ensisa.minimaths.Quizz;
import fr.ensisa.minimaths.R;
import fr.ensisa.minimaths.lazerbattle.LazerBattle;
import fr.ensisa.minimaths.lazerbattle.LazerBattleMenu;

public class QuizzDefeat extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameoverquizz);

        preferences = getSharedPreferences("SHARED_PREF_MAIN", MODE_PRIVATE);
        editor = preferences.edit();
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
    }

    public void RetryQuizz(View v){
        Intent activityQuizz = new Intent(this, Quizz.class);
        Bundle extras = getIntent().getExtras();
        activityQuizz.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, extras.getString(Constantes.ID_DIFFICULTY_NAME_EXTRAS));
        startActivity(activityQuizz);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
        this.finish();
    }

    public void backButton(View v){
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
        finish();
    }
}