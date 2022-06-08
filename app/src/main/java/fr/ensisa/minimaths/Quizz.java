package fr.ensisa.minimaths;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
        difficulty = "FACILE";

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
                int interval = (int) (Math.random() * 15) + 1;
                ((Button) buttonList.get(i)).setText(String.valueOf(equation.getResultat()+interval));
            }
        }
    }

    public void checkButton(View view){
        if(view.getId() == buttonList.get(buttonChoose).getId()){
            this.actualScore++;
            game();
        }
        else{
            if(vies == 0){
                Intent soloListGame = new Intent(this, soloGameList.class);
                startActivity(soloListGame);;
            }
            else{
                this.vies--;
            }
        }
    }
}