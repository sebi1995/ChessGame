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

        stringBuilder.append("\t");
        for (int i = 0; i < 8; ++i) {
            stringBuilder.append(i).append("       ");
        }
        return stringBuilder;
    }

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

    public boolean canGameContinue(String color) {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (board[i][j] instanceof King && board[i][j].getColor().equals(color)) {
                    if ((i - 1 >= 0 && j - 1 >= 0 && !isMoveNotPuttingKingInCheck(color, i - 1, j - 1)) &&
                            (i - 1 >= 0 && !isMoveNotPuttingKingInCheck(color, i - 1, j)) &&
                            (i - 1 >= 0 && j + 1 <= 7 && !isMoveNotPuttingKingInCheck(color, i - 1, j + 1)) &&
                            (j - 1 >= 0 && !isMoveNotPuttingKingInCheck(color, i, j - 1)) &&
                            (j + 1 <= 7 && !isMoveNotPuttingKingInCheck(color, i, j + 1)) &&
                            (i + 1 <= 7 && j - 1 >= 0 && !isMoveNotPuttingKingInCheck(color, i + 1, j - 1)) &&
                            (i + 1 <= 7 && !isMoveNotPuttingKingInCheck(color, i + 1, j)) &&
                            (i + 1 <= 7 && j + 1 <= 7 && !isMoveNotPuttingKingInCheck(color, i + 1, j + 1))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isSameColorPieceOnEndPos(String color, int eY, int eX) {
        return board[eY][eX] != null && color.equals(board[eY][eX].getColor());
    }

    public boolean isDifferentColorPieceOnEndPos(String color, int eY, int eX) {
        return board[eY][eX] != null && !color.equals(board[eY][eX].getColor());
    }

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

    public boolean isMoveNotPuttingKingInCheck(String startPieceColor, int eY, int eX) {
        int tempY = eY;
        int tempX = eX;

        //check if pawn can take king if moved to where player wants to move, if king in danger, don't allow user to make move
        if (turn == 1 && board[eY - 1][eX - 1] instanceof Pawn && isDifferentColorPieceOnEndPos(startPieceColor, eY - 1, eX - 1) ||
                turn == 2 && board[eY - 1][eX + 1] instanceof Pawn && isDifferentColorPieceOnEndPos(startPieceColor, eY - 1, eX + 1)) {
            return false;
        } else if (turn == 2 && board[eY + 1][eX - 1] instanceof Pawn && isDifferentColorPieceOnEndPos(startPieceColor, eY + 1, eX - 1) ||
                turn == 2 && board[eY + 1][eX + 1] instanceof Pawn && isDifferentColorPieceOnEndPos(startPieceColor, eY + 1, eX + 1)) {
            return false;
        }

        //check for horse
        if (eY + 2 <= 7 && eX - 1 >= 0 && board[eY + 2][eX - 1] instanceof Knight && isDifferentColorPieceOnEndPos(startPieceColor, eY + 2, eX - 1) ||
                eY + 2 <= 7 && eX + 1 <= 7 && board[eY + 2][eX + 1] instanceof Knight && isDifferentColorPieceOnEndPos(startPieceColor, eY + 2, eX + 1) ||
                eY + 1 <= 7 && eX + 2 <= 7 && board[eY + 1][eX + 2] instanceof Knight && isDifferentColorPieceOnEndPos(startPieceColor, eY + 1, eX + 2) ||
                eY + 1 <= 7 && eX - 2 >= 0 && board[eY + 1][eX - 2] instanceof Knight && isDifferentColorPieceOnEndPos(startPieceColor, eY + 1, eX - 2) ||
                eY - 2 >= 0 && eX - 1 >= 0 && board[eY - 2][eX - 1] instanceof Knight && isDifferentColorPieceOnEndPos(startPieceColor, eY - 2, eX - 1) ||
                eY - 2 >= 0 && eX + 1 <= 7 && board[eY - 2][eX + 1] instanceof Knight && isDifferentColorPieceOnEndPos(startPieceColor, eY - 2, eX + 1) ||
                eY - 1 >= 0 && eX + 2 <= 7 && board[eY - 1][eX + 2] instanceof Knight && isDifferentColorPieceOnEndPos(startPieceColor, eY - 1, eX + 2) ||
                eY - 1 >= 0 && eX - 2 >= 0 && board[eY - 1][eX - 2] instanceof Knight && isDifferentColorPieceOnEndPos(startPieceColor, eY - 1, eX - 2)) {
            return false;
        }

        //check for queen, rook or bishop
        //up left check
        while (tempY >= 0 && tempX >= 0) {
            if (board[tempY][tempX] != null) {
                if (board[tempY][tempX] instanceof Queen || board[tempY][tempX] instanceof Bishop) {
                    if (isDifferentColorPieceOnEndPos(startPieceColor, tempY, tempX)) {
                        return false;
                    }
                }
                break;
            }
            --tempY;
            --tempX;
        }
        tempY = eY;
        tempX = eX;
        //up right check
        while (tempY >= 0 && tempX <= 7) {
            if (board[tempY][tempX] != null) {
                if (board[tempY][tempX] instanceof Queen || board[tempY][tempX] instanceof Bishop) {
                    if (isDifferentColorPieceOnEndPos(startPieceColor, tempY, tempX)) {
                        return false;
                    }
                }
                break;
            }
            --tempY;
            ++tempX;
        }
        tempY = eY;
        tempX = eX;
        //down left check
        while (tempY <= 7 && tempX >= 0) {
            if (board[tempY][tempX] != null) {
                if (board[tempY][tempX] instanceof Queen || board[tempY][tempX] instanceof Bishop) {
                    if (isDifferentColorPieceOnEndPos(startPieceColor, tempY, tempX)) {
                        return false;
                    }
                }
                break;
            }
            ++tempY;
            --tempX;
        }
        tempY = eY;
        tempX = eX;
        //down right check
        while (tempY <= 7 && tempX <= 7) {
            if (board[tempY][tempX] != null) {
                if (board[tempY][tempX] instanceof Queen || board[tempY][tempX] instanceof Bishop) {
                    if (isDifferentColorPieceOnEndPos(startPieceColor, tempY, tempX)) {
                        return false;
                    }
                }
                break;
            }
            ++tempY;
            ++tempX;
        }
        tempY = eY;
        tempX = eX;
        //up
        while (tempY >= 0) {
            if (board[tempY][tempX] != null) {
                if (board[tempY][tempX] instanceof Queen || board[tempY][tempX] instanceof Rook) {
                    if (isDifferentColorPieceOnEndPos(startPieceColor, tempY, tempX)) {
                        return false;
                    }
                }
                break;
            }
            --tempY;
        }
        tempY = eY;
        tempX = eX;
        //down
        while (tempY <= 7) {
            if (board[tempY][tempX] != null) {
                if (board[tempY][tempX] instanceof Queen || board[tempY][tempX] instanceof Rook) {
                    if (isDifferentColorPieceOnEndPos(startPieceColor, tempY, tempX)) {
                        return false;
                    }
                }
                break;
            }
            ++tempY;
        }
        tempY = eY;
        tempX = eX;
        //left
        while (tempX >= 0) {
            if (board[tempY][tempX] != null) {
                if (board[tempY][tempX] instanceof Queen || board[tempY][tempX] instanceof Rook) {
                    if (isDifferentColorPieceOnEndPos(startPieceColor, tempY, tempX)) {
                        return false;
                    }
                }
                break;
            }
            --tempX;
        }
        tempY = eY;
        tempX = eX;
        //right
        while (tempX <= 7) {
            if (board[tempY][tempX] != null) {
                if (board[tempY][tempX] instanceof Queen || board[tempY][tempX] instanceof Rook) {
                    if (isDifferentColorPieceOnEndPos(startPieceColor, tempY, tempX)) {
                        return false;
                    }
                }
                break;
            }
            ++tempX;
        }

        if (turn == 1){
            playerOneKingY = eY;
            playerOneKingX = eX;

        } else {
            playerTwoKingY = eY;
            playerTwoKingX = eX;
        }
        return true;
    }

    public boolean isKingInCheck(Player player) {
        String startPieceColor = player.getColor();
        int sY;
        int sX;

        if (turn == 1){
            sY = playerOneKingY;
            sX = playerOneKingX;
        } else {
            sY = playerTwoKingY;
            sX = playerTwoKingX;
        }

        int tempY = sY;
        int tempX = sX;

        //check if pawn can take king if moved to where player wants to move, if king in danger, don't allow user to make move
        if (turn == 1 && board[sY - 1][sX - 1] instanceof Pawn && isDifferentColorPieceOnEndPos(startPieceColor, sY - 1, sX - 1) ||
                turn == 2 && board[sY - 1][sX + 1] instanceof Pawn && isDifferentColorPieceOnEndPos(startPieceColor, sY - 1, sX + 1)) {
            return false;
        } else if (turn == 2 && board[sY + 1][sX - 1] instanceof Pawn && isDifferentColorPieceOnEndPos(startPieceColor, sY + 1, sX - 1) ||
                turn == 2 && board[sY + 1][sX + 1] instanceof Pawn && isDifferentColorPieceOnEndPos(startPieceColor, sY + 1, sX + 1)) {
            return false;
        }

        //check for horse
        if (sY + 2 <= 7 && sX - 1 >= 0 && board[sY + 2][sX - 1] instanceof Knight && isDifferentColorPieceOnEndPos(startPieceColor, sY + 2, sX - 1) ||
                sY + 2 <= 7 && sX + 1 <= 7 && board[sY + 2][sX + 1] instanceof Knight && isDifferentColorPieceOnEndPos(startPieceColor, sY + 2, sX + 1) ||
                sY + 1 <= 7 && sX + 2 <= 7 && board[sY + 1][sX + 2] instanceof Knight && isDifferentColorPieceOnEndPos(startPieceColor, sY + 1, sX + 2) ||
                sY + 1 <= 7 && sX - 2 >= 0 && board[sY + 1][sX - 2] instanceof Knight && isDifferentColorPieceOnEndPos(startPieceColor, sY + 1, sX - 2) ||
                sY - 2 >= 0 && sX - 1 >= 0 && board[sY - 2][sX - 1] instanceof Knight && isDifferentColorPieceOnEndPos(startPieceColor, sY - 2, sX - 1) ||
                sY - 2 >= 0 && sX + 1 <= 7 && board[sY - 2][sX + 1] instanceof Knight && isDifferentColorPieceOnEndPos(startPieceColor, sY - 2, sX + 1) ||
                sY - 1 >= 0 && sX + 2 <= 7 && board[sY - 1][sX + 2] instanceof Knight && isDifferentColorPieceOnEndPos(startPieceColor, sY - 1, sX + 2) ||
                sY - 1 >= 0 && sX - 2 >= 0 && board[sY - 1][sX - 2] instanceof Knight && isDifferentColorPieceOnEndPos(startPieceColor, sY - 1, sX - 2)) {
            return false;
        }

        //check for queen, rook or bishop
        //up left check
        while (tempY >= 0 && tempX >= 0) {
            if (board[tempY][tempX] != null) {
                if (board[tempY][tempX] instanceof Queen || board[tempY][tempX] instanceof Bishop) {
                    if (isDifferentColorPieceOnEndPos(startPieceColor, tempY, tempX)) {
                        return false;
                    }
                }
                break;
            }
            --tempY;
            --tempX;
        }
        tempY = sY;
        tempX = sX;
        //up right check
        while (tempY >= 0 && tempX <= 7) {
            if (board[tempY][tempX] != null) {
                if (board[tempY][tempX] instanceof Queen || board[tempY][tempX] instanceof Bishop) {
                    if (isDifferentColorPieceOnEndPos(startPieceColor, tempY, tempX)) {
                        return false;
                    }
                }
                break;
            }
            --tempY;
            ++tempX;
        }
        tempY = sY;
        tempX = sX;
        //down left check
        while (tempY <= 7 && tempX >= 0) {
            if (board[tempY][tempX] != null) {
                if (board[tempY][tempX] instanceof Queen || board[tempY][tempX] instanceof Bishop) {
                    if (isDifferentColorPieceOnEndPos(startPieceColor, tempY, tempX)) {
                        return false;
                    }
                }
                break;
            }
            ++tempY;
            --tempX;
        }
        tempY = sY;
        tempX = sX;
        //down right check
        while (tempY <= 7 && tempX <= 7) {
            if (board[tempY][tempX] != null) {
                if (board[tempY][tempX] instanceof Queen || board[tempY][tempX] instanceof Bishop) {
                    if (isDifferentColorPieceOnEndPos(startPieceColor, tempY, tempX)) {
                        return false;
                    }
                }
                break;
            }
            ++tempY;
            ++tempX;
        }
        tempY = sY;
        tempX = sX;
        //up
        while (tempY >= 0) {
            if (board[tempY][tempX] != null) {
                if (board[tempY][tempX] instanceof Queen || board[tempY][tempX] instanceof Rook) {
                    if (isDifferentColorPieceOnEndPos(startPieceColor, tempY, tempX)) {
                        return false;
                    }
                }
                break;
            }
            --tempY;
        }
        tempY = sY;
        tempX = sX;
        //down
        while (tempY <= 7) {
            if (board[tempY][tempX] != null) {
                if (board[tempY][tempX] instanceof Queen || board[tempY][tempX] instanceof Rook) {
                    if (isDifferentColorPieceOnEndPos(startPieceColor, tempY, tempX)) {
                        return false;
                    }
                }
                break;
            }
            ++tempY;
        }
        tempY = sY;
        tempX = sX;
        //left
        while (tempX >= 0) {
            if (board[tempY][tempX] != null) {
                if (board[tempY][tempX] instanceof Queen || board[tempY][tempX] instanceof Rook) {
                    if (isDifferentColorPieceOnEndPos(startPieceColor, tempY, tempX)) {
                        return false;
                    }
                }
                break;
            }
            --tempX;
        }
        tempY = sY;
        tempX = sX;
        //right
        while (tempX <= 7) {
            if (board[tempY][tempX] != null) {
                if (board[tempY][tempX] instanceof Queen || board[tempY][tempX] instanceof Rook) {
                    if (isDifferentColorPieceOnEndPos(startPieceColor, tempY, tempX)) {
                        return false;
                    }
                }
                break;
            }
            ++tempX;
        }
        return true;
    }

    public int getTurn() {
        return turn;
    }
}
