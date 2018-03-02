package Pieces;

import Game.Board;

public class Rook extends Piece {

    public Rook(String color, Board board) {
        super(color, board);
    }

    @Override
    public boolean isValidMove(int who, int sY, int sX, int eY, int eX) {
//        switch (who){
//            case 1:
//                if (sX )
//            case 2:
//        }
        return true;
    }

    @Override
    public Type getType() {
        return Type.Rook;
    }
}
