package fr.ensisa.minimaths;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import fr.ensisa.minimaths.Constantes;
import fr.ensisa.minimaths.Quizz;
import fr.ensisa.minimaths.R;
import fr.ensisa.minimaths.lazerbattle.LazerBattle;
import fr.ensisa.minimaths.lazerbattle.LazerBattleMenu;

public class QuizzDefeat extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameoverquizz);
    }

    public void RetryQuizz(View v){
        Intent activityQuizz = new Intent(this, Quizz.class);
        Bundle extras = getIntent().getExtras();
        activityQuizz.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, extras.getString(Constantes.ID_DIFFICULTY_NAME_EXTRAS));
        startActivity(activityQuizz);
        this.finish();
    }
    public void goHome(View v){
        Intent activityLazer = new Intent(this, MainActivity.class);
        startActivity(activityLazer);
        this.finish();
    }
}