package Pieces;

import Game.Board;

public class Rook extends Piece {

    public Rook(String color, Board board) {
        super(color, board);
    }

    @Override
    public boolean isValidMove(int sY, int sX, int eY, int eX) {
        int y = sY, x = sX;
        String passThisString = "";

        if (y > eY && x == eX) {
            while (--y >= 0) {
                if (y == eY) {
                    passThisString = "up";
                    break;
                }
            }
        } else if (y < eY && x == eX) {
            while (++y <= 7) {
                if (y == eY) {
                    passThisString = "down";
                    break;
                }
            }
        } else if (x > eX && y == eY) {
            while (--x >= 0) {
                if (x == eX) {
                    passThisString = "left";
                    break;
                }
            }
        } else if (x < eX && y == eY) {
            while (++x <= 7) {
                if (x == eX) {
                    passThisString = "right";
                    break;
                }
            }
        } else return false;

        return !passThisString.equals("") && board.isPieceNotOnPath(passThisString, sY, sX, eY, eX);
    }

    @Override
    public Type getType() {
        return Type.Rook;
    }
}








