package fr.ensisa.minimaths;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private Animation animSlideIn, animZoomIn1, animZoomIn2;
    private CardView header;
    private ImageButton btn1, btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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