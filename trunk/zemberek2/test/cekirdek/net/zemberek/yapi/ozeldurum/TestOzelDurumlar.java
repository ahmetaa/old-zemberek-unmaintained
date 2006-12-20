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

package net.zemberek.yapi.ozeldurum;

import net.zemberek.TemelTest;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.kok.KokOzelDurumBilgisi;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;

import java.io.IOException;

/**
 */
public class TestOzelDurumlar extends TemelTest {

   KokOzelDurumBilgisi koz ;

    @Before
    public void once() throws IOException {
        super.once();
        koz = dilBilgisi.kokOzelDurumlari();
    }

    @Before
    public void testYumusama() {
        HarfDizisi dizi = hd("kitap");
        dilBilgisi.kokOzelDurumlari().ozelDurum("YUM").uygula(dizi);
        assertEquals("kitab", dizi.toString());
    }

    @Before
    public void testCiftleme() {
        HarfDizisi dizi =hd("hat");
         dilBilgisi.kokOzelDurumlari().ozelDurum("CIFT").uygula(dizi);
        assertEquals("hatt", dizi.toString());
    }

    @Before
    public void testAraSesliDusmesi() {
        HarfDizisi dizi = hd("burun");
         dilBilgisi.kokOzelDurumlari().ozelDurum("DUS").uygula(dizi);
        assertEquals("burn", dizi.toString());
    }

    @Before
    public void testKokDegisimleri() {
        Kok kok= new Kok("bahset", KelimeTipi.FIIL);
        kok.ozelDurumEkle(koz.ozelDurum("GEN"));
        kok.ozelDurumEkle(koz.ozelDurum("YUM"));
        String[] sonuclar = koz.ozelDurumUygula(kok);
        assertTrue(sonuclar.length==1);
        assertEquals(sonuclar[0], "bahsed");
    }


}
