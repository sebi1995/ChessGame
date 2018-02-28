package Pieces;

public class Pawn extends Piece {

    public Pawn(int x, int y, String color) {
        super(x, y, color);
    }

    @Override
    public boolean isValidMove(int who, int startY, int startX, int endY, int endX, boolean pieceIsOnSpotYX) {
        if (who == 1) {
            if (endY >= startY) return false;
            if (startX == endX) {
                return !pieceIsOnSpotYX && startY - endY == 1;
            }
        } else {
            if (endY <= startY) return false;
            if (startX == endX) {
                return !pieceIsOnSpotYX && endY - startY == 1;
            }
        }
        return pieceIsOnSpotYX && (endX - startX == 1 || startX - endX == 1);
    }

    @Override
    public Type getType() {
        return Type.Pawn;
    }
}
