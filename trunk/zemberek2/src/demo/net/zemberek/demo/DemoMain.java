/*
 *  ***** BEGIN LICENSE BLOCK *****
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
 *  The Original Code is Zemberek Doðal Dil Ýþleme Kütüphanesi.
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akýn, Mehmet D. Akýn.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.demo;

import net.zemberek.araclar.turkce.YaziIsleyici;

import java.io.File;
import java.io.IOException;
/**
 */
public class DemoMain {

    private MainFrame mainFrame = new MainFrame();

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public static void main(String[] args) {
        DemoMain demoMain = new DemoMain();
        String s = "";
        if (args.length > 0) {
            String fileName = args[0];
            File file = new File(fileName);
            if (!file.exists()) {
                System.out.println(fileName + " adli dosya bulunamadi..");
            } else {
                try {
                    s = YaziIsleyici.yaziOkuyucu(fileName);
                } catch (IOException e) {
                    System.out.println("Dosya erisiminde sorun var.");
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
        DemoPaneli demoPaneli = new DemoPaneli(new DemoYonetici());
        demoPaneli.yaziEkle(s);
        demoMain.getMainFrame().setContent(demoPaneli.getMainPanel());
    }
}