package Pieces;

public class Queen extends Piece {

    public Queen(int x, int y, String color) {
        super(x, y, color);
    }

    @Override
    public boolean isValidMove(int who, int sY, int sX, int eY, int eX, boolean pieceOnEndPos) {
        return false;
    }

    @Override
    public Type getType() {
        return Type.Queen;
    }
}
