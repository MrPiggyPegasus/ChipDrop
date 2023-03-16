package play;

import connect4.Board;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Play {
    public static void playerTurn(Board board, int player, Scanner s) throws Exception {
        int move=-1;
        System.out.println("Enter move for player " + player + ": ");
        do {
            try {
                move = s.nextInt();
            } catch (InputMismatchException e) {
                s.next();
            }
        } while (!board.isLegal(move-1));
        board.drop(move-1, player);
    }
    public static void pve() throws Exception {
        int computerPlayer=-1;
        Board board = new Board();
        Scanner s = new Scanner(System.in);
        System.out.println("Computer as [1] or [2]?");
        do {
            try {
                computerPlayer = s.nextInt();
            } catch (InputMismatchException e) {
                s.next();
            }
        } while(computerPlayer!=1 && computerPlayer!=2);
        if(computerPlayer==1) {
            while(true) {
                int cmove =board.bestMove(2);
                board.drop(cmove, 1);
                System.out.println("Computer's move: " + cmove);
                board.show();
                if(board.isOver()) {
                    break;
                }
                playerTurn(board, 2, s);
                if(board.isOver()) {
                    break;
                }
            }
        } else {
            board.show();
            while(true) {
                playerTurn(board, 1, s);
                if(board.isOver()) {
                    break;
                }
                int cmove =board.bestMove(2);
                board.drop(cmove, 2);
                if (board.isOver()) {
                    break;
                }
                System.out.println("Computer's move: " + cmove);
                board.show();
            }
        }
        System.out.println(board.situation());
        board.show();
        System.out.println(board.pgn);
    }
    public static void pvp() throws Exception {
        Board board = new Board();
        Scanner s = new Scanner(System.in);
        while(true) {
            playerTurn(board, 1, s);
            if(board.isOver()) {
                break;
            }
            playerTurn(board, 2, s);
            if(board.isOver()) {
                break;
            }
        }
        if(board.situation()==0) {
            System.out.println("\nDraw!");
        } else {
            System.out.println("\n Player " + board.situation() + " wins!");
        }
    }
    public static void playInTerminal() throws Exception {
        Scanner s = new Scanner(System.in);
        int choice=-1;
        System.out.println("CONNECT 4\n\n[1] - Play\n\n[2] - Play against computer");
        do {
            try {
                choice = s.nextInt();
            } catch (InputMismatchException e) {
                s.next();
            }
        } while (choice!=1 && choice!=2);
        if (choice == 1) {
            pvp();
        } else {
            pve();
        }
    }
}
