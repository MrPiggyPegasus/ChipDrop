/*
   Copyright (c) 2023. "MrPiggyPegasus"
   Permission is hereby granted, free of charge, to any person obtaining a copy
   of this software and associated documentation files (the "Software"), to deal
   in the Software without restriction, including without limitation the rights
   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
   copies of the Software, and to permit persons to whom the Software is
   furnished to do so, subject to the following conditions:

   The above copyright notice and this permission notice shall be included in all
   copies or substantial portions of the Software.

   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
   FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
   SOFTWARE.
 */

package play;

import connect4.Board;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Play {
    public static void playerTurn(Board pos) {
        if(pos.isInPlay()) {
            Scanner s = new Scanner(System.in);
            pos.show();
            if (pos.player == 1) {
                System.out.println("Enter move for player 1");
            } else {
                System.out.println("Enter move for player 2");
            }
            int move = 9;
            do {
                try {
                    move = s.nextInt();
                    if (pos.isLegal(move)) {
                        pos.play(move);
                        return;
                    }
                } catch (InputMismatchException e) {
                    s.next();
                }
            } while (!pos.isLegal(move));
        }
    }

    public static void computerTurn(Board pos) {
        if(pos.isInPlay()) {
            int bestMove = pos.bestMove();
            pos.play(bestMove);
            System.out.println("Computer's move: " + bestMove);
        }
    }

    private static void pvc(Board pos, int computerPlayer) {
        if(computerPlayer==2) {
            computerPlayer = -1;
        }
        if(computerPlayer==pos.player) {
            do {
                computerTurn(pos);
                playerTurn(pos);
            } while (pos.isInPlay());
        } else if(computerPlayer==-pos.player) {
            do {
                playerTurn(pos);
                computerTurn(pos);
            } while(pos.isInPlay());
        } else {
            throw new RuntimeException(new InvalidComputerPlayerException(computerPlayer));
        }
        System.out.println("\n\n\n");
        pos.show();
        switch(pos.situation()) {
            case(1) -> System.out.println("Player 1 wins!");
            case(-1)-> System.out.println("Player 2 wins!");
            case(0) -> System.out.println("Draw!");
        }
    }

    public static void playerVsComputer(Board pos) {//computer will always play the next move
        pvc(pos, pos.player);
    }

    public static void playerVsComputer(Board pos, boolean computerTurn) {
        if(computerTurn) {
            pvc(pos, pos.player);
        } else {
            pvc(pos, -pos.player);
        }
    }

    public static void playerVsComputer(int computerPlayer) {
        pvc(new Board(), computerPlayer);
    }
    private static void pvp(Board pos) {
        Scanner s = new Scanner(System.in);
        do {
            playerTurn(pos);
        } while (pos.isInPlay());
        System.out.println("\n\n\n");
        pos.show();
        switch(pos.situation()) {
            case(1) -> System.out.println("Player 1 wins!");
            case(-1)-> System.out.println("Player 2 wins!");
            case(0) -> System.out.println("Draw!");
        }
        System.out.println(pos.pgn);
        System.out.println(pos.situation());
    }
    public static void playerVsPlayer(Board pos) { // user inputs moves for both players
        pvp(pos);
    }

    public static void playerVsPlayer() { // same but without
        pvp(new Board());
    }

    public static class InvalidComputerPlayerException extends Exception {
        public InvalidComputerPlayerException(int computerPlayer) {
            super(String.valueOf(computerPlayer));
        }
    }
}
