package fr.ensisa.minimaths.meteorite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import fr.ensisa.minimaths.Constantes;
import fr.ensisa.minimaths.Quizz;
import fr.ensisa.minimaths.R;

public class MeteorDefeat extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameoverquizz);
    }

    public void RetryQuizz(View v){
        Intent activityMeteor = new Intent(this, MeteorActivity.class);
        startActivity(activityMeteor);
        this.finish();
    }

}

