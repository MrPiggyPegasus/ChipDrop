/*
 * Copyright (c) 2023. "MrPiggyPegasus"
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package gui;

import connect4.Board;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PGNPopup extends JFrame {
    public static boolean active;
    public PGNPopup() {
        if(active) {
            dispose();
            return;
        }
        active = true;
        setLayout(new FlowLayout(FlowLayout.CENTER, 100,0));
        setSize(250,200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(MainFrame.gamePanel);
        setVisible(true);

        JTextArea field = new JTextArea();
        field.setBackground(Color.LIGHT_GRAY);
        field.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        field.setSize(new Dimension(150,100));
        field.setRows(3);
        field.setLineWrap(true);
        field.setBorder(new BevelBorder(BevelBorder.LOWERED));
        add(field);

        JButton submitButton = new JButton("OK");
        submitButton.setBackground(Color.LIGHT_GRAY);
        submitButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        submitButton.setPreferredSize(new Dimension(100,20));
        submitButton.addActionListener(e -> {
            String txt = field.getText().replace("\n", "");
            if(Board.isValidPgn(txt)) {
                MainFrame.gamePanel.resetToPGN(txt);
                active = false;
                dispose();
            }
        });
        add(submitButton);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                active = false;
                super.windowClosing(e);
            }
        });
    }
}
