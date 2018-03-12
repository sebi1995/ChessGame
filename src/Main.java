import Game.Board;
import Game.Player;

import java.util.Random;
import java.util.Scanner;

public class Main {

    private final static String BLUE = "\033[1;34m";
    private final static String RED = "\033[1;31m";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Player player1, player2;
        Board board;

        board = new Board();
//        System.out.println("Player 1, please enter your name.");
//        System.out.print("Name: ");
//        player1 = new Player(scanner.nextLine(), BLUE);
        player1 = new Player("seb", BLUE);
//        System.out.println("\nPlayer 2, please enter your name.");
//        System.out.print("Name: ");
//        player2 = new Player(scanner.nextLine(), RED);
        player2 = new Player("tom", RED);

        Player currentPlayer = player1;

        int kingY, kingX;
        boolean isKingCheck;

        while (board.canGameContinue(currentPlayer)) {
            kingY = board.getKingY();
            kingX = board.getKingX();

            String playerColor = currentPlayer.getColor();

            isKingCheck = !board.canKingMoveThere(playerColor, kingY, kingX);

            System.out.println(currentPlayer.getName() + ", it's your turn!");

            if (isKingCheck) {
                System.out.println("Your King is in danger!");
                System.out.println(board.getBoard());
                while (isKingCheck) {
                    System.out.println("Please enter letter for first piece to move.");
                    System.out.print("Letter: ");
                    String startY = scanner.next();

                    System.out.println("Please enter number for first piece to move.");
                    System.out.print("Number: ");
                    int startX = scanner.nextInt();

                    System.out.println("Please enter letter for where to move first piece to.");
                    System.out.print("Letter: ");
                    String endY = scanner.next();

                    System.out.println("Please enter number for where to move first piece to.");
                    System.out.print("Number: ");
                    int endX = scanner.nextInt();

                    if (board.canTakeTurn(currentPlayer, startY, startX, endY, endX)) {
                        board.flipTheBoard(currentPlayer);
                        currentPlayer = (currentPlayer == player1) ? player2 : player1;
                        isKingCheck = board.canKingMoveThere(playerColor, kingY, kingX);
                    } else {
                        System.out.println("Invalid move, try again!");
                        System.out.println(board.getBoard());
                    }
                }
            } else {
                System.out.println(board.getBoard());

                System.out.println("Please enter letter for first piece to move.");
                System.out.print("Letter: ");
                String startY = scanner.next();

                System.out.println("Please enter number for first piece to move.");
                System.out.print("Number: ");
                int startX = scanner.nextInt();

                System.out.println("Please enter letter for where to move first piece to.");
                System.out.print("Letter: ");
                String endY = scanner.next();

                System.out.println("Please enter number for where to move first piece to.");
                System.out.print("Number: ");
                int endX = scanner.nextInt();

                if (board.canTakeTurn(currentPlayer, startY, startX, endY, endX)) {
                    board.flipTheBoard(currentPlayer);
                    currentPlayer = (currentPlayer == player1) ? player2 : player1;
                } else {
                    System.out.println("Please try again!");
                }
            }
        }

        System.out.print("Congratulations ");

        if (currentPlayer.getName().equals(player1.getColor())){
            System.out.print(player2.getName());
        } else {
            System.out.print(player1.getName());
        }

        System.out.print(", you have won!");
    }
}
