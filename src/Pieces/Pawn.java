package Pieces;

import Game.Board;

public class Pawn extends Piece {

    public Pawn(String color, Board board) {
        super(color, board);
    }

    @Override
    public boolean isValidMove(int sY, int sX, int eY, int eX) {
        switch (board.getTurn()) {
            case 1:
                if (eY >= sY) return false;
                if (sY == 6 && (sX >= 0 && sX <= 7) && (sY - eY == 2) && (sX == eX) && board.isPieceNotOnPath("up", sY, sX, eY, eX))
                    return true;
                if (sX == eX && sY - eY == 1 && !board.isDifferentColorPieceOnEndPos(this.getColor(), eY, eX)) {
                    return true;
                }
                if (sY - eY == 1 && (sX - 1 == eX || sX + 1 == eX) && board.isDifferentColorPieceOnEndPos(this.getColor().substring(4, 6), eY, eX)) {
                    return true;
                }
            case 2:
                if (eY <= sY) return false;
                if (sY == 1 && (sX >= 0 && sX <= 7) && (sY - eY == -2) && (sX == eX) && board.isPieceNotOnPath("down", sY, sX, eY, eX))
                    return true;
                if (sX == eX && sY - eY == -1 && !board.isDifferentColorPieceOnEndPos(this.getColor(), eY, eX)) {
                    return true;
                }
                if (sY - eY == -1 && (sX - 1 == eX || sX + 1 == eX) && board.isDifferentColorPieceOnEndPos(this.getColor().substring(4, 6), eY, eX))
                    return true;
        }
        //this is unreachable
        return false;
    }

    @Override
    public Type getType() {
        return Type.Pawn;
    }
}
