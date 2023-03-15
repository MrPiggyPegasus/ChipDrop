package engine;

import connect4.Board;

public class Engine {
    public static int negamax(Board pos, int player, int alpha, int beta) throws Exception {
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
        for(int i=0; i<6; i++) {
            try {
                Board childPos = new Board(pos.board);
                childPos.drop(i, 2);
                int score = -negamax(childPos, 2, -beta, -alpha);
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
        int[] scores = new int[6];
        int c=0;
        for(int i=0; i<6; i++) {
            if(pos.isWinning(i, player)) {
                return i;
            }
            if(pos.isLegal(i)) {
                Board childPos = new Board(pos.board);
                childPos.drop(i, player);
                scores[c] = negamax(childPos, player, -100, 100);
                c++;
            }
        }
        int max=scores[0];
        int maxIndex=0;
        for(int i=1; i<c; i++) {
            if(scores[i]>max) {
                max=scores[i];
                maxIndex=i;
            }
        }
        return maxIndex;
    }
}
