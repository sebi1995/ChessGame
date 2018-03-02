package Game;

import Pieces.Piece;

import java.util.ArrayList;

public class Player {

    private String name;
    private int who;
    public ArrayList<Piece> myPieces;

    public Player(String name, int who) {
        this.name = name;
        this.who = who;
    }

    public int getWho() {
        return who;
    }
}
