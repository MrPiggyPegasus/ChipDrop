/*
 * Copyright (c) 2023. "MrPiggyPegasus" Subject to the MIT License, found in "LICENSE.txt"
 */

package Connect4;

public class Board {
    public int[][] board = new int[6][7];
    public int player;

    public Board() {
        this.player = 1;
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
                if (board[i][j] == -1) {
                    System.out.print(2 + " ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public int situation() {
        // checks horizontal rows
        int consec = 0;
        int player = 1;
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (board[row][col] == 0) {
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
        // checks vertical rows
        consec = 0;
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 6; row++) {
                if (board[row][col] == 0) {
                    consec = 0;
                } else if (board[row][col] == player) {
                    consec++;
                } else {
                    player = -player;
                }
                if (consec == 4) {
                    System.out.println("a");
                    return player;
                }
            }
        }
        // check downwards-right diagonals
        consec = 0;
        for(int col=0; col<4; col++) {
            for(int row=0; row<3; row++) {
                for(int shift=0; shift<4; shift++) {
                    if(board[row+shift][col+shift] == 0) {
                        consec = 0;
                    }
                    if(board[row+shift][col+shift] != player) {
                        consec = 0;
                        player = -player;
                    }
                    consec++;
                    if(consec == 4) {
                        System.out.println("a");
                        return player;
                    }
                }
            }
        }
        // check downwards-left diagonals
        consec=0;
        for(int col=0; col<4; col++) {
            for(int row=0; row<3; row++) {
                for(int shift=4; shift>0; shift--) {
                    if(board[row+shift][col+shift]==0) {
                        consec = 0;
                    }
                    if(board[row+shift][col+shift] != player) {
                        consec=0;
                        player=-player;
                    }
                    consec++;
                    if(consec == 4) {
                        System.out.println("b");
                        return player;
                    }
                }
            }
        }
        return 2;
    }

    static class IllegalMoveException extends Exception {
        public IllegalMoveException(int move) {
            super(String.valueOf(move) + " is illegal for the given position.");
        }
    }
}
