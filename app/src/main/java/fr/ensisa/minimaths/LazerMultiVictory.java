package fr.ensisa.minimaths;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;

import fr.ensisa.minimaths.lazerbattle.LazerBattle;

public class LazerMultiVictory extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazerwin);

        preferences = getSharedPreferences("SHARED_PREF_MAIN", MODE_PRIVATE);
        editor = preferences.edit();
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
    }

    public void goHome(View v){
        Intent home = new Intent(this, PartyList.class);
        startActivity(home);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
        finish();
    }
}