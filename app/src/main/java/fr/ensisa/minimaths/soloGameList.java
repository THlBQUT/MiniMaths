package fr.ensisa.minimaths;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class soloGameList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_game_list);
    }

    public void returnHome(View v){
        setContentView(R.layout.activity_main);
    }

    public void goToLazerBattle(View v){
        Intent activityLazer = new Intent(this, LazerBattleMenu.class);
        startActivity(activityLazer);
    }

    public void goToQuizz(View v){
        Intent activityQuizz = new Intent(this, Quizz.class);
        startActivity(activityQuizz);
    }
}