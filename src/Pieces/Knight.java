package Pieces;

import Game.Board;

public class Knight extends Piece {

    public Knight(String color, Board board) {
        super(color, board);
    }

    @Override
    public boolean isValidMove(int startY, int startX, int endY, int endX) {

        if (startY - 2 == endY && startX + 1 == endX ||     //up right
                startY - 1 == endY && startX + 2 == endX || //up right right
                startY + 1 == endY && startX + 2 == endX || //down right right
                startY + 2 == endY && startX + 1 == endX || //down right
                startY + 2 == endY && startX - 1 == endX || //down left
                startY + 1 == endY && startX - 2 == endX || //down left left
                startY - 1 == endY && startX - 2 == endX || //up left left
                startY - 2 == endY && startX - 1 == endX) { //up left
            return !board.isPieceOnPosition(endY, endX) || board.isDifferentColorPieceOnPosition(getColor(), endY, endX);
        }

        return false;
    }

    @Override
    public Type getType() {
        return Type.Knight;
    }
}
