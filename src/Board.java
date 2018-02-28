import Pieces.*;

public class Board {

    private Piece[][] pieces;

    Board() {
        initBoard(pieces = new Piece[8][8]);

//        pieces[2][2] = pieces[1][2];
//        pieces[1][2] = null;
    }

    private void initBoard(Piece[][] pieces) {
        pieces[0][0] = new Rook(0, 0, 0);
        pieces[0][1] = new Knight(0, 1, 0);
        pieces[0][2] = new Bishop(0, 2, 0);
        pieces[0][3] = new Queen(0, 3, 0);
        pieces[0][4] = new King(0, 4, 0);
        pieces[0][5] = new Bishop(0, 5, 0);
        pieces[0][6] = new Knight(0, 6, 0);
        pieces[0][7] = new Rook(0, 7, 0);

        for (int i = 0; i < 8; ++i) {
            pieces[1][i] = new Pawn(1, i, 0);
        }

        for (int i = 0; i < 8; ++i) {
            pieces[6][i] = new Pawn(6, i, 1);
        }


        pieces[7][0] = new Rook(7, 0, 1);
        pieces[7][1] = new Knight(7, 1, 1);
        pieces[7][2] = new Bishop(7, 2, 1);
        pieces[7][3] = new Queen(7, 3, 1);
        pieces[7][4] = new King(7, 4, 1);
        pieces[7][5] = new Bishop(7, 5, 1);
        pieces[7][6] = new Knight(7, 6, 1);
        pieces[7][7] = new Rook(7, 7, 1);
    }

    public void printBoard(){

        StringBuilder stringBuilder = new StringBuilder();
        int spaces = 8;

        for (int i = 0; i < 8; ++i) {
            System.out.print((i+1) + "\t");

            for (int j = 0; j < 8; ++j) {
                if (pieces[i][j] != null){
                    stringBuilder.append(pieces[i][j].getType());
                    for (int k = pieces[i][j].getType().toString().length(); k < spaces; ++k) {
                        stringBuilder.append(" ");
                    }
                } else {
                    for (int k = 0; k < spaces; ++k) {
                        stringBuilder.append(" ");
                    }
                }
                System.out.print(stringBuilder);
                stringBuilder.delete(0, stringBuilder.length());
            }
            System.out.println();
        }

        System.out.print("\t");
        for (int i = 0; i < 8; ++i) {
            System.out.print((i+1) + "       ");
        }
    }
}
