package Game;

import Pieces.*;

public class Board {

    private Piece[][] board;
    private static final String BLUE = "\033[1;34m";
    private static final String RED = "\033[1;31m";

    private Piece piece;

    private StringBuilder stringBuilder;

    public Board() {
        stringBuilder = new StringBuilder();

        initBoard(board = new Piece[8][8]);

        board[4][2] = new Pawn("\033[1;31m", this);
        board[3][3] = new Pawn("\033[1;31m", this);
        board[2][4] = new Pawn("\033[1;31m", this);
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
            if (board[sY][sX] == null) {
                return false;
            } else {
                if (isSameColorPieceOnEndPos(sY, sX, eY, eX)) {
                    return false;
                } else {
                    boolean canContinue;

                    canContinue = board[sY][sX].isValidMove(player.getWho(), sY, sX, eY, eX);

                    if (canContinue) {
                        board[eY][eX] = board[sY][sX];
                        board[sY][sX] = null;
                        return true;
                    } else return false;
                }
            }

        } else {
            return false;
        }

    }


    private boolean isSameColorPieceOnEndPos(int sY, int sX, int eY, int eX) {
        return board[eY][eX] != null && Integer.parseInt(board[sY][sX].getColor().substring(4, 6)) ==
                Integer.parseInt(board[eY][eX].getColor().substring(4, 6));
    }

    public boolean isDifferentColorPieceOnEndPos(int sY, int sX, int eY, int eX) {
        return board[eY][eX] != null && Integer.parseInt(board[sY][sX].getColor().substring(4, 6)) !=
                Integer.parseInt(board[eY][eX].getColor().substring(4, 6));
    }

    public boolean isPieceOnPath(String path, int sY, int sX, int eY, int eX) {
        int y = sY;
        int x = sX;

        switch (path) {
            case "ul":
                while (y > eY && x > eX) {
                    if (board[--y][--x] != null) return true;
                }
            case "ur":
                while (y > eY && x < eX) {
                    if (board[--y][++x] != null) return true;
                }
            case "dr":
                while (y < eY && x < eX) {
                    if (board[++y][++x] != null) return true;
                }
            case "dl":
                while (y < eY && x > eX) {
                    if (board[++y][--x] != null) return true;
                }
            default:
                return false;
        }
    }
}
