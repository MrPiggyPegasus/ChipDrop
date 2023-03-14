public class Main {
    public static void main(String[] args) throws Exception {
        Board board = new Board();
        board.drop(3,1);
        board.drop(3,2);
        board.drop(3,1);
        board.drop(3,1);

        board.drop(2,2);
        board.drop(2,1);
        board.drop(2,1);

        board.drop(1,2);
        board.drop(1,1);

        board.drop(0,1);
        board.show();
        System.out.println(board.situation());
    }
}
