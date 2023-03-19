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

package engine;

import connect4.Board;

public class Engine {
    public static int[] minimax(Board pos, int alpha, int beta) {
        int situation = pos.situation();
        if(situation!=2) {
            return new int[]{situation, 9};
        }
        int maxValue;
        int maxMove = 0;
        if(pos.player==1) { // maximising player
            maxValue = -1000;
            for(int move=0; move<7; move++) {
                if(pos.isLegal(move)) {
                    Board childPos = new Board(pos.pgn);
                    childPos.play(move);
                    int value = minimax(childPos, alpha, beta)[0];
                    if(value>maxValue) {
                        maxMove = move;
                        maxValue = value;
                    };
                    if(alpha>maxValue) {
                        alpha = maxValue;
                    }
                    if(value>=beta) {
                        break;
                    }
                }
            }
        } else {
            maxValue = 1000;
            for(int move=0; move<7; move++) {
                if(pos.isLegal(move)) {
                    Board childPos = new Board(pos.pgn);
                    childPos.play(move);
                    int value = minimax(childPos, alpha, beta)[0];
                    if(value<maxValue) {
                        maxMove = move;
                        maxValue = value;
                    }
                    if(beta<maxValue) {
                        beta=maxValue;
                    }
                    if(value<=alpha) {
                        break;
                    }
                }
            }
        }
        return new int[]{maxValue, maxMove};
    }
}
