import Pieces.*;

import java.util.ArrayList;

public class Player {

    private String name, color;
    private int who;
    public ArrayList<Piece> myPieces;

    Player(String name, int who) {
        this.name = name;
        this.color = (who == 1) ? "\033[1;34m" : "\033[1;31m";
        this.who = who;

        setMyPieces(myPieces = new ArrayList<>(16), this.color);
    }

    private void setMyPieces(ArrayList<Piece> myPieces, String color) {
        if (who == 1) {
            for (int i = 0; i < 8; ++i) {
                myPieces.add(new Pawn   (0, i, color));
            }
            myPieces.add(new Rook   (0, 0, color));
            myPieces.add(new Knight (0, 1, color));
            myPieces.add(new Bishop (0, 2, color));
            myPieces.add(new King   (0, 4, color));
            myPieces.add(new Queen  (0, 3, color));
            myPieces.add(new Bishop (0, 5, color));
            myPieces.add(new Knight (0, 6, color));
            myPieces.add(new Rook   (0, 7, color));
        } else {
            myPieces.add(new Rook   (0, 0, color));
            myPieces.add(new Knight (0, 1, color));
            myPieces.add(new Bishop (0, 2, color));
            myPieces.add(new Queen  (0, 3, color));
            myPieces.add(new King   (0, 4, color));
            myPieces.add(new Bishop (0, 5, color));
            myPieces.add(new Knight (0, 6, color));
            myPieces.add(new Rook   (0, 7, color));
            for (int i = 0; i < 8; ++i) {
                myPieces.add(new Pawn   (1, i, color));
            }
        }
    }

    public int getWho() {
        return who;
    }
}
