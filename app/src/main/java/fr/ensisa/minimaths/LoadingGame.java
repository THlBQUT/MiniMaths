package fr.ensisa.minimaths;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class LoadingGame extends AppCompatActivity {

    private Animation animFadein;
    private CardView chargementCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_game);

        chargementCard = findViewById(R.id.texte_chargement);
        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        animFadein.setStartOffset(300);
        chargementCard.startAnimation(animFadein);

        Bundle bundle = getIntent().getExtras();
        Intent intent = new Intent(this, (Class<?>) bundle.get("class"));
        intent.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, bundle.getString(Constantes.ID_DIFFICULTY_NAME_EXTRAS));

        Runnable waiting = new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1500);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_enter, 0);
            }
        };
        Thread thread1 = new Thread(waiting);
        thread1.start();
    }
}