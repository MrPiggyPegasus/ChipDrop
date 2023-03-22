/*
    Copyright (c) 2023. "MrPiggyPegasus"
   Permission is hereby granted, free of charge, to any person obtaining a copy
   of this software and associated documentation files (the "Software"), to deal
   in the Software without restriction, including without limitation the rights
   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
   copies of the Software, and to permit persons to whom the Software is
   furnished to do so, subject to the following conditions:

   The above copyright notice and this permission notice shall be included in all
   copies or substantial portions of the Software.

   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
   FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
   SOFTWARE.
 */

package gui;

import connect4.Board;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    public String pgn = "023631550123";

    public int bestMove = 3;
    public JPanel game;

    public GamePanel() {
        game = new JPanel() {
            public void paint(Graphics gs) {
                Graphics2D g = (Graphics2D) gs;
                g.setPaint(Color.GRAY);
                g.fillRect(20,20,350,300);
                Board board = new Board(pgn);
                for (int row = 0; row < 6; row++) { // display counters on the board
                    for (int col = 0; col < 7; col++) {
                        if (board.board[row][col] == 0) {
                            g.setPaint(Color.DARK_GRAY);
                            g.fillOval(50 * col+20, 50 * row+20, 30, 30);
                        } else if (board.board[row][col] == 1) {
                            g.setPaint(Color.RED);
                            g.fillOval(50 * col+20, 50 * row+20, 30, 30);
                            g.setPaint(Color.BLACK);
                            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
                            g.drawString("1", 50*col+30, 50*row+43);
                        } else if (board.board[row][col] == -1) {
                            g.setPaint(Color.YELLOW);
                            g.fillOval(50 * col+20, 50 * row+20, 30, 30);
                            g.setPaint(Color.BLACK);
                            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
                            g.drawString("2", 50*col+30, 50*row+43);
                        }
                    }
                }
                if (bestMove != 9) { // bestMove=9 if the best move hasn't been computed yet
                    if(board.player != 0) {
                        g.setPaint(Color.GREEN);
                        g.fillOval(50 * bestMove+20,
                                   50 * board.highestTokenAtCol(bestMove)+20,
                                   30,
                                   30);
                    }
                }
            }
        };
        game.setBounds(20,20,360,310);
        for (int i=0; i<7; i++) {
            JLabel label = new JLabel();
            label.setText(String.valueOf(i));
            label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
            label.setForeground(Color.BLACK);
            label.setBounds(50*i+50, 0, 25,25);
            add(label);
        }
        for (int i=0; i<6; i++) {
            JLabel label = new JLabel();
            label.setText(String.valueOf(i));
            label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
            label.setForeground(Color.BLACK);
            label.setBounds(10, 50*i+40, 25,25);
            add(label);
        }
        setBackground(Color.GRAY);
        setLayout(null);
        add(game);
        setBounds(0,0,380,330);
        setLocation(0,0);
        setVisible(true);
    }
}
