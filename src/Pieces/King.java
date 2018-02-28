package Pieces;

public class King extends Piece {

    public King(int x, int y, String color) {
        super(x, y, color);
    }

    @Override
    public boolean isValidMove(int who, int startY, int startX, int endY, int endX, boolean pieceIsOnSpotYX) {
        return false;
    }

    @Override
    public Type getType() {
        return Type.King;
    }
}
