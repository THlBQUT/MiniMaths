package fr.ensisa.minimaths.meteorite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import fr.ensisa.minimaths.MainActivity;
import fr.ensisa.minimaths.lazerbattle.DefeatActivity;
import fr.ensisa.minimaths.lazerbattle.LazerBattle;

public class MeteorActivity extends Activity {

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.bundle = savedInstanceState;

        // Set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set No Title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.setContentView(new GameSurface(this));
    }

    public void setGameOver(){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }
}