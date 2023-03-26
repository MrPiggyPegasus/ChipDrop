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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamePanel extends JPanel implements MouseListener {
    public Board pos;
    public int bestMove;
    public boolean gameOver = false;
    public BestMoveSubject sub;

    public GamePanel() {
        sub = new BestMoveSubject();
        pos = new Board("");
        findBestMove();
        bestMove = 9;
        addMouseListener(this);
        setBackground(Color.GRAY);
        setLayout(null);
        setPreferredSize(new Dimension(370,320));
        setLocation(0,0);
        setVisible(true);
    }

    public void updateBestMove(BestMoveSubject subject) {
        if(subject!=sub) return;
        bestMove = sub.bestMove;
        repaint();
    }

    void findBestMove() {
        sub.cancel();
        sub = new BestMoveSubject();
        sub.subscribe(this);
        sub.findMove(pos);
    }

    void gameOver() {
        sub.cancel();
        gameOver = true;
        bestMove = 9;
        repaint();
    }

    void resetToPGN(String pgn) {
        sub.cancel();
        pos = new Board(pgn);
        findBestMove();
        repaint();
    }

    void playMove(int move) {
        if(!gameOver) {
            findBestMove();
            pos.play(move);
            SettingsPanel.updatePGN(pos.pgn);
            if (!pos.isInPlay()) {
                gameOver();
                return;
            }
            bestMove = 9;
            repaint();
            findBestMove();
        }
    }

    @Override
    public void paint(Graphics gs) {
        Graphics2D g = (Graphics2D) gs;
        g.setPaint(Color.GRAY);
        g.fillRect(0,0,370,320);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        for (int col = 0; col < 7; col++) {
            // display counters on board
            for (int row = 0; row < 6; row++) {
                switch (pos.board[row][col]) {
                    case 0 -> {
                        g.setPaint(Color.DARK_GRAY);
                        g.fillOval(50 * col + 20, 50 * row + 20, 30, 30);
                    }
                    case 1 -> {
                        g.setPaint(Color.RED);
                        g.fillOval(50 * col + 20, 50 * row + 20, 30, 30);
                        g.setPaint(Color.BLACK);
                        g.drawString("1", 50 * col + 30, 50 * row + 43);
                    }
                    case -1 -> {
                        g.setPaint(Color.YELLOW);
                        g.fillOval(50 * col + 20, 50 * row + 20, 30, 30);
                        g.setPaint(Color.BLACK);
                        g.drawString("2", 50 * col + 30, 50 * row + 43);
                    }
                }
            }
            // mark each column with its index
            g.setPaint(Color.BLACK);
            g.drawString(String.valueOf(col), 50 * col+30, 17);
        }
        // highlights the best move in green
        // bestMove=9 if the best move hasn't been computed yet
        if(bestMove != 9) {
            if(pos.player == 1 && SettingsPanel.showP1BestMove) {
                g.setPaint(Color.GREEN);
                g.fillOval(50 * bestMove + 20,
                        50 * (pos.highestTokenAtCol(bestMove) - 1) + 20,
                        30,
                        30);
                g.setPaint(Color.BLACK);
                g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
                g.drawString("1", 50 * bestMove + 30, 50 * (pos.highestTokenAtCol(bestMove) - 1) + 43);
            } else if(pos.player == -1 && SettingsPanel.showP2BestMove) {
                g.setPaint(Color.GREEN);
                g.fillOval(50 * bestMove + 20,
                        50 * (pos.highestTokenAtCol(bestMove) - 1) + 20,
                        30,
                        30);
                g.setPaint(Color.BLACK);
                g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
                g.drawString("2", 50 * bestMove + 30, 50 * (pos.highestTokenAtCol(bestMove) - 1) + 43);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // checks if the mouse is over a column
        if(pos.isInPlay()) {
            double x = e.getX();
            double y = e.getY();
            if ((x < 20 && y < 20) || (x > 370 && y > 320)) {
                return;
            }
            x += 20;
            for (int col = 0; col < 7; col++) {
                if (x < ((col * 50) + 80) && pos.isLegal(col)) {
                    playMove(col);
                    return;
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
