import Game.Board;
import Game.Player;

import java.util.Scanner;

class Main {

    private static final String BLUE = "\033[1;34m";
    private static final String RED = "\033[1;31m";

    public static void main(String[] args) {
        Player player1 = new Player("Sebi", BLUE);
        Player player2 = new Player("Tom", RED);
        Board board = new Board();

        Scanner scanner = new Scanner(System.in);

        String color = player1.getColor();
        Player currentPlayer = player1;

        while (board.canGameContinue(color)){
            System.out.println("It's " + currentPlayer.getName() + " turn!");

            System.out.println(board.getBoard());

            if (board.isKingInCheck(currentPlayer)){
                System.out.println("Your King is in danger! you must move it or you lose.");
            }

            System.out.println(currentPlayer.getName() + ", please enter a starting X and Y position to select a piece to move,\nand a ending X and Y position to move the piece.");

            System.out.print("Starting X: ");
            int sX = scanner.nextInt();
            System.out.print("Starting Y: ");
            int sY = scanner.nextInt();
            System.out.print("Ending X: ");
            int eX = scanner.nextInt();
            System.out.print("Ending Y: ");
            int eY = scanner.nextInt();

            if (board.makeMove(currentPlayer, sY, sX, eY, eX)){
                currentPlayer = (currentPlayer == player1) ? player2 : player1;
                color = currentPlayer.getColor();
            } else System.out.println("Try again!");
        }
    }
}







































/* writeToFile writeToFile = new writeToFile();

    System.out.println(writeToFile.getText("input"));

    writeToFile.writeText("hool", "input");
    writeToFile.writeText("hool", "input");
    writeToFile.writeText("hool", "input");
    writeToFile.writeText("hool", "input");

    System.out.println(writeToFile.getText("input"));*/
