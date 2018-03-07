package Pieces;

import Game.Board;

public class King extends Piece {

    public King(String color, Board board) {
        super(color, board);
    }

    @Override
    public boolean isValidMove(int sY, int sX, int eY, int eX) {
        boolean validMove = false;

        if (sY - eY == 0 && sX - eX == 1) {
            validMove = true;
        } else if (sY - eY == 0 && sX - eX == -1) {
            validMove = true;
        } else if (sY - eY == 1 && sX - eX == 1) {
            validMove = true;
        } else if (sY - eY == 1 && sX - eX == 0) {
            validMove = true;
        } else if (sY - eY == 1 && sX - eX == -1) {
            validMove = true;
        } else if (sY - eY == -1 && sX - eX == 1) {
            validMove = true;
        } else if (sY - eY == -1 && sX - eX == 0) {
            validMove = true;
        } else if (sY - eY == -1 && sX - eX == -1) {
            validMove = true;
        }

        //this returns if move is valid or not
        return validMove && board.isKingNotInDanger(this.getColor(), eY, eX);
    }

    @Override
    public Type getType() {
        return Type.King;
    }
}
