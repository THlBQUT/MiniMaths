package fr.ensisa.minimaths.lazerbattle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import fr.ensisa.minimaths.Constantes;
import fr.ensisa.minimaths.MainActivity;
import fr.ensisa.minimaths.R;
import fr.ensisa.minimaths.Settings;

public class LazerBattleMenu extends AppCompatActivity {

    private Animation animSlideIn4, animZoomIn12, animZoomIn13, animZoomIn14;
    private CardView header4;
    private RelativeLayout btn12, btn13, btn14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazer_battle_menu);
        header4 = findViewById(R.id.header_lazermenu);
        btn12 = findViewById(R.id.lazer_facile);
        btn13 = findViewById(R.id.lazer_moyen);
        btn14 = findViewById(R.id.lazer_difficile);
        animSlideIn4 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
        animSlideIn4.setStartOffset(300);
        animZoomIn12 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        animZoomIn12.setStartOffset(600);
        animZoomIn13 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        animZoomIn13.setStartOffset(800);
        animZoomIn14 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        animZoomIn14.setStartOffset(1000);
        header4.startAnimation(animSlideIn4);
        btn12.startAnimation(animZoomIn12);
        btn13.startAnimation(animZoomIn13);
        btn14.startAnimation(animZoomIn14);
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

    public void goToHome(View v){
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }

    public void goToSettings(View v){
        Intent settingsIntent = new Intent(this, Settings.class);
        startActivity(settingsIntent);
    }

}