package Pieces;

import Game.Board;

public class King extends Piece {

    private boolean kingIsInStartPosition = true;

    public King(String color, Board board) {
        super(color, board);
    }


    @Override
    public boolean isValidMove(int startY, int startX, int endY, int endX) {
        if (startY - endY == 1 && startX == endX ||             //up
                startY - endY == 1 && startX - endX == -1 ||    //upright
                startY == endY && startX - endX == -1 ||        //right
                startY - endY == -1 && startX - endX == -1 ||   //downright
                startY - endY == -1 && startX == endX ||        //down
                startY - endY == -1 && startX - endX == 1 ||    //downleft
                startY == endY && startX - endX == 1 ||         //left
                startY - endY == 1 && startX - endX == 1) {     //upleft

            if (!board.isPieceOnPosition(endY, endX) && board.canKingMoveThere(getColor(), endY, endX) || board.isDifferentColorPieceOnPosition(getColor(), endY, endX) && board.canKingMoveThere(getColor(), endY, endX)) {

                board.playerOneKingY = endY;
                board.playerOneKingX = endX;

                if (kingIsInStartPosition) {
                    kingIsInStartPosition = false;
                }

                return true;
            }
        }

        //castle
        if (kingIsInStartPosition) {
            if (startY == 7 && startX == 4 && startY == endY) {
                if (startX - endX == 2) {
                    return board.canKingCastle(getColor(), "left");
                } else if (startX - endX == -2) {
                    return board.canKingCastle(getColor(), "right");
                }
            }
        }

        return false;
    }

    @Override
    public Type getType() {
        return Type.King;
    }
}
