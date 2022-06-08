package fr.ensisa.minimaths;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LazerBattleMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazer_battle_menu);
    }

    public void goOnEasy(View v){
        Intent intent = new Intent(this, LazerBattle.class);
        intent.putExtra("DIFFICULTY", "FACILE");
        startActivity(intent);
    }

    public void goOnMedium(View v){
        Intent intent = new Intent(this, LazerBattle.class);
        intent.putExtra("DIFFICULTY", "MOYEN");
        startActivity(intent);
    }
    public void goOnHard(View v){
        Intent intent = new Intent(this, LazerBattle.class);
        intent.putExtra("DIFFICULTY", "DIFFICILE");
        startActivity(intent);
    }
}