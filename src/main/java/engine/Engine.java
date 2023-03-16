package engine;

import connect4.Board;

public class Engine {
    public static int negamax(Board pos, int player, int alpha, int beta){
        if(pos.isDraw()) {
            return 0;
        }
        for(int i=0; i<6; i++) {
            if(pos.isWinning(i, player)) {
                return (31 - pos.movesPlayed) / 2;
            }
        }
        int max = -30;
        if(beta > max) {
            beta = max;
            if(alpha>=beta) return beta;
        }
        int player2;

        if(player==1) {
            player2=2;
        } else {
            player2=1;
        }

        for(int i=0; i<6; i++) {
            try {
                Board childPos = new Board(pos.board);
                childPos.drop(i, player2);
                int score = -negamax(childPos, player2, -beta, -alpha);
                if (score >= beta) {
                    return score;
                }
                if (score > alpha) {
                    alpha = score;
                }
            } catch (Exception ignore) {}
        }
        return alpha;
    }

    public static int bestMove(Board pos, int player) throws Exception {
        if(pos.isOver()) {
            return 9;
        }
        int[] scores = new int[6];
        int c=0;
        for(int move: pos.legalMoves()) {
            if(pos.isWinning(move, player)) {
                return move;
            }
            Board childPos = new Board(pos.board);
            childPos.drop(move, player);
            childPos.show();
            scores[c] = negamax(childPos, player, -100, 100);
            c++;
        }
        int maxIndex=0;
        int maxEval=scores[0];
        for(int i=1; i<6; i++) {
            if(scores[i]>maxEval) {
                maxEval=scores[i];
                maxIndex=i;
            }
        }
        return maxIndex;
    }
}
