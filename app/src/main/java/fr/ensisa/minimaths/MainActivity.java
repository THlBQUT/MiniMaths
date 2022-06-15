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
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class MainActivity extends AppCompatActivity {

    private Animation animSlideIn, animZoomIn1, animZoomIn2;
    private CardView header;
    private Vibrator vibrator;
    private ImageButton btn1, btn2;
    private GoogleSignInAccount account;
    private TextView textPseudo;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("SHARED_PREF_MAIN", MODE_PRIVATE);
        editor = preferences.edit();
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        header = findViewById(R.id.header_main);
        btn1 = findViewById(R.id.soloButton);
        btn2 = findViewById(R.id.multiButton);
        textPseudo = findViewById(R.id.main_textview_pseudo);

        animSlideIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        animZoomIn1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        animZoomIn2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);

        animSlideIn.setStartOffset(300);
        animZoomIn1.setStartOffset(800);
        animZoomIn2.setStartOffset(1200);

        header.startAnimation(animSlideIn);
        btn1.startAnimation(animZoomIn1);
        btn2.startAnimation(animZoomIn2);

        textPseudo.setText(preferences.getString("SHARED_PREF_MAIN_PSEUDO", "Invité"));
        account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null){
            Toast.makeText(getApplicationContext(), "Welcome " + account.getDisplayName(), Toast.LENGTH_LONG).show();
            textPseudo.setText(account.getDisplayName());
        }
    }

    public void goToSoloGames(View v){
        Intent soloGamesIntent = new Intent(this, SoloGameList.class);
        startActivity(soloGamesIntent);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public void goToHome(View v){
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public void goToRanking(View v){
        Intent ranking = new Intent(this, Ranking.class);
        startActivity(ranking);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }
    public void goToMulti(View v){
        Intent multiplayer = new Intent(this, MultiDifficulty.class);
        startActivity(multiplayer);
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
    }
}