package Game;

import Pieces.Piece;

import java.util.ArrayList;

public class Player {

    private String name;
    private int who;
    private String color;
    public ArrayList<Piece> myPieces;

    public Player(String name, int who, String color) {
        this.name = name;
        this.who = who;
        this.color = color;
    }

    public int getWho() {
        return who;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
}
