import Pieces.*;

public class Board {

    private Piece[][] board;
    private StringBuilder stringBuilder;
    private Player player1, player2;
    private int turn = 1;

    Board(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        stringBuilder = new StringBuilder();

        initBoard(board = new Piece[8][8]);

        board[5][1] = new Pawn(5, 1, "\033[1;31m");
    }

    /*
    R  K  B  Q  KI B  K  R
    P  P  P  P  P  P  P  P
    N  N  N  N  N  N  N  N
    N  N  N  N  N  N  N  N
    N  N  N  N  N  N  N  N
    N  N  N  N  N  N  N  N
    P  P  P  P  P  P  P  P
    R  K  B  KI Q  B  K  R
    */
    private void initBoard(Piece[][] board) {
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

    //    public boolean makeMove(Player player, int x, int startY){
    public boolean makeMove(Player player, int startY, int startX, int endY, int endX) {
        if (board[startY][startX].isValidMove(player.getWho(), startY, startX, endY, endX)) {
            board[endY][endX] = board[startY][startX];
            board[startY][startX] = null;
            return true;
        } else return false;
    }
}
