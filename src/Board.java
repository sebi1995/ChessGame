import Pieces.*;

public class Board {

    private Piece[][] board;
    private StringBuilder stringBuilder;

    Board(Player player1, Player player2) {
        stringBuilder = new StringBuilder();

        initBoard(player1, player2, board = new Piece[8][8]);
//        board[5][1] = new Pawn(5, 1, "\033[1;31m");
        board[4][2] = new Pawn(4, 2, "\033[1;31m");
        board[3][3] = new Pawn(3, 3, "\033[1;31m");
        board[2][4] = new Pawn(2, 4, "\033[1;31m");
    }

    private void initBoard(Player player1, Player player2, Piece[][] board) {
        int p1 = 0;
        for (int i = 6; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                board[i][j] = player1.myPieces.get(p1++);
            }
        }
        p1 = 0;
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 8; ++j) {
                board[i][j] = player2.myPieces.get(p1++);
            }
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
        if (board[sY][sX] == null) return false;

        if (isSameColorPieceOnEndPos(sY, sX, eY, eX)) {
            return false;
        } else {
            boolean canContinue;

            canContinue = board[sY][sX].isValidMove(player.getWho(), sY, sX, eY, eX, isPieceOnEndPos(sY, sX, eY, eX));

            if (canContinue) {
                board[eY][eX] = board[sY][sX];
                board[sY][sX] = null;
                return true;
            } else return false;


//            boolean ifContinue;
//            if ((board[sY][sX] instanceof Pawn && isSameColorPieceOnEndPos(sY, sX, eY, eX))) {
//                ifContinue = board[sY][sX].isValidMove(player.getWho(), sY, sX, eY, eX);
//            } else {
//                ifContinue = board[sY][sX].isValidMove(player.getWho(), sY, sX, eY, eX);
//            }
//            if (ifContinue) {
//                board[eY][eX] = board[sY][sX];
//                board[sY][sX] = null;
//                return true;
//            }
        }
    }

    private boolean isSameColorPieceOnEndPos(int sY, int sX, int eY, int eX) {
        return board[eY][eX] != null && Integer.parseInt(board[sY][sX].getColor().substring(4, 6)) == Integer.parseInt(board[eY][eX].getColor().substring(4, 6));
    }

    private boolean isPieceOnEndPos(int sY, int sX, int eY, int eX) {
        return board[eY][eX] != null && Integer.parseInt(board[sY][sX].getColor().substring(4, 6)) != Integer.parseInt(board[eY][eX].getColor().substring(4, 6));
    }
}
