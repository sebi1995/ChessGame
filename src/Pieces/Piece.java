package Pieces;

import Game.Board;

public abstract class Piece {

    //x y used for Table[y][x]
    //board allows the pieces to have contact with the board itself
    protected Board board;
    //color if for the colour of the piece
    private String color;

    Piece(String color, Board board){
        this.color = color;
        this.board = board;
    }

    //isValidMove checks if the move the user wants to make on the piece is a valid move that the piece can make
    public abstract boolean isValidMove(int sY, int sX, int eY, int eX);

    //get what type of piece this is
    public abstract Type getType();

    public String getColor(){
        return this.color;
    };
}
