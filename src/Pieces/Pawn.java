package Pieces;

public class Pawn extends Piece {

    public Pawn(int x, int y, String color) {
        super(x, y, color);
    }

    @Override
    public boolean isValidMove(int who, int startY, int startX, int endY, int endX) {
        int minusSEYY = startY - endY;
        System.out.println(minusSEYY);
        int minusSEXX = startX - endX;
        System.out.println(minusSEXX);
//System.out.println(board.makeMove(player1,6, 0, 5, 1));
        if (who == 1){
            if (minusSEYY == 1 && minusSEXX == 1 &&
                        minusSEXX > 0 && minusSEYY > 0 &&
                        minusSEXX < 8 && minusSEYY < 8){
                return true;
            }
        }
        return false;
    }

    @Override
    public Type getType() {
        return Type.Pawn;
    }
}
