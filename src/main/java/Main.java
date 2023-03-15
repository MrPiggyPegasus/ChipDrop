public class Main {
    public static void main(String[] args) throws Exception {
        Board board = new Board();
        board.show();
        System.out.println(Engine.minimax(board, false, 10));
    }
}
