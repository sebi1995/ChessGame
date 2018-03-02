package Pieces;

import Game.Board;

public class Knight extends Piece {

    public Knight(String color, Board board) {
        super(color, board);
    }

    @Override
    public boolean isValidMove(int who, int sY, int sX, int eY, int eX) {
        return false;
    }

    @Override
    public Type getType() {
        return Type.Knight;
    }
}
