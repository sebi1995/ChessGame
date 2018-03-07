import Game.Board;
import Game.Player;

class Main {

    private static final String BLUE = "\033[1;34m";
    private static final String RED = "\033[1;31m";

    public static void main(String[] args) {
        Player player1, player2;
        Board board;
        player1 = new Player("Sebi", 1, BLUE);
        player2 = new Player("Tom", 2, RED);
        board = new Board();



        System.out.println(board.getBoard());
    }
}







































/* writeToFile writeToFile = new writeToFile();

    System.out.println(writeToFile.getText("input"));

    writeToFile.writeText("hool", "input");
    writeToFile.writeText("hool", "input");
    writeToFile.writeText("hool", "input");
    writeToFile.writeText("hool", "input");

    System.out.println(writeToFile.getText("input"));*/
