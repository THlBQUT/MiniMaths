package fr.ensisa.minimaths;

import android.os.Bundle;
import android.os.VibrationEffect;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Credits extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

    }

    public void backButton(View v){
        onBackPressed();
       /* if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
       */
        finish();
    }
}
