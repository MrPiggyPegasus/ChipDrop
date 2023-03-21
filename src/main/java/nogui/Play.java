/*
 * Copyright (c) 2023. "MrPiggyPegasus"
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package nogui;

import connect4.Board;

import java.awt.*;
import java.net.URI;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Play {

    public static void menu() {
        while(true) {
            System.out.println("""
                      ______   __        __            _______                              \s
                     /      \\ /  |      /  |          /       \\                             \s
                    /$$$$$$  |$$ |____  $$/   ______  $$$$$$$  |  ______    ______    ______\s
                    $$ |  $$/ $$      \\ /  | /      \\ $$ |  $$ | /      \\  /      \\  /      \\
                    $$ |      $$$$$$$  |$$ |/$$$$$$  |$$ |  $$ |/$$$$$$  |/$$$$$$  |/$$$$$$  |
                    $$ |   __ $$ |  $$ |$$ |$$ |  $$ |$$ |  $$ |$$ |  $$/ $$ |  $$ |$$ |  $$ |
                    $$ \\__/  |$$ |  $$ |$$ |$$ |__$$ |$$ |__$$ |$$ |      $$ \\__$$ |$$ |__$$ |
                    $$    $$/ $$ |  $$ |$$ |$$    $$/ $$    $$/ $$ |      $$    $$/ $$    $$/
                     $$$$$$/  $$/   $$/ $$/ $$$$$$$/  $$$$$$$/  $$/        $$$$$$/  $$$$$$$/\s
                                            $$ |                                    $$ |    \s
                                            $$ |                                    $$ |    \s
                                            $$/                                     $$/     \s
                    =========================================================================""");
            System.out.println("""


                    [1] - Play against the ChipDrop engine
                    [2] - Player against human
                    [3] - Recommend move from PGN*
                    [4] - Info
                    [5] - Exit
                                    
                    *(See info for the standard PGN format)
                    """);
            Scanner s = new Scanner(System.in);
            int choice = 0;
            do {
                try {
                    choice = s.nextInt();
                } catch(InputMismatchException e) {
                    s.next();
                }
            } while (choice > 5 || choice < 1);
            if(choice == 1) {
                System.out.println("\n\n\n[1] - New game\n[2] - From pgn");
                int pgnChoice = 0;
                do {
                    try {
                        pgnChoice = s.nextInt();
                    } catch(InputMismatchException e) {
                        s.next();
                    }
                } while (pgnChoice != 1 && pgnChoice != 2);
                Board pos;
                System.out.println("Should the computer be player [1] or [2]?");
                do {
                    try {
                        choice = s.nextInt();
                    } catch (InputMismatchException e) {
                        s.next();
                    }
                } while (choice != 1 && choice != 2);
                if (pgnChoice == 2) {
                    choice = 0;
                    pos = new Board(getUserPgn());
                } else {
                    pos = new Board();
                }
                pos.playerVsComputer(choice == pos.player);
            } else if(choice == 2) {
                System.out.println("\n\n\n[1] - New game\n[2] - From PGM");
                int pgnChoice = 0;
                do {
                    try {
                        pgnChoice = s.nextInt();
                    } catch(InputMismatchException e) {
                        s.next();
                    }
                } while (pgnChoice != 1 && pgnChoice != 2);
                Board pos;
                if(pgnChoice == 2) {
                    pos = new Board(getUserPgn());
                } else {
                    pos = new Board();
                }
                pos.playerVsPlayer();
            } else if(choice == 3) {
                Board pos = new Board(getUserPgn());
                System.out.println("ChipDrop's move: " + pos.bestMove());
            } else if(choice == 4){
                try {
                    URI uri = new URI("https://github.com/MrPiggyPegasus/ChipDrop/");
                    Desktop.getDesktop().browse(uri);
                } catch(Exception e) {
                    System.out.println("Couldn't open webpage.\n" +
                            "Please visit: https://github.com/MrPiggyPegasus/ChipDrop/");
                }
            } else {
                System.exit(0);
            }
        }
    }

    public static String getUserPgn() {
        System.out.println("Enter PGN: ");
        String pgn;
        Scanner s = new Scanner(System.in);
        do {
            pgn = s.nextLine();
        } while(!Board.isValidPgn(pgn));
        return pgn;
    }
    public static void playerTurn(Board pos) {
        if(pos.isInPlay()) {
            Scanner s = new Scanner(System.in);
            System.out.println("PGN: " + pos.pgn);
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
        pos.show();
        switch(pos.situation()) {
            case(1) -> System.out.println("Player 1 wins!");
            case(-1)-> System.out.println("Player 2 wins!");
            case(0) -> System.out.println("Draw!");
        }
        System.out.println("PGN: " + pos.pgn);
        Scanner s = new Scanner(System.in);
        System.out.println("Press enter to return to the menu.");
        s.nextInt();
    }

    public static void playerVsComputer(Board pos, boolean computerTurn) {
        if(computerTurn) {
            pvc(pos, pos.player);
        } else {
            pvc(pos, -pos.player);
        }
    }

    private static void pvp(Board pos) {
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
        System.out.println("PGN: " + pos.pgn);
        Scanner s = new Scanner(System.in);
        System.out.println("Press enter to return to the menu.");
        s.nextLine();
    }
    public static void playerVsPlayer(Board pos) {
        pvp(pos);
    }

    public static class InvalidComputerPlayerException extends Exception {
        public InvalidComputerPlayerException(int computerPlayer) {
            super(String.valueOf(computerPlayer));
        }
    }
}
