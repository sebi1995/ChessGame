package Pieces;

public class Bishop extends Piece {

    public Bishop(int x, int y, int color) {
        super(x, y, color);
    }

    @Override
    boolean isValidMove(int x, int y) {
        return false;
    }

    @Override
    public Type getType() {
        return Type.Bishop;
    }
}
