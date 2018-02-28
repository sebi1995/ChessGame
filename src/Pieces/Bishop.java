package Pieces;

public class Bishop extends Piece {

    public Bishop(int x, int y, String color) {
        super(x, y, color);
    }

    @Override
    public boolean isValidMove(int who, int startY, int startX, int endY, int endX) {
        return false;
    }

    @Override
    public Type getType() {
        return Type.Bishop;
    }
}
