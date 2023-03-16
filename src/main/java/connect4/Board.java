package connect4;

import engine.Engine;

public class Board {
    public int movesPlayed;
    public int[][] board = new int[5][6];
    public Board() {
        for(int i=0; i< 5; i++) {
            for(int j=0; j<6; j++) {
                board[i][j] = 0;
            }
        }
    }
    public Board(int[][] pos) {
        for(int row=0; row<5; row++) {
            for(int col=0; col<6; col++) {
                if(pos[row][col]!=0) {
                    this.movesPlayed++;
                    this.board[row][col]=pos[row][col];
                }
            }
        }
    }
    public void show() {
        System.out.println("| 1  2  3  4  5  6 |");
        System.out.println("--------------------");
        for(int row=0; row<5; row++) {
            System.out.print("| ");
            for(int col=0; col<6; col++) {
                if(col!=5) {
                    switch (this.board[row][col]) {
                        case (0) -> System.out.print(".  ");
                        case (1) -> System.out.print("1  ");
                        case (2) -> System.out.print("2  ");
                    }
                } else {
                    switch (this.board[row][col]) {
                        case (0) -> System.out.print(". ");
                        case (1) -> System.out.print("1 ");
                        case (2) -> System.out.print("2 ");
                    }
                }
            }
            System.out.println("|");
        }
        System.out.println("--------------------");
    }
    public void drop(int location, int player) throws Exception {
        if(player!=1 && player!=2) {
            throw new IllegalPlayerArgumentException();
        }
        if(location<0 || location > 5) {
            throw new MoveOutOfBoundsException();
        }
        for(int i=4; i>=0; i--) {
            if(board[i][location]==0) {
                board[i][location] = player;
                return;
            }
        }
        throw new IllegalMoveException("Move: " + location + " is illegal.");
    }
    public boolean isLegal(int move) {
        if(move<0 || move > 5) {
            return false;
        }
        for(int i=4; i>=0; i--) {
            if(board[i][move]==0) {
                return true;
            }
        }
        return false;
    }
    public int[] legalMoves() {
        int[] legalMoves = new int[6];
        int c=0;
        for(int i=0; i<6; i++) {
            if(this.isLegal(i)) {
                legalMoves[c] = i;
                c++;
            }
        }
        int[] returnArray = new int[c];
        System.arraycopy(legalMoves, 0, returnArray, 0, c);
        return returnArray;
    }
    public boolean isWinning(int move, int player) {
        Board child = new Board(this.board);
        try {
            child.drop(move, player);
            int sit=child.situation();
            if(sit!=0 && sit!=2) {
                return true;
            }
        } catch (Exception ignore) {}
        return false;
    }
    public boolean isOver() {
        return this.situation() != 2;
    }
    public int situation() {
        // check vertical wins
        int player = 1;
        int consec = 0;
        for(int column=0; column<6; column++) {
            for(int row=4; row>=0; row--) {
                if (board[row][column]==0) {
                    consec=0;
                    continue;
                }
                if(board[row][column]==player) {
                    consec++;
                } else {
                    consec = 1;
                    player = board[row][column];
                }
                if(consec==4) {
                    if(player==1) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
            consec=0;
        }
        // check horizontal wins
        for(int row=4; row>=0; row--) {
            for(int column=0; column<6; column++) {
                if(consec==4) {
                    if(player==1) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
                if(player==board[row][column]) {
                    consec++;
                } else if(board[row][column]==0) {
                    consec=0;
                } else {
                    player=board[row][column];
                    consec=1;
                }
            }
            consec=0;
        }
        /* check \ facing diagonal wins
                  \
                   \
        */
        for(int row=0; row<2; row++) {
            for(int column=0; column<2; column++) {
                for(int shift=0; shift<4; shift++) {
                    if(board[row+shift][column+shift]==player) {
                        consec++;
                    } else if(board[row+shift][column+shift]==0) {
                        consec=0;
                    } else {
                        player=board[row+shift][column+shift];
                        consec=1;
                    }
                }
                if(consec==4) {
                    if(player==1) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            consec=0;
            }
        }
        /* check / facing diagonal wins
                /
               /
        */
        for(int row=4; row>=3; row--) {
            for(int column=0; column<2; column++) {
                for(int shift=0; shift<4; shift++) {
                    if(board[row-shift][column+shift]==player) {
                        consec++;
                    } else if(board[row-shift][column+shift]==0) {
                        consec=0;
                    } else {
                        player=board[row-shift][column+shift];
                        consec=1;
                    }
                    if(consec==4) {
                        if(player==1) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                }
            }
        }

        if(this.isDraw()) {
            return 0;
        }
        return 2;
    }
    public boolean isDraw() {
        return this.movesPlayed == 30; // if all slots are filled
    }

    public int bestMove(int player) throws Exception {
        int bestMove = Engine.bestMove(this, player);
        if(bestMove==9) {
            throw new PositionAlreadyOverException();
        } else {
            return bestMove;
        }
    }
}
class MoveOutOfBoundsException extends Exception {
    public MoveOutOfBoundsException() {
        super("Move index out of bounds. Must be between 0 and 5 inclusive.");
    }
}
class IllegalPlayerArgumentException extends Exception {
    public IllegalPlayerArgumentException() {
        super("Player value must be either 1 or 2.");
    }
}
class IllegalMoveException extends Exception {
    public IllegalMoveException(String msg) {
        super(msg);
    }
}
class PositionAlreadyOverException extends Exception {
    public PositionAlreadyOverException() {
        super("The entered position is already over.");
    }
}
