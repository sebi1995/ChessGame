package Pieces;

public class Knight extends Piece {

    public Knight(int x, int y, String color) {
        super(x, y, color);
    }

    @Override
    public boolean isValidMove(int who, int sY, int sX, int eY, int eX, boolean pieceOnEndPos) {
        return false;
    }

    @Override
    public Type getType() {
        return Type.Knight;
    }
}
