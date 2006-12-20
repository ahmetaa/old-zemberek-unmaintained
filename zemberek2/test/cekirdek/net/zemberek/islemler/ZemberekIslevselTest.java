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

import net.zemberek.TemelTest;
import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * User: ahmet
 * Date: Dec 10, 2005
 */
public class ZemberekIslevselTest extends TemelTest {

    Zemberek zemberek = new Zemberek(new TurkiyeTurkcesi());

    @Test
    public void testAsciidenDonusturucuSiralama() {
        //asciiden donustur.
        String giris = "cok";
        String[] sonuclar = zemberek.asciidenTurkceye(giris);
        //essesliler eleneceginden iki sonuc donmeli
        assertTrue(sonuclar.length == 2);
        //kok kullanim frekansina gore siralanmis olmasi gerekir.
        assertEquals(sonuclar[0], "\u00e7ok");
        assertEquals(sonuclar[1], "\u00e7\u00f6k");
    }

    @Test
    public void tesAsciiDonucturucu() {
        String giris = "Ibrik";
        String[] sonuclar = zemberek.asciidenTurkceye(giris);
        assertTrue(sonuclar.length == 1);
        assertEquals(sonuclar.length, "ibrik");
        giris = "Irmak";
        sonuclar = zemberek.asciidenTurkceye(giris);
        assertTrue(sonuclar.length == 1);
        assertEquals(sonuclar[0], "\u0131rmak");
    }

    @Test
    public void testKokEkle()
    {
        assertFalse(zemberek.kelimeDenetle("zembenklik"));
        Sozluk kokler = zemberek.dilBilgisi().kokler();
        Kok kok = new Kok("zembenk", KelimeTipi.ISIM);
        kok.ozelDurumEkle(dilBilgisi.kokOzelDurumlari().ozelDurum(TurkceKokOzelDurumTipleri.SESSIZ_YUMUSAMASI_NK));
        kokler.ekle(kok);
        assertTrue(zemberek.kelimeDenetle("zembenklik"));
        assertTrue(zemberek.kelimeDenetle("zembenge"));
    }

}
