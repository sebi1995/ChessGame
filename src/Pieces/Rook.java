package Pieces;

import Game.Board;

public class Rook extends Piece {

    private boolean rookIsInStartPosition = true;

    public Rook(String color, Board board) {
        super(color, board);
    }


    @Override
    public boolean isValidMove(int startY, int startX, int endY, int endX) {
        int Y = startY;
        int X = startX;

        if (rookIsInStartPosition) {
            if (Y == 7 && (X < endX || X > endX)) {
                if (startX - endX == -3) {
                    if (board.isPathClear("right", startY, startX, endY, endX)) {
                        rookIsInStartPosition = false;
                        return true;
                    }
                }
                if (startX - endX == 2) {
                    if (board.isPathClear("left", startY, startX, endY, endX)) {
                        rookIsInStartPosition = false;
                        return true;
                    }
                }
            }
        }

        //up
        if (startY > endY && startX == endX) {
            while (--Y >= 0) {
                if (Y == endY) {
                    if (board.isPathClear("up", startY, startX, endY, endX)) {
                        rookIsInStartPosition = false;
                        return true;
                    }
                }
            }
        }
        //right
        if (startY == endY && startX < endX) {
            while (++X <= 7) {
                if (X == endX) {
                    if (board.isPathClear("right", startY, startX, endY, endX)) {
                        rookIsInStartPosition = false;
                        return true;
                    }
                }
            }
        }
        //down
        if (startY < endY && startX == endX) {
            while (++Y <= 7) {
                if (Y == endY) {
                    if (board.isPathClear("down", startY, startX, endY, endX)) {
                        rookIsInStartPosition = false;
                        return true;
                    }
                }
            }
        }
        //left
        if (startX > endX && startY == endY) {
            while (--X >= 0) {
                if (X == endX) {
                    if (board.isPathClear("left", startY, startX, endY, endX)) {
                        rookIsInStartPosition = false;
                        return true;
                    }
                }
            }
        }
        //else
        return false;
    }

    @Override
    public Type getType() {
        return Type.Rook;
    }
}
