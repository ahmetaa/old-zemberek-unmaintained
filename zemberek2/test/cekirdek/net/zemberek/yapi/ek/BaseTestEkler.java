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

package net.zemberek.yapi.ek;

import net.zemberek.TemelTest;
import net.zemberek.islemler.cozumleme.KesinHDKiyaslayici;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * User: aakin
 * Date: Feb 24, 2004
 */
public class BaseTestEkler extends TemelTest {

    protected Kelime[] kelimeler;

    protected void olusanEkKontrol(String[] strs, String[] gercek, Ek ek) {
        String[] olusanEkler;
        kelimeleriOlustur(strs);
        olusanEkler = ekleriOlustur(ek);
        for (int i = 0; i < gercek.length; i++) {
            assertEquals("Hatali olusum:" + olusanEkler[i], olusanEkler[i], gercek[i]);
        }
    }

    protected void kelimeleriOlustur(String[] strs) {
        kelimeler = new Kelime[strs.length];
        for (int i = 0; i < strs.length; i++) {
            kelimeler[i] = new Kelime(new Kok(strs[i], KelimeTipi.FIIL), alfabe);
        }
    }

    protected String[] ekleriOlustur(Ek ek) {
        String[] olusan = new String[kelimeler.length];
        for (int i = 0; i < kelimeler.length; i++) {
            olusan[i] = ek.cozumlemeIcinUret(kelimeler[i], kelimeler[i].icerik(),
                    new KesinHDKiyaslayici()).toString();
        }
        return olusan;
    }

    public void testEmpty() {
        assertTrue(true);
    }
}
