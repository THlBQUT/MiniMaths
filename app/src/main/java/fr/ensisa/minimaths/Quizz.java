package fr.ensisa.minimaths;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Quizz extends AppCompatActivity {

    Equation equation;
    private TextView score;
    private ArrayList<Button> buttonList = new ArrayList<>();
    private TextView equationText;
    private int buttonChoose;
    private int vies = 2;
    private int actualScore;
    private String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Quizz","Oncreate");
        setContentView(R.layout.activity_quizz);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {this.difficulty= extras.getString("DIFFICULTY");}

        this.equation = new Equation(difficulty);

        score = this.findViewById(R.id.score);
        equationText = this.findViewById(R.id.equation);

        buttonList.add(this.findViewById(R.id.button1));
        buttonList.add(this.findViewById(R.id.button2));
        buttonList.add(this.findViewById(R.id.button3));
        buttonList.add(this.findViewById(R.id.button4));

        actualScore = 0;
        game();
    }

    private void game() {
        ImageView img = findViewById(R.id.TV);
        img.setImageResource(R.drawable.tv_equation);
        for(int i = 0; i < 4; i++){
            ((Button) buttonList.get(i)).setBackgroundColor(getColor(R.color.default_quizz));
        }
        this.equation = new Equation(difficulty);
        this.equationText.setText(equation.getEquation());
        this.score.setText(String.valueOf(actualScore));
        buttonChoose = (int) (Math.random() * 4);
        while (buttonChoose > 4) {
            buttonChoose = (int) (Math.random() * 4);
        }
        setButton();
    }

    public void setButton(){
        for(int i = 0; i < 4; i++){
            if(i == buttonChoose){
                ((Button) buttonList.get(buttonChoose)).setText(String.valueOf(equation.getResultat()));
            }
            else{
                int interval = (int) (Math.random() * 25) + 1;
                int operation = (int) (Math.random() * 1);
                if(operation == 1){
                    ((Button) buttonList.get(i)).setText(String.valueOf(equation.getResultat()+interval));
                }
                else {
                    ((Button) buttonList.get(i)).setText(String.valueOf(equation.getResultat()-interval));
                }
            }
        }
    }

    public void checkButton(View view){
        if(view.getId() == buttonList.get(buttonChoose).getId()){
            this.actualScore++;
            Handler handler = new Handler();
            ((Button)view).setBackgroundColor(getColor(R.color.true_quizz));
            ImageView img = findViewById(R.id.TV);
            TextView txt = findViewById(R.id.equation);
            txt.setText("");
            img.setImageResource(R.drawable.tv_changement);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(500);
                    game();
                }
            });
        }
        else{
            if(vies == 0){
                Handler handler = new Handler();
                ImageView img = findViewById(R.id.heart1);
                img.setImageResource(R.drawable.emptyheart);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(500);
                    }
                });
                Intent activityDefeat = new Intent(Quizz.this, QuizzDefeat.class);
                activityDefeat.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, difficulty);
                startActivity(activityDefeat);
            }
            else{
                this.vies--;
            }
            if (this.vies == 1) {
                ImageView img = findViewById(R.id.heart3);
                img.setImageResource(R.drawable.emptyheart);
            }
            if (this.vies == 0) {
                ImageView img = findViewById(R.id.heart2);
                img.setImageResource(R.drawable.emptyheart);
            }
            ((Button)view).setBackgroundColor(getColor(R.color.false_quizz));
        }
    }
}