package Pieces;

import Game.Board;

public class Pawn extends Piece {

    public Pawn(String color, Board board) {
        super(color, board);
    }

    @Override
    public boolean isValidMove(int startY, int startX, int endY, int endX) {

        if (startY == 6 && startY - endY == 2 && startX == endX) {
            return !board.isPieceOnPosition(endY, endX);
        }

        if (startY - endY == 1 && startX == endX) {
            return !board.isPieceOnPosition(endY, endX);
        }

        if (startY - endY == 1 && startX - endX == 1 || startY - endY == 1 && startX - endX == -1) {
            return board.isPieceOnPosition(endY, endX) && board.isDifferentColorPieceOnPosition(getColor(), endY, endX);
        }


        return false;
    }

    @Override
    public Type getType() {
        return Type.Pawn;
    }
}
