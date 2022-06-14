package fr.ensisa.minimaths;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import fr.ensisa.minimaths.lazerbattle.LazerBattle;

public class Quizz_Menu extends AppCompatActivity {

    private Animation animSlideIn3, animZoomIn9, animZoomIn10, animZoomIn11;
    private CardView header3;
    private RelativeLayout btn9, btn10, btn11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz_menu);
        header3 = findViewById(R.id.header_quizmenu);
        btn9 = findViewById(R.id.quiz_facile);
        btn10 = findViewById(R.id.quiz_moyen);
        btn11 = findViewById(R.id.quiz_difficile);
        animSlideIn3 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
        animSlideIn3.setStartOffset(300);
        animZoomIn9 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        animZoomIn9.setStartOffset(600);
        animZoomIn10 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        animZoomIn10.setStartOffset(800);
        animZoomIn11 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        animZoomIn11.setStartOffset(1000);
        header3.startAnimation(animSlideIn3);
        btn9.startAnimation(animZoomIn9);
        btn10.startAnimation(animZoomIn10);
        btn11.startAnimation(animZoomIn11);
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