package fr.ensisa.minimaths;


import java.util.ArrayList;

public class Equation {

    private enum Niveau {FACILE, MOYEN, DIFFICILE}

    ;
    private ArrayList<String> operandes = new ArrayList<String>() {{
        add("+");
        add("-");
        add("x");
        add("/");
    }};
    private int resultat;
    private String equation;

    public Equation(Niveau niveau) {
        generator(niveau);
    }

    private void generator(Niveau niveau) {
        int operation = (int) (Math.random() * 4) + 1;
        switch (niveau) {
            case FACILE:
                switch (operation) {
                    case 0:
                        generatorAdd(50);
                        break;
                    case 1:
                        generatorSub(50);
                        break;
                    case 2:
                        generatorMult(15);
                        break;
                    case 3:
                        generatorDivi(20);
                        break;
                }
            case MOYEN:
                switch (operation) {
                    case 0:
                        generatorAdd(350);
                        break;
                    case 1:
                        generatorSub(250);
                        break;
                    case 2:
                        generatorMult(25);
                        break;
                    case 3:
                        generatorDivi(20);
                        break;
                }
                break;
            case DIFFICILE:
                switch (operation) {
                    case 0:
                        generatorAdd(1300);
                        break;
                    case 1:
                        generatorSub(1000);
                        break;
                    case 2:
                        generatorMult(50);
                        break;
                    case 3:
                        generatorDivi(40);
                        break;
                }
                break;
            default:
                break;
        }
    }

    private void generatorAdd(int num){
        int rand1;
        int rand2;
        rand1 = (int)(Math.random() * num) + 1;
        equation = rand1 + "";
        rand2 = (int)(Math.random() * num) + 1;
        equation += operandes.get(0);
        this.resultat = rand1 + rand2;
        this.equation += rand2;
    }

    private void generatorSub(int num) {
        int rand1;
        int rand2;
        rand1 = (int)(Math.random() * num) + 1;
        equation = rand1 + "";
        rand2 = (int)(Math.random() * num) + 1;
        equation += operandes.get(1);
        while(rand1 < rand2) {
            rand2 = (int) (Math.random() * num) + 1;
        }
        this.resultat = rand1 - rand2;
        this.equation += rand2;
    }

    private void generatorMult(int num){
        int rand1;
        int rand2;
        rand1 = (int)(Math.random() * 15) + 1;
        equation = rand1 + "";
        rand2 = (int)(Math.random() * 15) + 1;
        equation += operandes.get(2);
        this.resultat = rand1 * rand2;
        this.equation += rand2;
    }

    private void generatorDivi(int num) {
        int rand1;
        int rand2;
        rand2 = (int)(Math.random() * 20 + 1);
        rand1 = rand2 * (int)(Math.random() * 20 + 1);
        equation = rand1 + "";
        equation += operandes.get(3);
        this.resultat = rand1 / rand2;
        this.equation += rand2;
    }
}
