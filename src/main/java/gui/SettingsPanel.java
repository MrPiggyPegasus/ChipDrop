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

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ItemEvent;

public class SettingsPanel extends JPanel {
    public static boolean showP2BestMove = false;
    public static boolean showP1BestMove = false;
    public GamePanel target;
    public static JTextArea pgnLabel;
    public SettingsPanel(GamePanel target) {
        this.target = target;
        setPreferredSize(new Dimension(350,320));
        setBackground(Color.LIGHT_GRAY);
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        setLayout(new FlowLayout(FlowLayout.LEFT, 20,20));

        pgnLabel = new JTextArea();
        updatePGN("");
        pgnLabel.setLineWrap(true);
        pgnLabel.setFocusable(false);
        pgnLabel.setEditable(false);
        pgnLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
        super.update(getGraphics());

        JScrollPane pgnPane = new JScrollPane(pgnLabel);
        pgnPane.setHorizontalScrollBar(null);
        pgnPane.setPreferredSize(new Dimension(100,80));
        add(pgnPane);

        JCheckBox p1CheckBox = new JCheckBox("Show best move for player 1");
        p1CheckBox.setBackground(Color.LIGHT_GRAY);
        p1CheckBox.setPreferredSize(new Dimension(300,20));
        p1CheckBox.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        p1CheckBox.addItemListener(e -> {
            showP1BestMove = e.getStateChange() == ItemEvent.SELECTED;
            MainFrame.gamePanel.repaint();
        });
        add(p1CheckBox);

        JCheckBox p2CheckBox = new JCheckBox("Show best move for player 2");
        p2CheckBox.setBackground(Color.LIGHT_GRAY);
        p2CheckBox.setPreferredSize(new Dimension(300,20));
        p2CheckBox.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        p2CheckBox.addItemListener(e -> {
            showP2BestMove = e.getStateChange() == ItemEvent.SELECTED;
            MainFrame.gamePanel.repaint();
        });
        add(p2CheckBox);
    }

    public static void updatePGN(String pgn) {
        pgnLabel.setText("PGN: " + pgn);
    }
}
