import Game.Board;
import Game.Player;

class Main {


    public static void main(String[] args) {
        Player player1, player2;
        Board board;

        player1 = new Player("Sebi", 1);
        player2 = new Player("Tom", 2);
        board = new Board();

        System.out.println(board.makeMove(player1, 7, 2, 5, 1));
        System.out.println(board.getBoard());
        System.out.println(board.makeMove(player1, 6, 1, 5, 1));
        System.out.println(board.getBoard());
        System.out.println(board.makeMove(player1, 7, 2, 5, 0));
        System.out.println(board.getBoard());
        System.out.println(board.makeMove(player1, 5, 0, 1, 4));
        System.out.println(board.getBoard());
        System.out.println(board.makeMove(player1, 1, 4, 0, 3));
        System.out.println(board.getBoard());
    }
}
