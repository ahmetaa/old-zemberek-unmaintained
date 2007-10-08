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
 *   Serkan Kaba
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.islemler;

import net.zemberek.TemelTest;
import net.zemberek.TestUtils;
import net.zemberek.bilgi.kokler.KokAdayiBulucu;
import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.islemler.cozumleme.CozumlemeSeviyesi;
import net.zemberek.islemler.cozumleme.KesinHDKiyaslayici;
import net.zemberek.islemler.cozumleme.StandartCozumleyici;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.*;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.ek.Ek;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 */
public class TestKelimeUretici extends TemelTest {

    private KelimeUretici kelimeUretici;
    private StandartCozumleyici cozumleyici;
    private Sozluk kokler;

    @Before
    public void once() throws IOException {
        super.once();
        kelimeUretici = new KelimeUretici(alfabe, dilBilgisi.cozumlemeYardimcisi());
        //Normal denetleyici-cozumleyici olusumu
        KokAdayiBulucu kokBulucu = dilBilgisi.kokler().kokBulucuFactory().kesinKokBulucu();
        cozumleyici = new StandartCozumleyici(kokBulucu, new KesinHDKiyaslayici(), alfabe, dilBilgisi.ekler(), dilBilgisi.cozumlemeYardimcisi());
        kokler = dilBilgisi.kokler();
    }

    private Ek ek(String ad) {
        return dilBilgisi.ekler().ek(ad);
    }

    @Test
    public void testKelimeUret() {

    Kok kok = kokler.kokBul("armut", KelimeTipi.ISIM);
    List<Ek> ekler = Arrays.asList(
            ek(ISIM_KOK),
            ek(ISIM_SAHIPLIK_BIZ_IMIZ),
            ek(ISIM_TANIMLAMA_DIR));
    assertEquals("armudumuzdur", kelimeUretici.kelimeUret(kok, ekler));

        kok = kokler.kokBul("sabret", KelimeTipi.FIIL);
        ekler = Arrays.asList(
                ek(FIIL_KOK),ek(FIIL_YETENEK_EBIL),
                ek(FIIL_GELECEKZAMAN_ECEK),ek(FIIL_KISI_BIZ));
        assertEquals("sabredebilece\u011fiz", kelimeUretici.kelimeUret(kok, ekler));
    }

    /**
     * fonksiyonel olusum testi. hepsi-dogru.txt dosyasindaki kelimeleri cozumleyip geri olusturur.
     * TODO:kisaltmalar islemiyor.
     * @throws java.io.IOException
     */
    @Ignore("Henuz kisaltmalarda islemiyor.")
    @Test
    public void testCozGeriOlustur() throws IOException {
        List<String> kelimeler = TestUtils.satirlariOku("kaynaklar/tr/test/hepsi-dogru.txt");
        for (String s : kelimeler) {
            Kelime[] cozumler = cozumleyici.cozumle(s, CozumlemeSeviyesi.TEK_KOK);
            for (Kelime kelime : cozumler) {
                String uretilen = kelimeUretici.kelimeUret(kelime.kok(), kelime.ekler());
                assertEquals("cozumlenen:" + s + ", olusan:" + uretilen + " ile ayni degil", s, uretilen);
            }
        }
    }

    @Test
    public void testEkAyristirma() {
    	String l1[] = { "kedi","le","r","im"};
    	String l2[] = { "kedi","ler","im"};
        Kelime[] cozumler = cozumleyici.cozumle("kedilerim",  CozumlemeSeviyesi.TEK_KOK);
        for (Kelime kel : cozumler) {
            if(kel.ekSayisi()==4)
              assertEquals(l1 , kelimeUretici.ayristir(kel));
            else
              assertEquals(l2 , kelimeUretici.ayristir(kel));
        }
    }


}
