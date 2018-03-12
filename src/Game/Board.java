package Game;

import Pieces.*;
import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Board {

    private Piece[][] board;
    private ArrayList<Piece> playerOneLostPieces, playerTwoLostPieces;
    private HashMap<Integer, String> leftKeys;
    public int playerOneKingY, playerOneKingX, playerTwoKingY, playerTwoKingX;

    private StringBuilder boardString;
    private int turn = 1;

    public Board() {
        leftKeys = new HashMap<>();
        leftKeys.put(0, "H");
        leftKeys.put(1, "G");
        leftKeys.put(2, "F");
        leftKeys.put(3, "E");
        leftKeys.put(4, "D");
        leftKeys.put(5, "C");
        leftKeys.put(6, "B");
        leftKeys.put(7, "A");

        playerOneLostPieces = new ArrayList<>();
        playerTwoLostPieces = new ArrayList<>();

        initBoard();

        boardString = new StringBuilder();

        board[7][1] = null;
        board[7][2] = null;
        board[7][3] = null;
    }

    private void initBoard() {
        board = new Piece[8][8];

        String BLUE = "\033[1;34m";
        board[7][0] = new Rook(BLUE, this);
        board[7][1] = new Knight(BLUE, this);
        board[7][2] = new Bishop(BLUE, this);
        board[7][3] = new Queen(BLUE, this);
        board[7][4] = new King(BLUE, this);
        playerOneKingY = playerTwoKingY = 7;
        playerOneKingX = playerTwoKingX = 4;
        board[7][5] = new Bishop(BLUE, this);
        board[7][6] = new Knight(BLUE, this);
        board[7][7] = new Rook(BLUE, this);

        for (int i = 0; i <= 7; ++i) {
            board[6][i] = new Pawn(BLUE, this);
        }

        String RED = "\033[1;31m";
        for (int i = 0; i <= 7; ++i) {
            board[1][i] = new Pawn(RED, this);
        }

        board[0][0] = new Rook(RED, this);
        board[0][1] = new Knight(RED, this);
        board[0][2] = new Bishop(RED, this);
        board[0][3] = new King(RED, this);
        board[0][4] = new Queen(RED, this);
        board[0][5] = new Bishop(RED, this);
        board[0][6] = new Knight(RED, this);
        board[0][7] = new Rook(RED, this);
    }

    public StringBuilder getBoard() {

        String RESET = "\033[0m";
        boardString.delete(0, boardString.length());


        for (int i = 0; i <= 7; ++i) {
            boardString.append(leftKeys.get(i)).append("\t");
            for (int j = 0; j <= 7; ++j) {
                if (board[i][j] != null) {
                    String type = board[i][j].getType().toString();
                    int size = type.length();
                    boardString.append(" ");
                    boardString.append(board[i][j].getColor()).append(type).append(RESET);

                    for (int k = size; k < 8; ++k) {
                        boardString.append(" ");
                    }
                } else {
                    for (int k = 0; k <= 8; ++k) {
                        boardString.append(" ");
                    }
                }
                boardString.append("|");
            }
            boardString.append("\n");
            for (int k = 0; k < 7; ++k) {
                boardString.append("____________");
            }
            boardString.append("\n");
        }

        boardString.append(" \t");
        for (int i = 1; i <= 8; ++i) {
            boardString.append(i);
            for (int k = 0; k < 9; ++k) {
                boardString.append(" ");
            }
        }
        return boardString;
    }

    public boolean canTakeTurn(Player player, String startY, int startX, String endY, int endX) {

        --startX;
        --endX;

        if (leftKeys.containsValue(startY.toUpperCase()) && (startX >= 0 && startX <= 7) &&
                leftKeys.containsValue(endY.toUpperCase()) && (endX >= 0 && endX <= 7)) {

            int sY = getNumberFromLetter(startY);
            int eY = getNumberFromLetter(endY);

            if (board[sY][startX] != null && isSameColorPieceOnPosition(player.getColor(), sY, startX)) {

                Piece startPiece = board[sY][startX];

                if (startPiece.isValidMove(sY, startX, eY, endX)) {

                    //if taking a piece, add it to the lost pieces list
                    if (board[eY][endX] != null && isDifferentColorPieceOnPosition(player.getColor(), eY, endX)) {
                        switch (turn) {
                            case 1:
                                playerTwoLostPieces.add(board[eY][endX]);
                                break;
                            case 2:
                                playerOneLostPieces.add(board[eY][endX]);
                                break;
                        }
                    }

                    //if pawn reaches end, switch pawn for piece
                    if (eY == 7 && board[sY][startX] instanceof Pawn) {
                        switch (turn) {
                            case 1:
                                board[eY][endX] = getWhichPieceToExchange(player.getName(), playerOneLostPieces);
                                break;
                            case 2:
                                board[eY][endX] = getWhichPieceToExchange(player.getName(), playerTwoLostPieces);
                                break;
                        }
                    } else {
                        //castle
                        if (sY == 7 && eY == 7 && startX == 4 && endX == 2 && board[sY][startX] instanceof King) {
                            board[7][2] = board[7][4];
                            board[7][4] = null;
                            board[7][3] = board[7][0];
                            board[7][0] = null;
                            turn = (turn == 1) ? 2 : 1;
                            return true;
                        } else {
                            board[eY][endX] = board[sY][startX];
                        }
                    }

                    board[sY][startX] = null;
                    turn = (turn == 1) ? 2 : 1;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isDifferentColorPieceOnPosition(String startPieceColor, int Y, int X) {
        return board[Y][X] != null && !startPieceColor.equals(board[Y][X].getColor());
    }

    private boolean isSameColorPieceOnPosition(String startPieceColor, int Y, int X) {
        return board[Y][X] != null && startPieceColor.equals(board[Y][X].getColor());
    }

    public boolean isPieceOnPosition(int Y, int X) {
        return board[Y][X] != null;
    }

    public boolean isPathClear(String path, int startY, int startX, int endY, int endX) {

        switch (path) {
            case "up":
                while (--startY > endY) {
                    if (board[startY][startX] != null) {
                        return false;
                    }
                }
                break;
            case "upright":
                while (--startY > endY && ++startX < endX) {
                    if (board[startY][startX] != null) {
                        return false;
                    }
                }
                break;
            case "right":
                while (++startX < endX) {
                    if (board[startY][startX] != null) {
                        return false;
                    }
                }
                break;
            case "downright":
                while (++startY < endY && ++startX < endX) {
                    if (board[startY][startX] != null) {
                        return false;
                    }
                }
                break;
            case "down":
                while (++startY < endY) {
                    if (board[startY][startX] != null) {
                        return false;
                    }
                }
                break;
            case "downleft":
                while (++startY < endY && --startX > endX) {
                    if (board[startY][startX] != null) {
                        return false;
                    }
                }
                break;
            case "left":
                while (--startX > endX) {
                    if (board[startY][startX] != null) {
                        return false;
                    }
                }
                break;
            case "upleft":
                while (--startY > endY && --startX > endX) {
                    if (board[startY][startX] != null) {
                        return false;
                    }
                }
                break;
        }
        return true;
    }

    public boolean canKingMoveThere(String color, int endY, int endX) {
        //check for king
        if (endY - 1 >= 0 && board[endY - 1][endX] instanceof King && isDifferentColorPieceOnPosition(color, endY - 1, endX) ||                                          //up
                endY - 1 >= 0 && endX + 1 <= 7 && board[endY - 1][endX + 1] instanceof King && isDifferentColorPieceOnPosition(color, endY - 1, endX + 1) ||        //upright
                endX + 1 <= 7 && board[endY][endX + 1] instanceof King && isDifferentColorPieceOnPosition(color, endY, endX + 1) ||                                      //right
                endY + 1 <= 7 && endX + 1 <= 7 && board[endY + 1][endX + 1] instanceof King && isDifferentColorPieceOnPosition(color, endY + 1, endX + 1) ||        //downright
                endY + 1 <= 7 && board[endY + 1][endX] instanceof King && isDifferentColorPieceOnPosition(color, endY + 1, endX) ||                                      //down
                endY + 1 <= 7 && endX - 1 >= 0 && board[endY + 1][endX - 1] instanceof King && isDifferentColorPieceOnPosition(color, endY + 1, endX - 1) ||        //downleft
                endX - 1 >= 0 && board[endY][endX - 1] instanceof King && isDifferentColorPieceOnPosition(color, endY, endX - 1) ||                                      //left
                endY - 1 >= 0 && endX - 1 >= 0 && board[endY - 1][endX - 1] instanceof King && isDifferentColorPieceOnPosition(color, endY - 1, endX - 1)) {         //upleft
            return false;
        }

        //check for pawn
        if (endY - 1 >= 0 && endX - 1 >= 0 && board[endY - 1][endX - 1] instanceof Pawn && !board[endY - 1][endX - 1].getColor().equals(color) ||
                endY - 1 >= 0 && endX <= 7 && board[endY - 1][endX + 1] instanceof Pawn && !board[endY - 1][endX + 1].getColor().equals(color)) {//upleft and upright
            return false;
        }

        //check for knight
        if (endY - 2 >= 0 && endX + 1 <= 7 &&
                board[endY - 2][endX + 1] instanceof Knight && isDifferentColorPieceOnPosition(color, endY - 2, endX + 1) ||
                endY - 1 >= 0 && endX + 2 <= 7 &&
                        board[endY - 1][endX + 2] instanceof Knight && isDifferentColorPieceOnPosition(color, endY - 1, endX + 2) ||
                endY + 1 <= 7 && endX + 2 <= 7 &&
                        board[endY + 1][endX + 2] instanceof Knight && isDifferentColorPieceOnPosition(color, endY + 1, endX + 2) ||
                endY + 2 <= 7 && endX + 1 <= 7 &&
                        board[endY + 2][endX + 1] instanceof Knight && isDifferentColorPieceOnPosition(color, endY + 2, endX + 1) ||
                endY + 2 <= 7 && endX - 1 >= 0 &&
                        board[endY + 2][endX - 1] instanceof Knight && isDifferentColorPieceOnPosition(color, endY + 2, endX - 1) ||
                endY + 1 <= 7 && endX - 2 >= 0 &&
                        board[endY + 1][endX - 2] instanceof Knight && isDifferentColorPieceOnPosition(color, endY + 1, endX - 2) ||
                endY - 1 >= 0 && endX - 1 >= 0 &&
                        board[endY - 1][endX - 1] instanceof Knight && isDifferentColorPieceOnPosition(color, endY - 1, endX - 1) ||
                endY - 2 >= 0 && endX - 1 >= 0 &&
                        board[endY - 2][endX - 1] instanceof Knight && isDifferentColorPieceOnPosition(color, endY - 2, endX - 1)) {
            return false;
        }

        //check for queen and bishop vertical
        //upright
        int Y = endY;
        int X = endX;
        while (--Y >= 0 && ++X <= 7) {
            if (board[Y][X] != null) {
                if ((board[Y][X] instanceof Queen || board[Y][X] instanceof Bishop) && isDifferentColorPieceOnPosition(color, Y, X)) {
                    return false;
                } else {
                    break;
                }
            }
        }
        //downright
        Y = endY;
        X = endX;
        while (++Y <= 7 && ++X <= 7) {
            if (board[Y][X] != null) {
                if ((board[Y][X] instanceof Queen || board[Y][X] instanceof Bishop) && isDifferentColorPieceOnPosition(color, Y, X)) {
                    return false;
                } else {
                    break;
                }
            }
        }
        //downleft
        Y = endY;
        X = endX;
        while (++Y <= 7 && --X >= 0) {
            if (board[Y][X] != null) {
                if ((board[Y][X] instanceof Queen || board[Y][X] instanceof Bishop) && isDifferentColorPieceOnPosition(color, Y, X)) {
                    return false;
                } else {
                    break;
                }
            }
        }
        //upleft
        Y = endY;
        X = endX;
        while (--Y >= 0 && --X >= 0) {
            if (board[Y][X] != null) {
                if ((board[Y][X] instanceof Queen || board[Y][X] instanceof Bishop) && isDifferentColorPieceOnPosition(color, Y, X)) {
                    return false;
                } else {
                    break;
                }
            }
        }

        //checks for queen or rook
        //up
        Y = endY;
        X = endX;
        while (--Y >= 0) {
            if (board[Y][X] != null) {
                if ((board[Y][X] instanceof Queen || board[Y][X] instanceof Rook) && isDifferentColorPieceOnPosition(color, Y, X)) {
                    return false;
                } else {
                    break;
                }
            }
        }
        //right
        Y = endY;
        X = endX;
        while (++X <= 7) {
            if (board[Y][X] != null) {
                if ((board[Y][X] instanceof Queen || board[Y][X] instanceof Rook) && isDifferentColorPieceOnPosition(color, Y, X)) {
                    return false;
                } else {
                    break;
                }
            }
        }
        //down
        Y = endY;
        X = endX;
        while (++Y <= 7) {
            if (board[Y][X] != null) {
                if ((board[Y][X] instanceof Queen || board[Y][X] instanceof Rook) && isDifferentColorPieceOnPosition(color, Y, X)) {
                    return false;
                } else {
                    break;
                }
            }
        }
        //left
        Y = endY;
        X = endX;
        while (--X >= 0) {
            if (board[Y][X] != null) {
                if ((board[Y][X] instanceof Queen || board[Y][X] instanceof Rook) && isDifferentColorPieceOnPosition(color, Y, X)) {
                    return false;
                } else {
                    break;
                }
            }
        }


        return true;

    }

    public boolean canGameContinue(Player player) {

        int Y = 0;
        int X = 0;

        switch (turn) {
            case 1:
                Y = playerOneKingY;
                X = playerOneKingX;
                break;
            case 2:
                Y = playerTwoKingY;
                X = playerTwoKingX;
                break;
        }

        String color = player.getColor();

        boolean up = Y - 1 >= 0 && canKingMoveThere(color, Y - 1, X);
        boolean upright = Y - 1 >= 0 && X + 1 <= 7 && canKingMoveThere(color, Y - 1, X + 1);
        boolean right = X + 1 <= 7 && canKingMoveThere(color, Y, X + 1);
        boolean downright = Y + 1 <= 7 && X + 1 <= 7 && canKingMoveThere(color, Y + 1, X + 1);
        boolean down = Y + 1 <= 7 && canKingMoveThere(color, Y + 1, X);
        boolean downleft = Y + 1 <= 7 && X - 1 >= 0 && canKingMoveThere(color, Y + 1, X - 1);
        boolean left = X - 1 >= 0 && canKingMoveThere(color, Y, X - 1);
        boolean upleft = Y - 1 >= 0 && X - 1 >= 0 && canKingMoveThere(color, Y - 1, X - 1);

        System.out.println(Y);
        System.out.println(X);
        return up || upright || right || downright || down || downleft || left || upleft;
    }

    public boolean canKingCastle(String color, String direction) {
        /*
        The king and the chosen rook are on the player's first rank.[3]
        Neither the king nor the chosen rook has previously moved.
        There are no pieces between the king and the chosen rook.<++++++++++++++
        The king is not currently in check. <++++++++
        The king does not pass through a square that is attacked by an enemy piece.[4]+++++++++++++++
        The king does not end up in check. (True of any legal move.)+++++++++++++++

        */

        switch (direction) {
            case "left":
                if (board[7][3] == null && board[7][2] == null) {
                    if (canKingMoveThere(color, 7, 4) &&
                            canKingMoveThere(color, 7, 3) &&
                            canKingMoveThere(color, 7, 2) &&
                            board[7][0] instanceof Rook) {

                        return board[7][0].isValidMove(7, 0, 7, 3);
                    }
                }
            case "right":
                if (board[7][5] == null && board[7][6] == null) {
                    if (canKingMoveThere(color, 7, 4) &&
                            canKingMoveThere(color, 7, 5) &&
                            canKingMoveThere(color, 7, 6) &&
                            board[7][7] instanceof Rook) {

                        return board[7][7].isValidMove(7, 7, 7, 5);
                    }
                }
                break;
        }

        return false;
    }

    public void flipTheBoard(Player player) {
        Piece[][] second = new Piece[8][8];

        for (int i = 0; i <= 7; ++i) {
            for (int j = 0; j <= 7; ++j) {
                if (board[i][j] != null) {
                    if (board[i][j].getColor().equals(player.getColor())) {
                        second[i][j] = board[i][j];
                        board[i][j] = null;
                    }
                }
            }
        }

        board = flipBoardToPlaceHolder(board);
        second = flipBoardToPlaceHolder(second);

        for (int i = 0; i <= 7; ++i) {
            for (int j = 0; j <= 7; ++j) {
                if (second[i][j] != null) {
                    board[i][j] = second[i][j];
                }
            }
        }
    }

    private Piece[][] flipBoardToPlaceHolder(Piece[][] board) {
        Piece[][] placeHolder = new Piece[8][8];
        for (int i = 0; i <= 7; ++i) {
            for (int j = 0; j <= 7; ++j) {
                if (board[i][j] != null) {
                    int Y = getSwitchedNumber(i);
                    int X = getSwitchedNumber(j);

                    placeHolder[Y][X] = board[i][j];
                }
            }
        }
        return placeHolder;
    }

    private Piece getWhichPieceToExchange(String name, ArrayList<Piece> pieceArray) {
        Scanner scanner = new Scanner(System.in);
        Piece retPiece;

        System.out.println(name + ", what piece do you want to exchange your pawn for?");

        int i = 1;
        for (Piece piece : pieceArray) {
            System.out.println(i + ". " + piece.getType().toString());
        }

        System.out.println("Enter the number for the piece, for the piece you want back.");
        System.out.print("Piece: ");
        int pos = scanner.nextInt();

        retPiece = pieceArray.get(--pos);
        pieceArray.remove(pos);

        return retPiece;
    }

    private int getNumberFromLetter(String letter) {
        switch (letter.toUpperCase()) {
            case "A":
                return 7;
            case "B":
                return 6;
            case "C":
                return 5;
            case "D":
                return 4;
            case "E":
                return 3;
            case "F":
                return 2;
            case "G":
                return 1;
            case "H":
                return 0;
        }
        return -1;
    }

    private int getSwitchedNumber(int number) {
        switch (number) {
            case 0:
                return 7;
            case 1:
                return 6;
            case 2:
                return 5;
            case 3:
                return 4;
            case 4:
                return 3;
            case 5:
                return 2;
            case 6:
                return 1;
            case 7:
                return 0;
        }
        return 123;
    }

    public int getKingY() {
        return (turn == 1) ? playerOneKingY : playerTwoKingY;
    }

    public int getKingX() {
        return (turn == 1) ? playerOneKingX : playerTwoKingX;
    }
}
