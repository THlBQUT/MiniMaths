package fr.ensisa.minimaths;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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