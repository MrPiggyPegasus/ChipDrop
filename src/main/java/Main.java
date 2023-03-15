import Connect4.Board;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        int choice;
        do {
            System.out.println("CONNECT 4\n\n1 - Play\n\n2 -Play against computer");
            choice = s.nextInt();
        } while (choice != 1 && choice != 2);
        if (choice == 1) {
            Board board = new Board();
            while(true) {
                System.out.println("\n");
                if (board.isOver()) {
                    break;
                }
                board.show();
                int move = 0;
                do {
                    System.out.println("Enter move for player 1:");
                    try {
                        move = s.nextInt();
                    } catch (InputMismatchException ignore) {}
                } while (!board.isLegal(move-1));
                board.drop(move-1, 1);
                System.out.println("\n");
                board.show();
                if (board.isOver()) {
                    break;
                }
                do {
                    System.out.println("Enter move for player 2:");
                    try {
                        move = s.nextInt();
                    } catch (InputMismatchException ignore) {}
                } while (!board.isLegal(move-1));
                board.drop(move-1, 2);
            }
            if(board.situation()==-1) {
                System.out.println("Player 1 wins!");
            } else if(board.situation() == 1) {
                System.out.println("Player 2 wins!");
            } else {
                System.out.println("Draw!");
            }
        }
    }
}
