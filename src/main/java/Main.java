public class Main {
    public static void main(String[] args) throws Exception {
        Board board = new Board();
        board.drop(0,2);
        board.drop(0,2);
        board.drop(0,2);
        board.drop(2,2);
        System.out.println(board.situation());
        board.show();
    }
}
