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

public class Engine {
    public boolean killSwitch = false;

    public void kill() { // terminates the process immediately by throwing ProcessTerminatedException
        killSwitch = true;
    }

    public void resetKillSwitch() { // required after termination to allow for minimax to restart
        killSwitch = false;
    }
    public int[] minimax(Board pos, int depth, int alpha, int beta) throws ProcessTerminatedException {
        int sit = pos.situation();
        if (pos.situation()!=2)
            return new int[]{sit*1000, 9};

        if (killSwitch) {
            throw new ProcessTerminatedException();
        }
        if (depth==0) {
            return new int[]{heuristicEval(pos), 9};
        }
        int maxValue;
        int maxMove = 0;
        if (pos.player==1) { // maximising player
            maxValue = -Integer.MAX_VALUE;
            for (int move=0; move<7; move++) {
                if (pos.isLegal(move)) {
                    Board childPos = new Board(pos.board, pos.player);
                    childPos.play(move);
                    int value = minimax(childPos, depth-1, alpha, beta)[0];
                    if (value>maxValue) {
                        maxMove = move;
                        maxValue = value;
                    }
                    if (alpha>maxValue) {
                        alpha = maxValue;
                    }
                    if (value>=beta) {
                        break;
                    }
                }
            }
        } else {
            maxValue = Integer.MAX_VALUE;
            for (int move=0; move<7; move++) {
                if (pos.isLegal(move)) {
                    Board childPos = new Board(pos.board, pos.player);
                    childPos.play(move);
                    int value = minimax(childPos, depth-1, alpha, beta)[0];
                    if (value<maxValue) {
                        maxMove = move;
                        maxValue = value;
                    }
                    if (beta<maxValue) {
                        beta=maxValue;
                    }
                    if (value<=alpha) {
                        break;
                    }
                }
            }
        }
        return new int[]{maxValue, maxMove};
    }

    public static int heuristicEval(Board pos) {
        // will return heuristic value of a position for minimax
        // win returns + or -1000 for player 1 or 2
        // draw returns 0
        // else will return the net value derived by the following rules:
        // +-2 points for row/diagonal of 2
        // +-5 points for row/diagonal of 3
        int netPoints = 0;

        // checks horizontal lines of 4:
        int consec ;
        int player = 1;
        for (int row=0; row<6; row++) {
            consec = 0;
            for (int col=0; col<7; col++) {
                if (pos.board[row][col] == 0) {
                    consec = 0;
                } else if (pos.board[row][col] == player) {
                    consec++;
                } else {
                    player = -player;
                    consec = 1;
                }
                if (consec > 1) {
                    netPoints += consec*player;
                    if (consec == 4) {
                        return player*1000;
                    }

                }
            }
        }
        // checks vertical lines of 4:
        player = 1;
        for (int col=0; col<7; col++) {
            consec = 0;
            for (int row = 0; row < 6; row++) {
                if(pos.board[row][col] == player) {
                    consec++;
                } else if (pos.board[row][col] == 0) {
                    consec=0;
                } else {
                    player = -player;
                    consec = 1;
                }
                if (consec > 1) {
                    netPoints += consec*player;
                    if (consec == 4) {
                        return player * 1000;
                    }
                }
            }
        }
        // check downwards-right diagonals of 4:
        player = 1;
        for (int col=0; col<4; col++) {
            for (int row=0; row<3; row++) {
                consec = 0;
                for (int shift=0; shift<4; shift++) {
                    if (pos.board[row+shift][col+shift] == 0) {
                        consec = 0;
                    } else if(pos.board[row+shift][col+shift] == player) {
                        consec++;
                    } else {
                        player = -player;
                        consec = 1;
                    }
                    if (consec > 1) {
                        netPoints += consec*player;
                        if (consec == 4) {
                            return player * 1000;
                        }
                    }
                }
            }
        }
        // check downwards-left diagonals of 4:
        player=1;
        for (int col=0; col<4; col++) {
            for (int row=0; row<3; row++) {
                consec=0;
                for (int shift=3; shift>=0; shift--) {
                    if (pos.board[row+shift][col+(3-shift)]==0) {
                        consec = 0;
                    } else if (pos.board[row+shift][col+(3-shift)] == player) {
                        consec++;
                    } else {
                        consec = 1;
                        player = -player;
                    }
                    if(consec > 1) {
                        netPoints += consec*player;
                        if (consec == 4) {
                            return player * 1000;
                        }
                    }
                }
            }
        }
        if (pos.isDraw()) {
            return 0;
        }
        return netPoints;
    }
    public static class ProcessTerminatedException extends Exception {
        public ProcessTerminatedException() {
            super("Minimax process was terminated externaly");
        }
    }
}
