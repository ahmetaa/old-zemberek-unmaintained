package net.zemberek.demo;

import javax.swing.*;
import java.awt.*;

/**
 */
public class MainFrame extends JFrame {
    private JComponent shownContent = null;

    public MainFrame() {
        configureUI();
    }

    private void configureUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container cp = getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
        setTitle("Zemberek Demo");
    }

    public void setContent(JPanel panel) {
        Container cp = getContentPane();
        if (shownContent != null) {
            cp.remove(shownContent);
        }
        cp.add(panel);
        cp.validate();
        setSize(750, 500);
        //pack();
        setVisible(true);
        shownContent = panel;
    }
}
