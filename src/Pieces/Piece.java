package Pieces;

public abstract class Piece {

    //x y used for Table[y][x]
    private int x, y;
    //color if for the colour of the piece
    private String color;

    Piece(int x, int y, int color){
        this.x = x;
        this.y = y;
        this.color = (color == 0) ? "b" : "w";
    }

    //isValidMove checks if the move the user wants to make on the piece is a valid move
    //if it is, then query the board to make the move, if it isn't reply back to the user with an error.
    abstract boolean isValidMove(int x, int y);

    //get what type of piece this is
    public abstract Type getType();
}
