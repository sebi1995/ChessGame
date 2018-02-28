package Pieces;

public class Queen extends Piece {

    public Queen(int x, int y, int color) {
        super(x, y, color);
    }

    @Override
    boolean isValidMove(int x, int y) {
        return false;
    }

    @Override
    public Type getType() {
        return Type.Queen;
    }
}
