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
