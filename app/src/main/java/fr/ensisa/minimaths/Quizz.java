package fr.ensisa.minimaths;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class Quizz extends AppCompatActivity {

    Equation equation;
    private TextView score;
    private ArrayList<Button> buttonList = new ArrayList<Button>() {{
        Button button1; Button button2; Button button3; Button button4;
    }};
    private TextView equationText;
    private int buttonChoose;
    private int vies = 3;
    private int actualScore;
    private String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);
        Bundle extras = getIntent().getExtras();
        difficulty = "FACILE";

        this.equation = new Equation(difficulty);

        score = this.findViewById(R.id.score);
        equationText = this.findViewById(R.id.equation);

        buttonList.set(0,this.findViewById(R.id.button1));
        buttonList.set(1,this.findViewById(R.id.button2));
        buttonList.set(2,this.findViewById(R.id.button3));
        buttonList.set(3,this.findViewById(R.id.button4));

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

    private void checkButton(View view){
        if(view.getId() == buttonList.get(buttonChoose).getId()){
            this.actualScore++;
            view.setBackgroundColor(0x33BB33);
            game();
        }
        else{
            if(vies == 0){
                return;
            }
            else{
                this.vies--;
                view.setBackgroundColor(0xBB3333);
            }
        }
    }
}