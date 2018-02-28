package Pieces;

public abstract class Piece {

    private int x, y;
    private String color;

    Piece(int x, int y, int color){
        this.x = x;
        this.y = y;
        this.color = (color == 0) ? "b" : "w";
    }

    abstract boolean isValidMove(int x, int y);

    public abstract Type getType();
}
