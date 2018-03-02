import Game.Board;
import Game.Player;

import java.util.ArrayList;

class Main {


    public static void main(String[] args) {
        Player player1, player2;
        Board board;

        player1 = new Player("Sebi", 1);
        player2 = new Player("Tom", 2);
        board = new Board();

        ArrayList<Boolean> arrayList = new ArrayList<>();
        System.out.println(board.getBoard());
        System.out.println(board.makeMove(player1, 7, 2, 5, 1));
        System.out.println(board.getBoard());
        System.out.println(board.makeMove(player1, 6, 1, 5, 1));
        System.out.println(board.getBoard());
        System.out.println(board.makeMove(player1, 7, 2, 5, 0));
        System.out.println(board.getBoard());

        for (boolean b :
                arrayList) {
            System.out.print(b + " ");
        }
    }
}
