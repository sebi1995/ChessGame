class Main {


    public static void main(String[] args) {
        Player player1, player2;
        Board board;

        player1 = new Player("Sebi", 1);
        player2 = new Player("Tom", 2);
        board = new Board(player1, player2);

        System.out.println(board.getBoard());
        System.out.println(board.makeMove(player1,6, 0, 5, 1));
        System.out.println(board.getBoard());
        System.out.println(board.makeMove(player1,5, 1, 4, 0));
        System.out.println(board.getBoard());
        System.out.println(board.makeMove(player2,1, 0, 2, 0));
        System.out.println(board.getBoard());
//        System.out.println(board.makeMove(5, 0));
//        System.out.println(board.getBoard());
//        System.out.println(board.makeMove(2, 0));
//        System.out.println(board.getBoard());
//        System.out.println(board.makeMove(4, 0));
//        System.out.println(board.getBoard());
    }
}
