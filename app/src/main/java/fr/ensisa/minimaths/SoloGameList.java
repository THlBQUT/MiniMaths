package fr.ensisa.minimaths;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import fr.ensisa.minimaths.lazerbattle.LazerBattleMenu;
import fr.ensisa.minimaths.meteorite.MeteorActivity;

public class SoloGameList extends AppCompatActivity {

    private Animation animSlideIn2, animZoomIn5, animZoomIn6, animZoomIn7, animZoomIn8;
    private CardView header2;
    private RelativeLayout btn5, btn6, btn7, btn8;
    private Vibrator vibrator;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_game_list);

        preferences = getSharedPreferences("SHARED_PREF_MAIN", MODE_PRIVATE);
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        header2 = findViewById(R.id.header_sologamelist);
        btn5 = findViewById(R.id.solo_btn1);
        btn6 = findViewById(R.id.solo_btn2);
        btn7 = findViewById(R.id.solo_btn3);
        btn8 = findViewById(R.id.solo_btn4);

        animSlideIn2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
        animZoomIn5 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        animZoomIn6 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        animZoomIn7 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        animZoomIn8 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);

        animSlideIn2.setStartOffset(300);
        animZoomIn5.setStartOffset(600);
        animZoomIn6.setStartOffset(800);
        animZoomIn7.setStartOffset(1000);
        animZoomIn8.setStartOffset(1200);

        header2.startAnimation(animSlideIn2);
        btn5.startAnimation(animZoomIn5);
        btn6.startAnimation(animZoomIn6);
        btn7.startAnimation(animZoomIn7);
        btn8.startAnimation(animZoomIn8);
    }

    public void backButton(View v){
        onBackPressed();
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public void returnHome(View v){
        setContentView(R.layout.activity_main);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public void goToHome(View v){
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
        overridePendingTransition(0, android.R.anim.slide_out_right);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public void goToSettings(View v){
        Intent settingsIntent = new Intent(this, Settings.class);
        startActivity(settingsIntent);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public void goToLazerBattle(View v){
        Intent activityLazer = new Intent(this, LazerBattleMenu.class);
        startActivity(activityLazer);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public void goToMeteorite(View v){
        Intent activityMeteorite = new Intent(this, MeteorActivity.class);
        startActivity(activityMeteorite);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public void goToQuizz(View v){
        Intent activityQuizz = new Intent(this, Quizz_Menu.class);
        startActivity(activityQuizz);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }
    public void goToLabyrinthTip(View v) {
        Toast viewToast = Toast.makeText(this, "Retrouvez les nombres et opérateurs perdus dans le labyrinthe", Toast.LENGTH_LONG);
        viewToast.show();
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public void goToQuizzTip(View v) {
        Toast viewToast = Toast.makeText(this, "Répondez aux divers calculs successifs posés dans le Quizz", Toast.LENGTH_LONG);
        viewToast.show();
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public void goToLazerTip(View v) {
        Toast viewToast = Toast.makeText(this, "Répondez aux calculs le plus rapidement possible pour remporter le duel", Toast.LENGTH_LONG);
        viewToast.show();
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public void goToMeteorTip(View v) {
        Toast viewToast = Toast.makeText(this, "Interceptez les météorites correctes pour résoudre le calcul proposé", Toast.LENGTH_LONG);
        viewToast.show();
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }
}
