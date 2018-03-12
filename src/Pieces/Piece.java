package Pieces;

import Game.Board;

public abstract class Piece {

    private String color;
    protected Board board;

    Piece(String color, Board board) {
        this.color = color;
        this.board = board;
    }

    public abstract boolean isValidMove(int startY, int startX, int endY, int endX);

    public abstract Type getType();

    public String getColor() {
        return this.color;
    }
}
