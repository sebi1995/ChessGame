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

    //isValidMove checks if the move the user wants to make on the piece is a valid move that the piece can make
    public abstract boolean isValidMove(int who, int sY, int sX, int eY, int eX, boolean pieceOnEndPos);

    //get what type of piece this is
    public abstract Type getType();

    public String getColor(){
        return this.color;
    };
}
