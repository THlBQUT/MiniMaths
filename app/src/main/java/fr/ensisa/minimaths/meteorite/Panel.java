package fr.ensisa.minimaths.meteorite;

import android.graphics.Bitmap;

import java.util.concurrent.ThreadLocalRandom;

public class Panel {
    private int n1;
    private int n2;
    private boolean isSetN1;
    private boolean isSetN2;
    private int life;
    private int res;
    private int operator=1;
    private int score=0;
    private final int SUM=1;
    private final int PRODUCT=2;

    private static final int SUM_OPERATOR=1;
    private static final int PRODUCT_OPERATOR=2;

    public Panel()
    {
        life=3;
        generateEquation();
    }

    public int getScore(){
        return this.score;
    }
    public void collect(int value)
    {
        if(!isSetN1)
        {
            this.n1=value;
            isSetN1=true;
        }
        else
        {
            this.n2 = value;
            isSetN2=true;
            checkSum();
        }
    }

    public int getRes()
    {
        return this.res;
    }

    public void checkSum()
    {
        if(operator==this.SUM)
        {
            if(this.isSetN1 && this.isSetN2)
            {
                this.isSetN2 = false;
                if(this.n1 + this.n2 == this.res)
                {
                    score++;
                    generateEquation();
                    this.isSetN1 = false;

                }
                else {
                    life--;
                }
            }
        }
    }
    public void generateEquation()
    {
            this.res = (int) (Math.random() * 100) + 1;
    }

    public String getEquation()
    {
            if(isSetN1 && !isSetN2)
                return String.valueOf(this.n1)+" + _ = "+String.valueOf(this.res);
            else
                return  "_ + _ = "+String.valueOf(this.res);
    }

    public int getLife() {
        return this.life;
    }

    public int getN1() {
        return this.n1;
    }
    public int getN2() {
        return this.n2;
    }

    public boolean getIsSetN1(){
        return this.isSetN1;
    }

    public boolean getIsSetN2(){
        return this.isSetN2;
    }

    public int getOperator() {
        return this.operator;
    }
}
