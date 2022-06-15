package fr.ensisa.minimaths;

public class Room {

    private String name;
    private String difficulty;

    public Room(String name, String difficulty){
        this.difficulty = difficulty;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public Room getRoom(){return this;}
}
