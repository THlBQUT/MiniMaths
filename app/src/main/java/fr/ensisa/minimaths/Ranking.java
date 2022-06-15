package fr.ensisa.minimaths;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

public class Ranking extends AppCompatActivity {

    private LinearLayout rankLayout;
    private Vibrator vibrator;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        preferences = getSharedPreferences("SHARED_PREF_MAIN", MODE_PRIVATE);
        editor = preferences.edit();
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        this.rankLayout = this.findViewById(R.id.rank);

        createRank();
    }

    public void clearRankLayout(View v) {
        this.rankLayout.removeAllViews();
    }

    private void createOneRow() {
        LinearLayout parent = new LinearLayout(this);
        parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        parent.setOrientation(LinearLayout.HORIZONTAL);

        TextView playerName = new TextView(this);
        TextView score = new TextView(this);

        playerName.setText("Pseudo du joueur ici");
        score.setText("et son score");

        parent.addView(playerName);
        parent.addView(score);

        this.rankLayout.addView(parent);
    }

    private void createRank() {
        for(int i=0; i<50; i++){
            createOneRow();
        }
    }
    public void goToHome(View v){
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
        overridePendingTransition(0, android.R.anim.slide_out_right);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
        finish();
    }

    public void goToSettings(View v){
        Intent settingsIntent = new Intent(this, Settings.class);
        startActivity(settingsIntent);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
        finish();
    }

    public void backButton(View v){
        onBackPressed();
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
        finish();
    }
}