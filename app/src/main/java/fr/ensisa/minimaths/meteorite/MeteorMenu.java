package fr.ensisa.minimaths.meteorite;

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

import fr.ensisa.minimaths.Constantes;
import fr.ensisa.minimaths.LoadingGame;
import fr.ensisa.minimaths.MainActivity;
import fr.ensisa.minimaths.Quizz;
import fr.ensisa.minimaths.R;
import fr.ensisa.minimaths.Ranking;
import fr.ensisa.minimaths.Settings;

public class MeteorMenu extends AppCompatActivity {

    private Animation animSlideIn6, animZoomIn21;
    private CardView header6;
    private RelativeLayout btn21;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Vibrator vibrator;
    private TextView ms, np;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteor_menu);

        preferences = getSharedPreferences("SHARED_PREF_MAIN", MODE_PRIVATE);
        editor = preferences.edit();
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        header6 = findViewById(R.id.header_meteormenu);
        btn21 = findViewById(R.id.jouer_meteorite);
        ms = findViewById(R.id.meteorite_meilleurScore);
        np = findViewById(R.id.meteorite_nbParties);
        ms.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_METEORITE_MS", 0)));
        np.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_METEORITE_NP", 0)));
        animSlideIn6 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
        animZoomIn21 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        animSlideIn6.setStartOffset(300);
        animZoomIn21.setStartOffset(600);
        header6.startAnimation(animSlideIn6);
        btn21.startAnimation(animZoomIn21);
    }

    public void goMeteorite(View v){
        Intent intent = new Intent(this, LoadingGame.class);
        intent.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, Constantes.ID_DIFFICULTY_RELATIVE);
        intent.putExtra("class", MeteorActivity.class);
        startActivity(intent);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
        editor.putInt("SHARED_PREF_MAIN_METEORITE_NP",preferences.getInt("SHARED_PREF_MAIN_METEORITE_NP", 0)+1);
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
}