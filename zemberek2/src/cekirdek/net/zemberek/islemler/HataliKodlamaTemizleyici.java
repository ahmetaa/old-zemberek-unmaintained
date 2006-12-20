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

package net.zemberek.islemler;

import net.zemberek.bilgi.KaynakYukleyici;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 */
public class HataliKodlamaTemizleyici {
    public static Logger log = Logger.getLogger("HataliKodlamaTemizleyici.class");
    Map donusumler = new HashMap();

    public void initialize() throws IOException {
        //kodlama karsiliklari dosyasini oku
        BufferedReader reader = new KaynakYukleyici().getReader("kaynaklar/tr/bilgi/kodlama-donusum.txt");
        String s;
        while ((s = reader.readLine()) != null) {
            // bos ve # isaretli satirlari atla
            if (s.length() == 0 || s.charAt(0) == '#')
                continue;
            s = toNative(s);
            Character c = s.charAt(0);

            //satirdan turkce karaktere karsilik duzen kod cekiliyor. ve bu kod map'a yerletiriliyor
            String kod = s.substring(2);
            if (donusumler.containsKey(c)) {
                List a = (List) donusumler.get(c);
                a.add(kod);
            } else {
                List yeni = new ArrayList();
                yeni.add(kod);
                donusumler.put(c, yeni);
            }
        }
    }

    /**
     * Biraz balta bir metodla kodlara karsilik dusulen
     *
     * @param giris
     * @return String'in temizlenmis hali.
     */
    public String temizle(String giris) {
        //keys icerisinde turkce karakterler yer aliyor
        Set keys = donusumler.keySet();
        for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
            Character karakter = (Character) iterator.next();
            //values icinde "karakter"e karsilik dusen karakter dizileri bulunuyor
            List values = (List) donusumler.get(karakter);
            for (int i = 0; i < values.size(); i++) {
                String kod = (String) values.get(i);
                //asagidaki kod efektif degil ama is gorur. caktirmadan regexp kullaniyor.
                giris = giris.replaceAll(kod, karakter.toString());
            }
        }
        return giris;
    }

    /**
     * odun bir yontemle "uxxxx" seklindeki string'ler karakter karsiliklarina
     *
     * @param str
     * @return unicode karsilik.
     */
    private String toNative(String str) {
        boolean end = false;
        StringBuffer yeni = new StringBuffer();
        while (true) {
            if (str.startsWith("\\u")) {
                char c = (char) Integer.parseInt(str.substring(2, 6), 16);
                yeni.append(c);
                str = str.substring(6);
            } else {
                if (str.length() == 0)
                    break;
                yeni.append(str.charAt(0));
                str = str.substring(1);
            }
        }
        return yeni.toString();
    }
}


