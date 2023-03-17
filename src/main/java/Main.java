import connect4.Board;

public class Main {
    public static void main(String[] args) throws Exception {
        Board pos = new Board();
        pos.play(1);
        pos.play(2);
        pos.show();
    }
}
