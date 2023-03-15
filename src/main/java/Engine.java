import static java.lang.Math.*;
public class Engine {
    public static int eval(Board board) {
        int situation = board.situation();
        if(situation==2) {
            return 0;
        } else {
            return situation;
        }
    }
    public static double minimax(Board pos, boolean maxing, int depth) throws Exception {
        double eval=0;
        if(maxing) {
            if(pos.isOver() || depth==0) {
                System.out.println(eval(pos)-pos.movesPlayed/100.0);
                return eval(pos)-pos.movesPlayed/100.0;
            }
            for(int move: pos.legalMoves()) {
                Board child = new Board(pos.board);
                child.drop(move, 1);
                eval = max(eval, minimax(child, false, depth - 1));
                if(round(eval)==1) {
                    return 1;
                }
            }
        } else {
            if(pos.isOver() || depth==0) {
                System.out.println(eval(pos)+pos.movesPlayed/100.0);
                return eval(pos)+pos.movesPlayed/100.0;
            }
            for(int move: pos.legalMoves()) {
                Board child = new Board(pos.board);
                child.drop(move, 2);
                eval=min(eval, minimax(child, true, depth-1));
                if(round(eval)==-1) {
                    return -1;
                }
            }
        }
        return eval;
    }
}
