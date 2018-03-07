package Pieces;

import Game.Board;

public class Bishop extends Piece {

    public Bishop(String color, Board board) {
        super(color, board);
    }

    @Override
    public boolean isValidMove(int sY, int sX, int eY, int eX) {
        int y = sY;
        int x = sX;
        String passThisString = "";

        if (y < eY && x > eX) {
            while (y <= 7 && x >= 0) {
                if (++y == eY && --x == eX) {
                    passThisString = "dl";
                }
            }
        } else if (y > eY && x < eX) {
            while (--y >= 0 && ++x <= 7) {
                if (y == eY && x == eX) {
                    passThisString = "ur";
                    break;
                }
            }
        } else if (y > eY && x > eX) {
            while (--y >= 0 && --x >= 0) {
                if (y == eY && x == eX) {
                    passThisString = "ul";
                    break;
                }
            }
        } else if (y < eY && x < eX) {
            while (++y <= 7 && ++x <= 7) {
                if (y == eY && x == eX) {
                    passThisString = "dr";
                    break;
                }
            }
        } else return false;

        return !passThisString.equals("") && board.isPieceNotOnPath(passThisString, sY, sX, eY, eX);
    }

    @Override
    public Type getType() {
        return Type.Bishop;
    }
}
