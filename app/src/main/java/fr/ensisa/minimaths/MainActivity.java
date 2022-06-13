package fr.ensisa.minimaths;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
        header = findViewById(R.id.header_main);
        btn1 = findViewById(R.id.soloButton);
        btn2 = findViewById(R.id.multiButton);
        animSlideIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
        animSlideIn.setStartOffset(300);
        animZoomIn1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        animZoomIn1.setStartOffset(800);
        animZoomIn2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        animZoomIn2.setStartOffset(1200);
        header.startAnimation(animSlideIn);
        btn1.startAnimation(animZoomIn1);
        btn2.startAnimation(animZoomIn2);
        textPseudo = findViewById(R.id.main_textview_pseudo);
        textPseudo.setText(preferences.getString("SHARED_PREF_MAIN_PSEUDO", "Pseudo"));
        account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null)
            Toast.makeText(getApplicationContext(),"Welcome " + account.getDisplayName(), Toast.LENGTH_LONG).show();
    }

    public void goToSoloGames(View v){
        Intent soloGamesIntent = new Intent(this, SoloGameList.class);
        startActivity(soloGamesIntent);
    }

    public void goToHome(View v){

    }

    public void goToRanking(View v){
//
    }

    public void goToSettings(View v){
        Intent settingsIntent = new Intent(this, Settings.class);
        startActivity(settingsIntent);
    }

    public void backButton(View v){
        onBackPressed();
    }
}