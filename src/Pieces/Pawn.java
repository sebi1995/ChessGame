package Pieces;

public class Pawn extends Piece {

    public Pawn(int x, int y, String color) {
        super(x, y, color);
    }

    @Override
    public boolean isValidMove(int who, int sY, int sX, int eY, int eX, boolean pieceOnEndPos) {
        switch (who) {
            case 1:
                if (eY >= sY) return false;
                if (sX == eX) return sY - eY == 1;
                if (sY - eY == 1 && (sX - 1 == eX || sX + 1 == eX)) return true;
            case 2:
                if (eY <= sY) return false;
                if (sX == eX) return eY - sY == 1;
                if (eY - sY == 1 && (sX - 1 == eX || sX + 1 == eX)) return true;
        }
        return eX - sX == 1 || sX - eX == 1;
    }

    @Override
    public Type getType() {
        return Type.Pawn;
    }

    public boolean isPieceInEndPoint() {
        return false;
    }
}
