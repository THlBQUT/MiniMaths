package fr.ensisa.minimaths.lazerbattle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import fr.ensisa.minimaths.Constantes;
import fr.ensisa.minimaths.R;

public class LazerBattleMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazer_battle_menu);
    }

    public void goOnEasy(View v){
        Intent intent = new Intent(this, LazerBattle.class);
        intent.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, Constantes.ID_DIFFICULTY_FACILE);
        startActivity(intent);
    }

    public void goOnMedium(View v){
        Intent intent = new Intent(this, LazerBattle.class);
        intent.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, Constantes.ID_DIFFICULTY_MEDIUM);
        startActivity(intent);
    }
    public void goOnHard(View v){
        Intent intent = new Intent(this, LazerBattle.class);
        intent.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, Constantes.ID_DIFFICULTY_DIFFICILE);
        startActivity(intent);
    }

    public void backButton(View v){
        onBackPressed();
    }
}