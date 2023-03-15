import connect4.Board;
import engine.Engine;

public class Main {
    public static void main(String[] args) throws Exception {
        Board board = new Board();
        board.drop(3,2);
        board.drop(3,2);
        board.drop(3,2);

        board.drop(5,1);
        board.drop(5,1);
        // so i uhh think this works for player 1
        System.out.println(Engine.bestMove(board, 1));

    }
}
