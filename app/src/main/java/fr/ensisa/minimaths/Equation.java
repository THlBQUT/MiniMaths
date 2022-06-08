package fr.ensisa.minimaths;


import java.util.ArrayList;

public class Equation {

    private ArrayList<String> operandes = new ArrayList<String>() {{
        add("+"); add("-"); add("x"); add("/");
    }};
    private int resultat;
    private String equation;

    public Equation(String string) {
        generator(string);
    }

    public int getResultat() {return resultat;}

    public String getEquation() {return equation;}

    private void generator(String difficulty) {
        int operation = (int) (Math.random() * 4) + 1;
        while(operation > 4){
            operation = (int) (Math.random() * 4) + 1;
        }
        switch (difficulty) {
            case "FACILE":
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
                    default:
                        generator(difficulty);
                        break;
                }
                break;
            case "MOYEN":
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
                    default:
                        break;
                }
                break;
            case "DIFFICILE":
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
                    default:
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
        while(rand1 <= rand2) {
            rand2 = (int) (Math.random() * num) + 1;
        }
        this.resultat = rand1 - rand2;
        this.equation += rand2;
    }

    private void generatorMult(int num){
        int rand1;
        int rand2;
        rand1 = (int)(Math.random() * num) + 1;
        equation = rand1 + "";
        rand2 = (int)(Math.random() * num) + 1;
        equation += operandes.get(2);
        this.resultat = rand1 * rand2;
        this.equation += rand2;
    }

    private void generatorDivi(int num) {
        int rand1;
        int rand2;
        rand2 = (int)(Math.random() * num + 1);
        rand1 = rand2 * (int)(Math.random() * 6 + 1);
        equation = rand1 + "";
        equation += operandes.get(3);
        this.resultat = rand1 / rand2;
        this.equation += rand2;
    }
}
