package Pieces;

public abstract class Piece {

    //x y used for Table[y][x]
    private int x, y;
    //color if for the colour of the piece
    private String color;

    Piece(int x, int y, String color){
        this.x = x;
        this.y = y;
        this.color = color;
    }

    //isValidMove checks if the move the user wants to make on the piece is a valid move
    //if it is, then query the board to make the move, if it isn't reply back to the user with an error.
    public abstract boolean isValidMove(int who, int startY, int startX, int endY, int endX, boolean pieceIsOnSpotYX);

    boolean isValidMove(int startY, int startX) {
        return false;
    }

    //get what type of piece this is
    public abstract Type getType();

    public String getColor(){
        return this.color;
    };
}
