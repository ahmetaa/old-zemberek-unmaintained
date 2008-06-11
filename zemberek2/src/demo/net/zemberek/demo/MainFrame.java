/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.demo;

import javax.swing.*;
import java.awt.*;

/**
 */
public class MainFrame extends JFrame {
    private JPanel shownContent = null;

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
        setPreferredSize(new Dimension(640, 480));
        setLocation(100,100);
        pack();
        setVisible(true);
        shownContent = panel;
    }
}
