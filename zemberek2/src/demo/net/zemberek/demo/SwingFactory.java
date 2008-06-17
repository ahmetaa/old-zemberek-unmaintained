/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.demo;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

/**
 */
public class SwingFactory {
    protected static Color background = new Color(245, 245, 255);

    public SwingFactory() {
    }

    public static JButton getRegularButton(String lbl) {
        JButton button = new JButton(lbl);
        button.setFont(new Font("Tahoma", Font.PLAIN, 11));
        return button;
    }

    /*
    public static JButton getWrapButton(String lbl) {
        String html = "<html><center>";
        String newLbl = new String(html + lbl);
        return getRegularButton(newLbl);
    }

    public static JButton getRegularButton(String lbl, int sizex, int sizey, int fontSize) {
        JButton regButton = getRegularButton(lbl);
        regButton.setFont(new Font("Arial", Font.PLAIN, fontSize));
        regButton.setSize(sizex, sizey);
        return regButton;
    }
    */
}
