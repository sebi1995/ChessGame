package Pieces;

import Game.Board;

public class Bishop extends Piece {


    public Bishop(String color, Board board) {
        super(color, board);
    }

    @Override
    public boolean isValidMove(int startY, int startX, int endY, int endX) {
        //upright
        if (startY > endY && startX < endX) {
            while (--startY >= 0 && ++startX <= 7) {
                if (startY == endY && startX == endX) {
                    return true;
                }
            }
        }
        //downright
        if (startY < endY && startX < endX) {
            while (++startY <= 7 && ++startX <= 7) {
                if (startY == endY && startX == endX) {
                    return true;
                }
            }
        }
        //downleft
        if (startY < endY && startX > endX) {
            while (++startY <= 7 && --startX >= 0) {
                if (startY == endY && startX == endX) {
                    return true;
                }
            }
        }
        //upleft
        if (startY > endY && startX > endX) {
            while (--startY >= 0 && --startX >= 0) {
                if (startY == endY && startX == endX) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Type getType() {
        return Type.Bishop;
    }
}
