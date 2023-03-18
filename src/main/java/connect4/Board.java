/*
 * Copyright (c) 2023. "MrPiggyPegasus" Subject to the MIT License, found in "LICENSE.txt"
 */

package connect4;

public class Board {
    public int[][] board = new int[6][7];
    public int player;
    public static final int PLAYER_1_WON = 1;
    public static final int PLAYER_2_WON = -1;
    public static final int DRAW = 0;
    public static final int ONGOING = 2;
    public String pgn = "";
    public Board() {
        player = 1;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = 0;
            }
        }
    }

    public void play(int move) throws IllegalMoveException {
        try {
            for (int i = 5; i >= 0; i--) {
                if (board[i][move] == 0) {
                    board[i][move] = player;
                    player = -player;
                    return;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalMoveException(move);
        }
        throw new IllegalMoveException(move);
    }

    public void show() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                switch(board[i][j]) {
                    case(0) -> System.out.print(".  ");
                    case(1) -> System.out.print("1  ");
                    case(-1)-> System.out.print("2  ");
                }
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
        int consec = 0;
        int player = 1;
        for(int row = 0; row < 6; row++) {
            for(int col = 0; col < 7; col++) {
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
        player=1;
        consec = 0;
        for(int col = 0; col < 7; col++) {
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
        player=1;
        consec = 0;
        for(int col=0; col<4; col++) {
            for(int row=0; row<3; row++) {
                for(int shift=0; shift<4; shift++) {
                    if(board[row+shift][col+shift] == 0) {
                        consec = 0;
                    } else if(board[row+shift][col+shift] == player) {
                        consec++;
                    } else {
                        player = -player;
                        consec = 1;
                    }
                    if(consec == 4) {
                        return player;
                    }
                }
            }
        }
        // check downwards-left diagonals of 4:
        player=1;
        consec=0;
        for(int col=0; col<4; col++) {
            for(int row=0; row<3; row++) {
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
        return 2;
    }
    static class IllegalMoveException extends Exception {
        public IllegalMoveException(int move) {
            super(move + " is illegal for the given position.");
        }
    }
}
