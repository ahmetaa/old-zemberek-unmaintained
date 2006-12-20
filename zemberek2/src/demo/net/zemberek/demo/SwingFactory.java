/*
 *  ***** BEGIN LICENSE BLOCK *****
 *
 *  Version: MPL 1.1
 *
 *  The contents of this file are subject to the Mozilla Public License Version
 *  1.1 (the "License"); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an "AS IS" basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 *  for the specific language governing rights and limitations under the
 *  License.
 *
 *  The Original Code is Zemberek Do?al Dil ??leme Kütüphanesi.
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Ak?n, Mehmet D. Ak?n.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.demo;

import javax.swing.*;
import java.awt.*;

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
}
