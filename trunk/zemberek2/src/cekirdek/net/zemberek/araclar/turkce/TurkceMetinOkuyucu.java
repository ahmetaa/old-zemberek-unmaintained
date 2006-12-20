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

package net.zemberek.araclar.turkce;

import net.zemberek.istatistik.Istatistikler;

import java.util.ArrayList;

/**
 */
public class TurkceMetinOkuyucu {
    private Istatistikler istatistikler = null;

    public String[] MetinOku(String path) {
        String[] kelimeler;
        TurkishTokenStream stream = new TurkishTokenStream(path, "ISO-8859-9");
        if (istatistikler != null) {
            stream.setStatistics(istatistikler);
        }
        ArrayList list = new ArrayList();
        while (true) {
            String str = stream.nextWord();
            if (str == null) break;
            list.add(str);
        }
        System.out.println(" Metin kelime sayisi: " + list.size());
        kelimeler = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            kelimeler[i] = (String) list.get(i);
        }
        return kelimeler;
    }

    public void setStatistics(Istatistikler istatistikler) {
        this.istatistikler = istatistikler;
    }
}
