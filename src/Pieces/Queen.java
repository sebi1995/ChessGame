package Pieces;

public class Queen extends Piece {

    public Queen(int x, int y, String color) {
        super(x, y, color);
    }

    @Override
    public boolean isValidMove(int who, int startY, int startX, int endY, int endX, boolean pieceIsOnSpotYX) {
        return false;
    }

    @Override
    public Type getType() {
        return Type.Queen;
    }
}
