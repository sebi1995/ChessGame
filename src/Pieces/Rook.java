package Pieces;

public class Rook extends Piece {

    public Rook(int x, int y, String color) {
        super(x, y, color);
    }

    @Override
    public boolean isValidMove(int who, int startY, int startX, int endY, int endX) {
        return false;
    }

    @Override
    public Type getType() {
        return Type.Rook;
    }
}
