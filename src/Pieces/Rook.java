package Pieces;

public class Rook extends Piece {

    public Rook(int x, int y, String color) {
        super(x, y, color);
    }

    @Override
    public boolean isValidMove(int who, int sY, int sX, int eY, int eX, boolean pieceOnEndPos) {
//        switch (who){
//            case 1:
//                if (sX )
//            case 2:
//        }
        return true;
    }

    @Override
    public Type getType() {
        return Type.Rook;
    }
}
