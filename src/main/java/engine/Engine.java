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
    public static int negamax(Board pos, int alpha, int beta) {
        if(pos.isDraw()) {
            return 0;
        }
        for(int x=0; x<7; x++) { // if current player can win next move
            if(pos.isLegal(x) && pos.isWinning(x)) {
                return(43-pos.nbMoves())/2;
            }
        }
        int max=(41-pos.nbMoves())/2;
        if(beta > max) { // prune tree
            beta=max;
            if(alpha>=beta) {
                return beta;
            }
        }
        for(int x=0; x<7; x++) {
            if(pos.isLegal(x)) {
                Board p2 = new Board(pos);
                p2.play(x);
                int score = -negamax(p2, -beta, -alpha);
                if(score >= beta) {
                    return score;
                }
                if(score > alpha) {
                    alpha = score;
                }
            }
        }
        return alpha;
    }

    public static int bestMove(Board pos) {
        int maxEval = -100;
        int maxMove = 0;
        for(int move=0; move<7; move++) {
            if(pos.isLegal(move)) {
                Board childPos = new Board(pos);
                childPos.play(move);
                int eval = negamax(childPos, -1000,1000);
                if(eval>maxEval) {
                    maxEval = eval;
                    maxMove = move;
                }
            }
        }
        return maxMove;
    }
}
