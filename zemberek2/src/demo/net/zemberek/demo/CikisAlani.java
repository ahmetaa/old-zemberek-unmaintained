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
 *  The Original Code is "Zemberek Dogal Dil Isleme Kutuphanesi"
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akin, Mehmet D. Akin.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.demo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 */
public class CikisAlani {
    private JPanel mainPanel;
    private JTextArea outputArea;

    public CikisAlani() {
        configure();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setYazi(String yazi) {
        outputArea.setText(yazi);
    }

    public void configure() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(makeInputPanel(), BorderLayout.CENTER);
        mainPanel.setBorder(new TitledBorder(new EmptyBorder(2,2,2,2), "\u00c7\u0131k\u0131\u015f alan\u0131"));
    }


    public JPanel makeInputPanel() {
        JPanel pq = new JPanel(new BorderLayout());
        outputArea = new JTextArea();
        outputArea.setBorder(new EmptyBorder(1, 3, 1, 3));
        outputArea.setLineWrap(true);
        outputArea.setEditable(false);

        // jTextPanel scroll bar icermez. O yuzden onu scroll pane'e ekliyoruz.
        JScrollPane ps = new JScrollPane();
        ps.getViewport().add(outputArea);

        pq.add(ps,BorderLayout.CENTER);
        return pq;
    }
}
