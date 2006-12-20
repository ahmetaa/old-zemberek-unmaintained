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

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.List;

/**
 */
public class TestYaziIsleyici {

    @Test
    public void testAnalizDizisiOlustur() {
        String giris = "Merhaba, ben Ahmet!.  Nasilsiniz? ";
        String[] parcalar = {"Merhaba",
                             ", ",
                             "ben",
                             " ",
                             "Ahmet",
                             "!.  ",
                             "Nasilsiniz",
                             "? "};
        YaziBirimiTipi[] tipler = {YaziBirimiTipi.KELIME, //Merhaba
                                   YaziBirimiTipi.DIGER, //,
                                   YaziBirimiTipi.KELIME, //ben
                                   YaziBirimiTipi.DIGER, //
                                   YaziBirimiTipi.KELIME, //Ahmet
                                   YaziBirimiTipi.DIGER, //!.
                                   YaziBirimiTipi.KELIME, //Nasilsiniz
                                   YaziBirimiTipi.DIGER //?
        };
        List analizDizisi = YaziIsleyici.analizDizisiOlustur(giris);
        assertEquals(analizDizisi.size(), parcalar.length);
        for (int i = 0; i < parcalar.length; i++) {
            YaziBirimi birim = (YaziBirimi) analizDizisi.get(i);
            assertEquals(parcalar[i], birim.icerik);
            //assertEquals(tipler[i],birim.tip);
            assertEquals("hatali" + i + ":", tipler[i], birim.tip);

        }
    }

    @Test
    public void testKelimeAyikla() {
        String giris = "Merhaba, ben Ahmet!.  Nasilsiniz? ";
        String[] parcalar = {"Merhaba",
                             "ben",
                             "Ahmet",
                             "Nasilsiniz",
        };
        List kelimeler = YaziIsleyici.kelimeAyikla(giris);
        assertEquals(kelimeler.size(), parcalar.length);
        for (int i = 0; i < parcalar.length; i++) {
            String birim = (String) kelimeler.get(i);
            assertEquals(parcalar[i], birim);

        }


    }

    @Test
    public void testAnalizIcinKelimeAyikla() {
        String giris = "Merhaba, ben Ahmet!.  Nasilsiniz? ";
        String[] parcalar = {"ben"
        };
        List kelimeler = YaziIsleyici.analizIcinKelimeAyikla(giris);

        assertEquals(kelimeler.size(), parcalar.length);
        for (int i = 0; i < parcalar.length; i++) {
            String birim = (String) kelimeler.get(i);
            assertEquals(parcalar[i], birim);

        }


    }

    @Test
    public void testCumleAyikla() {
        String giris = "Merhaba, ben Ahmet!. Nasilsiniz?";
        String[] cumle = {"Merhaba, ben Ahmet!.",
                          "Nasilsiniz?"};
        List cumleler = YaziIsleyici.cumleAyikla(giris);
        assertEquals(cumleler.size(), 2);
        for (int i = 0; i < cumle.length; i++) {
            String birim = (String) cumleler.get(i);
            assertEquals(cumle[i], birim.trim());

        }
    }


}
