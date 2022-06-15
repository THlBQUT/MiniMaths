package fr.ensisa.minimaths.lazerbattle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import fr.ensisa.minimaths.Constantes;
import fr.ensisa.minimaths.MainActivity;
import fr.ensisa.minimaths.R;
import fr.ensisa.minimaths.lazerbattle.LazerBattle;
import fr.ensisa.minimaths.lazerbattle.LazerBattleMenu;

public class DefeatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameoverlazer);
    }

    public void RetryLazer(View v){
        Intent activityLazer = new Intent(this, LazerBattle.class);
        Bundle extras = getIntent().getExtras();
        activityLazer.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, extras.getString(Constantes.ID_DIFFICULTY_NAME_EXTRAS));
        startActivity(activityLazer);
        this.finish();
    }
    public void goHome(View v){
        Intent activityLazer = new Intent(this, MainActivity.class);
        startActivity(activityLazer);
        this.finish();
    }
}
