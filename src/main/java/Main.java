import connect4.Board;
public class Main {
    public static void main(String[] args) throws Exception {
        Board board = new Board();
        System.out.println(board.bestMove(1));
    }
}
