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

package net.zemberek.bilgi.kokler;

import net.zemberek.TemelTest;
import net.zemberek.araclar.TimeTracker;
import net.zemberek.bilgi.araclar.DuzYaziKokOkuyucu;
import net.zemberek.bilgi.araclar.KokOkuyucu;
import net.zemberek.bilgi.araclar.IkiliKokOkuyucu;
import net.zemberek.yapi.Kok;
import static org.junit.Assert.*;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 */
public class TestTreeSozluk extends TemelTest {

    KokOkuyucu okuyucu;
    Sozluk sozluk = null;

    public void once() throws IOException {
        super.once();
        okuyucu = getDuzyaziOkuyucu("kaynaklar/tr/test/test-sozluk.txt");
        sozluk = new AgacSozluk(okuyucu, alfabe, dilBilgisi.kokOzelDurumlari());
    }

    private KokOkuyucu getDuzyaziOkuyucu(String dosya) throws IOException {
        return new DuzYaziKokOkuyucu(
                dosya,
                dilBilgisi.kokOzelDurumlari(),
                alfabe,
                dilAyarlari.kokTipiAdlari());
    }

    private KokOkuyucu getIkiliOkuyucu(String dosya) throws IOException {
        return new IkiliKokOkuyucu(
                dosya,
                dilBilgisi.kokOzelDurumlari());
    }

    @Test
    public void testHatasizlik_binary() throws IOException {
        TimeTracker.startClock("x");
        KokOkuyucu okuyucu1 = getIkiliOkuyucu("kaynaklar/tr/bilgi/kokler_tr.bin");
        AgacSozluk testSozluk = new AgacSozluk(okuyucu1, alfabe, dilBilgisi.kokOzelDurumlari());
        System.out.println("Time: " + TimeTracker.getElapsedTimeString("x"));
        TimeTracker.stopClock("x");
        KokOkuyucu okuyucu2 = getIkiliOkuyucu("kaynaklar/tr/bilgi/kokler_tr.bin");
        List<Kok> list = okuyucu2.hepsiniOku();
        for (Kok kok : list) {
            if (testSozluk.kokBul(kok.icerik()) == null) {
                fail("Kelime a�a�ta bulunamad�: " + kok.icerik());
                return;
            }
        }
    }

    @Test
    public void testHatasizlik_() throws IOException {
        KokOkuyucu okuyucu1 = getDuzyaziOkuyucu("kaynaklar/tr/test/agactest-1.txt");
        AgacSozluk testSozluk = new AgacSozluk(okuyucu1, alfabe, dilBilgisi.kokOzelDurumlari());
        KokOkuyucu okuyucu2 = getDuzyaziOkuyucu("kaynaklar/tr/test/agactest-1.txt");
        List<Kok> list = okuyucu2.hepsiniOku();
        for (Kok kok : list) {
            if (testSozluk.kokBul(kok.icerik()) == null) {
                fail("Kelime a�a�ta bulunamad�: " + kok.icerik());
            }
        }
        Collection kokler = testSozluk.kokBul("imren");
        assertEquals(2, kokler.size());
    }

    @Test
    public void testHatasizlik() throws IOException {
        KokOkuyucu okuyucu1 = getDuzyaziOkuyucu("kaynaklar/tr/bilgi/duzyazi-kilavuz.txt");
        AgacSozluk testSozluk = new AgacSozluk(okuyucu1, alfabe, dilBilgisi.kokOzelDurumlari());
        KokOkuyucu okuyucu2 = getDuzyaziOkuyucu("kaynaklar/tr/bilgi/duzyazi-kilavuz.txt");
        List<Kok> list = okuyucu2.hepsiniOku();
        for (Kok kok : list) {
            if (testSozluk.kokBul(kok.icerik()) == null) {
                fail("Kelime a�a�ta bulunamad�: " + kok.icerik());
            }
        }
        List kokler = testSozluk.kokBul("usul");
        assertEquals(2, kokler.size());
    }

    @Test
    public void testKokler() {
        Collection list = sozluk.kokBul("armut");
        System.out.println("list:" + list);
        assertNotNull(list);
        assertTrue(list.size() == 1);
        Kok kok = (Kok) list.iterator().next();
        assertEquals(kok.icerik(), "armut");
        list = sozluk.kokBul("armud");
        System.out.println("list:" + list);
        assertTrue(list.size() == 1);
        kok = (Kok) list.iterator().next();
        assertEquals(kok.icerik(), "armut");
    }

    @Test
    public void testEsSesliKokBul() {
        Collection kokler = sozluk.kokBul("devir");
        assertTrue(kokler.size() == 2);
        kokler = sozluk.kokBul("devr");
        assertTrue(kokler.size() == 2);
    }

    @Test
    public void testKokBul() {
        Collection kokler = sozluk.kokBul("bahset");
        assertTrue(kokler.size() == 1);
        kokler = sozluk.kokBul("bahsed");
        assertTrue(kokler.size() == 1);

        KokAdayiBulucu kb = sozluk.kokBulucuFactory().kesinKokBulucu();
        List<Kok> adaylar = kb.adayKokleriBul("bahsetmek");
        assertTrue(adaylar.size() == 1);
        assertEquals(adaylar.get(0).icerik(), "bahset");

        adaylar = kb.adayKokleriBul("bahseden");
        assertTrue(adaylar.size() == 1);
        assertEquals(adaylar.get(0).icerik(), "bahset");

    }

}
