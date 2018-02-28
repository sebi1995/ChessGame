package Pieces;

public class Rook extends Piece {

    public Rook(int x, int y, int color) {
        super(x, y, color);
    }

    @Override
    boolean isValidMove(int x, int y) {
        return false;
    }

    @Override
    public Type getType() {
        return Type.Rook;
    }
}
