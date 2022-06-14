package fr.ensisa.minimaths;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

public class Ranking extends AppCompatActivity {

    private LinearLayout rankLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        this.rankLayout = this.findViewById(R.id.rank);

        createRank();
    }

    public void clearRankLayout(View v) {
        this.rankLayout.removeAllViews();
    }

    private void createOneRow() {
        LinearLayout parent = new LinearLayout(this);
        parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        parent.setOrientation(LinearLayout.HORIZONTAL);

        TextView playerName = new TextView(this);
        TextView score = new TextView(this);

        playerName.setText("Pseudo du joueur ici");
        score.setText("et son score");

        parent.addView(playerName);
        parent.addView(score);

        this.rankLayout.addView(parent);
    }

    private void createRank() {
        for(int i=0; i<50; i++){
            createOneRow();
        }
    }
}