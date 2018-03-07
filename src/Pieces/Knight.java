package Pieces;

import Game.Board;

public class Knight extends Piece {

    public Knight(String color, Board board) {
        super(color, board);
    }

    @Override
    public boolean isValidMove(int sY, int sX, int eY, int eX) {
        return sY - eY == 1 && sX - eX == 2 ||
                sY - eY == -1 && sX - eX == 2 ||
                sY - eY == 1 && sX - eX == -2 ||
                sY - eY == -1 && sX - eX == -2 ||
                sY - eY == 2 && sX - eX == 1 ||
                sY - eY == -2 && sX - eX == 1 ||
                sY - eY == 2 && sX - eX == -1 ||
                sY - eY == -2 && sX - eX == -1;
    }

    @Override
    public Type getType() {
        return Type.Knight;
    }
}
