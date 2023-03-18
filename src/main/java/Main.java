import connect4.Board;

public class Main {
    public static void main(String[] args) throws Exception {
        // placeholder code to demo win checking with .situation
        Board pos = new Board();
//        pos.play(5);
        pos.play(3);
        pos.play(1);
        pos.play(2);
        pos.play(2);
        pos.play(3);
        pos.play(3);
        pos.play(4);
        pos.play(4);
        pos.play(4);
        pos.play(4);
        pos.show();
        System.out.println(pos.situation());
        switch(pos.situation()) {
            case(Board.PLAYER_1_WON) -> System.out.println("PLAYER 1 WON!");
            case(Board.PLAYER_2_WON) -> System.out.println("Player 2 won!");
            case(Board.DRAW) -> System.out.println("Draw!");
        }
    }
}
