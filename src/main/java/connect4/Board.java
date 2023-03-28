/*
 Copyright (c) 2023. "MrPiggyPegasus"
 This file is part of the ChipDrop Connect4 engine, see https://github.com/MrPiggyPegasus/ChipDrop.

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
 FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

package connect4;

import nogui.Play;

public class Board {
    public int[][] board = new int[6][7];
    public int player;
    public String pgn = "";
    public Engine engine;
    public Board() {
        player = 1;
        for(int i=0; i<6; i++) {
            for(int j=0; j<7; j++) {
                board[i][j] = 0;
            }
        }
    }

    public Board(String pgn) {
        engine = new Engine();
        // initialise vars
        try {
            player = 1;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    board[i][j] = 0;
                }
            }
            // play out pgn game
            for (int i = 0; i < pgn.length(); i++) {
                play(Character.getNumericValue(pgn.charAt(i)));
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(new IllegalPgnException(pgn));
        }
    }

    public Board(int[][] pos, int player) {
        this.player = player;
        engine = new Engine();
        for(int row=0; row<6; row++) {
            System.arraycopy(pos[row], 0, board[row], 0, 7);
        }
    }

    public static boolean isValidPgn(String pgn) {
        try {
            new Board(pgn);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLegal(int move) {
        return board[0][move]==0;
    }

    public boolean isInPlay() {
        return situation() == 2;
    }

    public int highestTokenAtCol(int col) {
        for (int i=0; i<6; i++) {
            if (board[i][col] != 0) {
                return i;
            }
        }
        return 6;
    }
    public int bestMove() throws Engine.ProcessTerminatedException {
        if(!isInPlay()) {
            throw new RuntimeException(new PositionAlreadyConcludedException(pgn));
        }
        if(pgn.length()==0) {
            return 3;
        }
        return engine.minimax(this, 8, -1000000, 1000000)[1];
    }

    public void killMinimax() {
        engine.kill();
    }

    public void resetMinimax() {
        engine.resetKillSwitch();
    }

    public void play(int move) {
        try {
            for (int i=5; i>=0; i--) {
                if (board[i][move] == 0) {
                    board[i][move] = player;
                    player = -player;
                    pgn += String.valueOf(move);
                    return;
                }
            }
            throw new IllegalMoveException(move);
        } catch (Exception e) {
            throw new RuntimeException(new IllegalMoveException(move));
        }
    }

    public void show() {
        System.out.println("|-------------------|\n|0  1  2  3  4  5  6|");
        for (int i=0; i<6; i++) {
            switch(board[i][0]) {
                case(0) -> System.out.print("|.  ");
                case(1) -> System.out.print("|1  ");
                case(-1)-> System.out.print("|2  ");
            }
            for (int j=1; j<6; j++) {
                switch(board[i][j]) {
                    case(0) -> System.out.print(".  ");
                    case(1) -> System.out.print("1  ");
                    case(-1)-> System.out.print("2  ");
                }
            }
            switch(board[i][6]) {
                case(0) -> System.out.print(".|");
                case(1) -> System.out.print("1|");
                case(-1)-> System.out.print("2|");
            }
            System.out.println();
        }
    }

    public int situation() {
        /*  Query the situation of the board.
            returns:
                |------------------------------|
                |1   if player 1 has won       |
                |-1  if player 2 has won       |
                |0   if the position is a draw |
                |2   if the game is ongoing    |
                |------------------------------|
         */
        // checks horizontal lines of 4:
        int consec;
        int player = 1;
        for(int row=0; row<6; row++) {
            consec = 0;
            for(int col=0; col<7; col++) {
                if(board[row][col] == 0) {
                    consec = 0;
                } else if (board[row][col] == player) {
                    consec++;
                } else {
                    player = -player;
                    consec = 1;
                }
                if (consec == 4) {
                    return player;
                }
            }
        }
        // checks vertical lines of 4:
        player = 1;
        for(int col=0; col<7; col++) {
            consec = 0;
            for(int row = 0; row < 6; row++) {
                if(board[row][col] == player) {
                    consec++;
                } else if (board[row][col] == 0) {
                    consec=0;
                } else {
                    player = -player;
                    consec = 1;
                }
                if (consec == 4) {
                    return player;
                }
            }
        }
        // check downwards-right diagonals of 4:
        for(int col=0; col<4; col++) {
            for(int row=0; row<3; row++) {
                if(board[row][col]==0) {
                    continue;
                }
                for(int shift=0; shift<4; shift++) {
                    if(board[row+shift][col+shift]!=board[row][col]) {
                        break;
                    }
                    if(shift==3) {
                        return  board[row][col];
                    }
                }
            }
        }
        // check downwards-left diagonals of 4:
        player = 1;
        for(int col=0; col<4; col++) {
            for(int row=0; row<3; row++) {
                consec = 0;
                for(int shift=3; shift>=0; shift--) {
                    if(board[row+shift][col+(3-shift)]==0) {
                        consec = 0;
                    } else if(board[row+shift][col+(3-shift)] == player) {
                        consec++;
                    } else {
                        consec = 1;
                        player = -player;
                    }
                    if(consec == 4) {
                        return player;
                    }
                }
            }
        }
        if (this.isDraw()) {
            return 0;
        }
        return 2;
    }

    public boolean isDraw() {
        for(int col=0; col<7; col++) {
            if(board[0][col]==0) {
                return false;
            }
        }
        return true;
    }

    public void playerVsPlayer() {
        Play.playerVsPlayer(this);
    }

    public void playerVsComputer(boolean computersTurn) {
        Play.playerVsComputer(this, computersTurn);
    }

    public static class IllegalMoveException extends Exception {
        public IllegalMoveException(int move) {
            super(move + " is illegal for the given position.");
        }
    }

    public static class IllegalPgnException extends Exception {
        public IllegalPgnException(String pgn) {
            super("PGN `" + pgn + "` is invalid");
        }
    }

    public static class PositionAlreadyConcludedException extends Exception {
        public PositionAlreadyConcludedException(String pgn) {
            super("The position " + pgn + " cannot be analysed because it is already concluded");
        }
    }
}
