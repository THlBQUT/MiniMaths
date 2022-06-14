package fr.ensisa.minimaths.lazerbattle;

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
import fr.ensisa.minimaths.R;
import fr.ensisa.minimaths.Settings;

public class LazerBattleMenu extends AppCompatActivity {

    private Animation animSlideIn4, animZoomIn12, animZoomIn13, animZoomIn14;
    private Vibrator vibrator;
    private CardView header4;
    private RelativeLayout btn12, btn13, btn14;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private TextView ms_facile, np_facile, nv_facile, ms_moyen, np_moyen, nv_moyen, ms_difficile, np_difficile, nv_difficile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazer_battle_menu);

        preferences = getSharedPreferences("SHARED_PREF_MAIN", MODE_PRIVATE);
        editor = preferences.edit();
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        header4 = findViewById(R.id.header_lazermenu);
        btn12 = findViewById(R.id.lazer_facile);
        btn13 = findViewById(R.id.lazer_moyen);
        btn14 = findViewById(R.id.lazer_difficile);
        ms_facile = findViewById(R.id.lazer_meilleurScore_facile);
        ms_moyen = findViewById(R.id.lazer_meilleurScore_moyen);
        ms_difficile = findViewById(R.id.lazer_meilleurScore_difficile);
        np_facile = findViewById(R.id.lazer_nbParties_facile);
        np_moyen = findViewById(R.id.lazer_nbParties_moyen);
        np_difficile = findViewById(R.id.lazer_nbParties_difficile);
        nv_facile = findViewById(R.id.lazer_nbVictoires_facile);
        nv_moyen = findViewById(R.id.lazer_nbVictoires_moyen);
        nv_difficile = findViewById(R.id.lazer_nbVictoires_difficile);

        ms_facile.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_LAZER_MS_FACILE", 0)));
        ms_moyen.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_LAZER_MS_MOYEN", 0)));
        ms_difficile.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_LAZER_MS_DIFFICILE", 0)));
        np_facile.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_LAZER_NP_FACILE", 0)));
        np_moyen.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_LAZER_NP_MOYEN", 0)));
        np_difficile.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_LAZER_NP_DIFFICILE", 0)));
        nv_facile.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_LAZER_NV_FACILE", 0)));
        nv_moyen.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_LAZER_NV_MOYEN", 0)));
        nv_difficile.setText(Integer.toString(preferences.getInt("SHARED_PREF_MAIN_LAZER_NV_DIFFICILE", 0)));

        animSlideIn4 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
        animZoomIn12 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        animZoomIn13 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        animZoomIn14 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        animSlideIn4.setStartOffset(300);
        animZoomIn12.setStartOffset(600);
        animZoomIn13.setStartOffset(800);
        animZoomIn14.setStartOffset(1000);
        header4.startAnimation(animSlideIn4);
        btn12.startAnimation(animZoomIn12);
        btn13.startAnimation(animZoomIn13);
        btn14.startAnimation(animZoomIn14);
    }

    public void goOnEasy(View v){
        Intent intent = new Intent(this, LoadingGame.class);
        intent.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, Constantes.ID_DIFFICULTY_FACILE);
        intent.putExtra("class", LazerBattle.class);
        startActivity(intent);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public void goOnMedium(View v){
        Intent intent = new Intent(this, LoadingGame.class);
        intent.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, Constantes.ID_DIFFICULTY_MEDIUM);
        intent.putExtra("class", LazerBattle.class);
        startActivity(intent);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }
    public void goOnHard(View v){
        Intent intent = new Intent(this, LoadingGame.class);
        intent.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, Constantes.ID_DIFFICULTY_DIFFICILE);
        intent.putExtra("class", LazerBattle.class);
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