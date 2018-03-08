package Game;

import Pieces.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Board {

    private Piece[][] board;
    private final String BLUE = "\033[1;34m", RED = "\033[1;31m";
    private int turn = 1;
    private int playerOneKingY = -1, playerOneKingX = -1, playerTwoKingY = -1, playerTwoKingX = -1;
    private StringBuilder stringBuilder;
    private ArrayList<Piece> playerOneLostPieces, playerTwoLostPieces;

    public Board() {
        this.stringBuilder = new StringBuilder();

        initBoard(board = new Piece[8][8]);

        playerOneLostPieces = new ArrayList<>();
        playerTwoLostPieces = new ArrayList<>();
    }

    private void initBoard(Piece[][] board) {
        for (int i = 0; i < 8; ++i) {
            board[6][i] = new Pawn(BLUE, this);
        }
        board[7][0] = new Rook(BLUE, this);
        board[7][1] = new Knight(BLUE, this);
        board[7][2] = new Bishop(BLUE, this);
        board[7][3] = new King(BLUE, this);
        board[7][4] = new Queen(BLUE, this);
        board[7][5] = new Bishop(BLUE, this);
        board[7][6] = new Knight(BLUE, this);
        board[7][7] = new Rook(BLUE, this);

        board[0][0] = new Rook(RED, this);
        board[0][1] = new Knight(RED, this);
        board[0][2] = new Bishop(RED, this);
        board[0][3] = new Queen(RED, this);
        board[0][4] = new King(RED, this);
        board[0][5] = new Bishop(RED, this);
        board[0][6] = new Knight(RED, this);
        board[0][7] = new Rook(RED, this);
        for (int i = 0; i < 8; ++i) {
            board[1][i] = new Pawn(RED, this);
        }
    }

    public StringBuilder getBoard() {
        stringBuilder.delete(0, stringBuilder.length());
        int spaces = 8;

        stringBuilder.append("Y").append("\n");

        for (int i = 0; i < 8; ++i) {
            stringBuilder.append(i).append("\t");

            for (int j = 0; j < 8; ++j) {
                if (board[i][j] != null) {
                    stringBuilder.append(board[i][j].getColor()).append(board[i][j].getType()).append("\033[0m");

                    for (int k = board[i][j].getType().toString().length(); k < spaces; ++k) {
                        stringBuilder.append(" ");
                    }
                } else {
                    for (int k = 0; k < spaces; ++k) {
                        stringBuilder.append(" ");
                    }
                }
            }
            stringBuilder.append("\n");
        }

        stringBuilder.append("X >").append("\t");
        
        for (int i = 0; i < 8; ++i) {
            stringBuilder.append(i).append("       ");
        }
        return stringBuilder;
    }

    //sY = start Y sX = start X eY = end Y eX = end X
    public boolean makeMove(Player player, int sY, int sX, int eY, int eX) {
        if (sY >= 0 && sY <= 7 &&
                sX >= 0 && sX <= 7 &&
                eY >= 0 && eY <= 7 &&
                eX >= 0 && eX <= 7) {

            String pieceColor = board[sY][sX].getColor();

            if (board[sY][sX] != null) {
                if (isSameColorPieceOnEndPos(pieceColor, eY, eX)) {
                    return false;
                } else {
                    if (player.getColor().equals(pieceColor) && board[sY][sX].isValidMove(sY, sX, eY, eX)) {

                        if (board[eY][eX] != null) {
                            if (turn == 1) {
                                playerTwoLostPieces.add(board[eY][eX]);
                            } else {
                                playerOneLostPieces.add(board[eY][eX]);
                            }
                        }

                        if ((eY == 0 || eY == 7) && board[sY][sX] instanceof Pawn) {
                            if (turn == 1) {
                                board[eY][eX] = getWhichPieceToExchange(playerOneLostPieces);
                            } else {
                                board[eY][eX] = getWhichPieceToExchange(playerTwoLostPieces);
                            }
                        } else {
                            board[eY][eX] = board[sY][sX];
                        }

                        board[sY][sX] = null;

                        turn = (turn == 1) ? 2 : 1;

                        return true;
                    }
                }
            } else return false;
        }

        return false;
    }

    //asks the user what piece to exchange for a pawn if reached the other border
    private Piece getWhichPieceToExchange(ArrayList<Piece> pieceArray) {
        Scanner scanner = new Scanner(System.in);
        Piece retPiece;

        System.out.println("Player " + turn + ", what piece do you want to exchange your pawn for?");

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

    //this checks if the king is in check and cannot move, if returns false, game stops
    public boolean canGameContinue(String color) {
        for (int i = 0; i <= 7; ++i) {
            for (int j = 0; j <= 7; ++j) {
                if (board[i][j] instanceof King && board[i][j].getColor().equals(color)) {
                    if (turn == 1) {
                        playerOneKingY = i;
                        playerOneKingX = j;
                    } else {
                        playerTwoKingY = i;
                        playerTwoKingX = j;
                    }

                    boolean upleft = (i - 1 >= 0 && j - 1 >= 0 && isPositionCheckForKing(color, i - 1, j - 1));
                    boolean up = (i - 1 >= 0 && isPositionCheckForKing(color, i - 1, j));
                    boolean upright = (i - 1 >= 0 && j + 1 <= 7 && isPositionCheckForKing(color, i - 1, j + 1));
                    boolean left = (j - 1 >= 0 && isPositionCheckForKing(color, i, j - 1));
                    boolean right = (j + 1 <= 7 && isPositionCheckForKing(color, i, j + 1));
                    boolean downleft = (i + 1 <= 7 && j - 1 >= 0 && isPositionCheckForKing(color, i + 1, j - 1));
                    boolean down = (i + 1 <= 7 && isPositionCheckForKing(color, i + 1, j));
                    boolean downright = (i + 1 <= 7 && j + 1 <= 7 && isPositionCheckForKing(color, i + 1, j + 1));

                    if (right && down && downright || left && down && downleft || up && right && upright || left && up && upleft ||
                            upleft && up && upright && left && right && downleft && down && downright) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //this method checks if there is a piece where the player wants to move their piece and if it's the same colour
    private boolean isSameColorPieceOnEndPos(String color, int Y, int X) {
        return board[Y][X] != null && color.equals(board[Y][X].getColor());
    }

    //this method checks if there is a piece where the player wants to move their piece and if it's a diffrent colour
    public boolean isDifferentColorPieceOnEndPos(String color, int Y, int X) {
        return board[Y][X] != null && !color.equals(board[Y][X].getColor());
    }

    //this method checks if there is a piece in the direction the user wants to move their piece,
    // bishops and queens can move diagonal but they cannot jump over pieces
    public boolean isPieceNotOnPath(String path, int sY, int sX, int eY, int eX) {
        int y = sY;
        int x = sX;

        switch (path) {
            case "ul":
                while (--y > eY && --x > eX) {
                    if (board[y][x] != null) return false;
                }
                break;
            case "ur":
                while (--y > eY && ++x < eX) {
                    if (board[y][x] != null) return false;
                }
                break;
            case "dr":
                while (++y < eY && ++x < eX) {
                    if (board[y][x] != null) return false;
                }
                break;
            case "dl":
                while (++y < eY && --x > eX) {
                    if (board[y][x] != null) return false;
                }
                break;
            case "up":
                while (--y > eY) {
                    if (board[y][x] != null) return false;
                }
                break;
            case "down":
                while (++y < eY) {
                    if (board[y][x] != null) return false;
                }
                break;
            case "left":
                while (--x > eX) {
                    if (board[y][x] != null) return false;
                }
                break;
            case "right":
                while (++x < eX) {
                    if (board[y][x] != null) return false;
                }
                break;
        }
        return true;
    }

    //this checks if the position to which the user wants to move their king is a position which will mark them as checked
    //if it returns true then the king can be moved
    public boolean isPositionCheckForKing(String startPieceColor, int Y, int X) {
        int tempY = Y;
        int tempX = X;

        //check if the king can be taken by the other king in the end position
        if ((Y - 1 >= 0 && X - 1 >= 0 && board[Y - 1][X - 1] instanceof King && isDifferentColorPieceOnEndPos(startPieceColor, Y - 1, X - 1)) ||
                (Y - 1 >= 0 && board[Y - 1][X] instanceof King && isDifferentColorPieceOnEndPos(startPieceColor, Y - 1, X)) ||
                (Y - 1 >= 0 && X + 1 <= 7 && board[Y - 1][X + 1] instanceof King && isDifferentColorPieceOnEndPos(startPieceColor, Y - 1, X + 1)) ||
                (X - 1 >= 0 && board[Y][X - 1] instanceof King && isDifferentColorPieceOnEndPos(startPieceColor, Y, X - 1)) ||
                (X + 1 <= 7 && board[Y][X + 1] instanceof King && isDifferentColorPieceOnEndPos(startPieceColor, Y, X + 1)) ||
                (Y + 1 <= 7 && X - 1 >= 0 && board[Y + 1][X - 1] instanceof King && isDifferentColorPieceOnEndPos(startPieceColor, Y + 1, X - 1)) ||
                (Y + 1 <= 7 && board[Y + 1][X] instanceof King && isDifferentColorPieceOnEndPos(startPieceColor, Y + 1, X)) ||
                (Y + 1 <= 7 && X + 1 <= 7 && board[Y + 1][X + 1] instanceof King && isDifferentColorPieceOnEndPos(startPieceColor, Y + 1, X + 1))
                ) {
            return true;
        }

        //check if pawn can take king if moved to where player wants to move, if king in danger, don't allow user to make move
        if (turn == 1 && (Y - 1 >= 0 && X - 1 >= 0 && board[Y - 1][X - 1] instanceof Pawn && isDifferentColorPieceOnEndPos(startPieceColor, Y - 1, X - 1) ||
                Y - 1 >= 0 && X + 1 <= 7 && board[Y - 1][X + 1] instanceof Pawn && isDifferentColorPieceOnEndPos(startPieceColor, Y - 1, X + 1))) {
            return true;
        } else if (turn == 2 && (Y + 1 <= 7 && X - 1 >= 0 && board[Y + 1][X - 1] instanceof Pawn && isDifferentColorPieceOnEndPos(startPieceColor, Y + 1, X - 1) ||
                Y + 1 <= 7 && X + 1 <= 7 && board[Y + 1][X + 1] instanceof Pawn && isDifferentColorPieceOnEndPos(startPieceColor, Y + 1, X + 1))) {
            return true;
        }

        //check for horse
        if (Y + 2 <= 7 && X - 1 >= 0 && board[Y + 2][X - 1] instanceof Knight && isDifferentColorPieceOnEndPos(startPieceColor, Y + 2, X - 1) ||
                Y + 2 <= 7 && X + 1 <= 7 && board[Y + 2][X + 1] instanceof Knight && isDifferentColorPieceOnEndPos(startPieceColor, Y + 2, X + 1) ||
                Y + 1 <= 7 && X + 2 <= 7 && board[Y + 1][X + 2] instanceof Knight && isDifferentColorPieceOnEndPos(startPieceColor, Y + 1, X + 2) ||
                Y + 1 <= 7 && X - 2 >= 0 && board[Y + 1][X - 2] instanceof Knight && isDifferentColorPieceOnEndPos(startPieceColor, Y + 1, X - 2) ||
                Y - 2 >= 0 && X - 1 >= 0 && board[Y - 2][X - 1] instanceof Knight && isDifferentColorPieceOnEndPos(startPieceColor, Y - 2, X - 1) ||
                Y - 2 >= 0 && X + 1 <= 7 && board[Y - 2][X + 1] instanceof Knight && isDifferentColorPieceOnEndPos(startPieceColor, Y - 2, X + 1) ||
                Y - 1 >= 0 && X + 2 <= 7 && board[Y - 1][X + 2] instanceof Knight && isDifferentColorPieceOnEndPos(startPieceColor, Y - 1, X + 2) ||
                Y - 1 >= 0 && X - 2 >= 0 && board[Y - 1][X - 2] instanceof Knight && isDifferentColorPieceOnEndPos(startPieceColor, Y - 1, X - 2)) {
            return true;
        }

        //check for queen, rook or bishop
        //up left check
        while (tempY >= 0 && tempX >= 0) {
            if (board[tempY][tempX] != null) {
                if (board[tempY][tempX] instanceof Queen || board[tempY][tempX] instanceof Bishop) {
                    if (isDifferentColorPieceOnEndPos(startPieceColor, tempY, tempX)) {
                        return true;
                    }
                } else
                    break;
            }
            --tempY;
            --tempX;
        }

        tempY = Y;
        tempX = X;

        //up right check
        while (tempY >= 0 && tempX <= 7) {
            if (board[tempY][tempX] != null) {
                if (board[tempY][tempX] instanceof Queen || board[tempY][tempX] instanceof Bishop) {
                    if (isDifferentColorPieceOnEndPos(startPieceColor, tempY, tempX)) {
                        return true;
                    }
                } else
                    break;
            }
            --tempY;
            ++tempX;
        }

        tempY = Y;
        tempX = X;

        //down left check
        while (tempY <= 7 && tempX >= 0) {
            if (board[tempY][tempX] != null) {
                if (board[tempY][tempX] instanceof Queen || board[tempY][tempX] instanceof Bishop) {
                    if (isDifferentColorPieceOnEndPos(startPieceColor, tempY, tempX)) {
                        return true;
                    }
                } else
                    break;
            }
            ++tempY;
            --tempX;
        }

        tempY = Y;
        tempX = X;

        //down right check
        while (tempY <= 7 && tempX <= 7) {
            if (board[tempY][tempX] != null) {
                if (board[tempY][tempX] instanceof Queen || board[tempY][tempX] instanceof Bishop) {
                    if (isDifferentColorPieceOnEndPos(startPieceColor, tempY, tempX)) {
                        return true;
                    }
                } else
                    break;
            }
            ++tempY;
            ++tempX;
        }

        tempY = Y;
        tempX = X;

        //up
        while (tempY >= 0) {
            if (board[tempY][tempX] != null) {
                if (board[tempY][tempX] instanceof Queen || board[tempY][tempX] instanceof Rook) {
                    if (isDifferentColorPieceOnEndPos(startPieceColor, tempY, tempX)) {
                        return true;
                    }
                } else
                    break;
            }
            --tempY;
        }

        tempY = Y;
        tempX = X;

        //down
        while (tempY <= 7) {
            if (board[tempY][tempX] != null) {
                if (board[tempY][tempX] instanceof Queen || board[tempY][tempX] instanceof Rook) {
                    if (isDifferentColorPieceOnEndPos(startPieceColor, tempY, tempX)) {
                        return true;
                    }
                } else
                    break;
            }
            ++tempY;
        }

        tempY = Y;
        tempX = X;

        //left
        while (tempX >= 0) {
            if (board[tempY][tempX] != null) {
                if (board[tempY][tempX] instanceof Queen || board[tempY][tempX] instanceof Rook) {
                    if (isDifferentColorPieceOnEndPos(startPieceColor, tempY, tempX)) {
                        return true;
                    }
                } else
                    break;
            }
            --tempX;
        }

        tempY = Y;
        tempX = X;

        //right
        while (tempX <= 7) {
            if (board[tempY][tempX] != null) {
                if (board[tempY][tempX] instanceof Queen || board[tempY][tempX] instanceof Rook) {
                    if (isDifferentColorPieceOnEndPos(startPieceColor, tempY, tempX)) {
                        return true;
                    }
                } else
                    break;
            }
            ++tempX;
        }

        return false;
    }

    public int getTurn() {
        return turn;
    }

    public int getKingXPos() {
        if (turn == 1) {
            return playerOneKingX;
        } else {
            return playerTwoKingX;
        }
    }

    public int getKingYPos() {
        if (turn == 1) {
            return playerOneKingY;
        } else {
            return playerTwoKingY;
        }
    }
}
