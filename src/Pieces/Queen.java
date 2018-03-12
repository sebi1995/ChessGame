package Pieces;

import Game.Board;

public class Queen extends Piece {

    public Queen(String color, Board board) {
        super(color, board);
    }

    @Override
    public boolean isValidMove(int startY, int startX, int endY, int endX) {

        String passThisString = "";

        //up            --y x
        if (startY > endY && startX == endX) {
            while (--startY >= 0) {
                if (startY == endY) {
                    return board.isPathClear("up", startY, startX, endY, endX);
                }
            }
        }
        //upright       --y ++x
        if (startY > endY && startX < endX) {
            while (--startY >= 0 && ++startX <= 7) {
                if (startY == endY && startX == endX) {
                    return board.isPathClear("upright", startY, startX, endY, endX);
                }
            }
        }
        //right         y ++x
        if (startY == endY && startX < endX) {
            while (++startX <= 7) {
                if (startX == endX) {
                    return board.isPathClear("right", startY, startX, endY, endX);
                }
            }
        }
        //downright     ++y ++x
        if (startY < endY && startX < endX) {
            while (++startY <= 7 && ++startX <= 7) {
                if (startY == endY && startX == endX) {
                    return board.isPathClear("downright", startY, startX, endY, endX);
                }
            }
        }
        //down          ++y x
        if (startY < endY && startX == endX) {
            while (++startY <= 7) {
                if (startY == endY) {
                    return board.isPathClear("down", startY, startX, endY, endX);
                }
            }
        }
        //downleft      ++y --x
        if (startY < endY && startX > endX) {
            while (++startY <= 7 && --startX >= 0) {
                if (startY == endY && startX == endX) {
                    return board.isPathClear("downleft", startY, startX, endY, endX);
                }
            }
        }
        //left          --y x
        if (startY > endY && startX == endX) {
            while (--startY >= 0) {
                if (startY == endY) {
                    return board.isPathClear("left", startY, startX, endY, endX);
                }
            }
        }
        //upleft        --y --x
        if (startY > endY && startX > endX) {
            while (--startY >= 0 && --startX >= 0) {
                if (startY == endY && startX == endX) {
                    return board.isPathClear("upleft", startY, startX, endY, endX);
                }
            }
        }

        return false;
    }

    @Override
    public Type getType() {
        return Type.Queen;
    }
}
