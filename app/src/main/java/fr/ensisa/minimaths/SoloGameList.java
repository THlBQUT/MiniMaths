package fr.ensisa.minimaths;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
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

    public void goToLabyrhinte(View v){
        new AlertDialog.Builder(this)
                .setTitle("Coming Soon")
                .setMessage("Le professeur MicMath a semé la pagaille dans son laboratoire, le labyrhinte n'est pas soluble pour l'instant !")
                .setPositiveButton(android.R.string.yes, null)
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
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
        Toast viewToast = Toast.makeText(this, "Pris au piège dans son laboratoire, affrontez le professeur MicMath dans une série de questions enflammées ! Attention : l'erreur n’est pas une option !", Toast.LENGTH_LONG);
        viewToast.show();
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public void goToLazerTip(View v) {
        Toast viewToast = Toast.makeText(this, "Seul ou en multijoueur, affrontez vos adversaires dans une bataille Lazer épique au milieu d’une arène retro et augmentez votre puissance grâce au mathématiques !\n", Toast.LENGTH_LONG);
        viewToast.show();
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public void goToMeteorTip(View v) {
        Toast viewToast = Toast.makeText(this, "Précision, rapidité et calcul, rattrapez les bonnes météorites pour obtenir le meilleur score et sauvez ce qu’il reste de cette ville détruite par le professeur MicMath.", Toast.LENGTH_LONG);
        viewToast.show();
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }
}
