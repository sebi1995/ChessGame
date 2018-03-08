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

        Player currentPlayer = player1;
        String currentPlayerColor = currentPlayer.getColor();
        int move = 1;

        while (board.canGameContinue(currentPlayerColor)){
            System.out.println("It's " + currentPlayer.getName() + " turn!");

            System.out.println(board.getBoard());

            if (move > 1){
                if (board.isPositionCheckForKing(currentPlayerColor, board.getKingYPos(), board.getKingXPos())){
                    System.out.println("Your King is in danger! you must move it or you lose.");
                }
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
                currentPlayerColor = currentPlayer.getColor();
            } else System.out.println("Try again!");

            ++move;
        }

        System.out.print("Congratulations ");

        if (currentPlayerColor.equals(player1.getColor())){
            System.out.print(player2.getName());
        } else {
            System.out.print(player1.getName());
        }

        System.out.print(", you have won!");
    }
}







































/* writeToFile writeToFile = new writeToFile();

    System.out.println(writeToFile.getText("input"));

    writeToFile.writeText("hool", "input");
    writeToFile.writeText("hool", "input");
    writeToFile.writeText("hool", "input");
    writeToFile.writeText("hool", "input");

    System.out.println(writeToFile.getText("input"));*/
