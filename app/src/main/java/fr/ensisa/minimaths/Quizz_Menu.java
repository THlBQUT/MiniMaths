package fr.ensisa.minimaths;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import fr.ensisa.minimaths.lazerbattle.LazerBattle;

public class Quizz_Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz_menu);
    }

    public void goQuizzOnEasy(View v){
        Intent intent = new Intent(this, Quizz.class);
        intent.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, Constantes.ID_DIFFICULTY_FACILE);
        startActivity(intent);
    }

    public void goQuizzOnMedium(View v){
        Intent intent = new Intent(this, Quizz.class);
        intent.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, Constantes.ID_DIFFICULTY_MEDIUM);
        startActivity(intent);
    }
    public void goQuizzOnHard(View v){
        Intent intent = new Intent(this, Quizz.class);
        intent.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, Constantes.ID_DIFFICULTY_DIFFICILE);
        startActivity(intent);
    }
}