import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        Board board = new Board();
        board.drop(1,1);
        board.drop(1,1);
        board.drop(1,1);
        board.drop(1,2);
        board.drop(1,1);
        System.out.println(Arrays.toString(board.legalMoves()));
    }
}
