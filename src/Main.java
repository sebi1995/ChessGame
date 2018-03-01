import java.util.ArrayList;

class Main {


    public static void main(String[] args) {
        Player player1, player2;
        Board board;

        player1 = new Player("Sebi", 1);
        player2 = new Player("Tom", 2);
        board = new Board(player1, player2);

        ArrayList<Boolean> arrayList = new ArrayList<>();
        System.out.println(board.getBoard());
        arrayList.add(board.makeMove(player1, 6, 0, 5, 1));
        System.out.println(board.getBoard());


        for (boolean b :
                arrayList) {
            System.out.print(b + " ");
        }
    }
}
