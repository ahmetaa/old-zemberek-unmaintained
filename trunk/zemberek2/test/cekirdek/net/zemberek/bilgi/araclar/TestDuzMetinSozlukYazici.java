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
 *  The Original Code is Zemberek Do?al Dil ??leme Kütüphanesi.
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Ak?n, Mehmet D. Ak?n.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

/*
 * Created on 09.Nis.2004
 */
package net.zemberek.bilgi.araclar;

import net.zemberek.TemelTest;
import net.zemberek.araclar.TimeTracker;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.kok.KokOzelDurumu;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author MDA & GBA
 */
public class TestDuzMetinSozlukYazici extends TemelTest {

    @Test
    public void testYazici() throws IOException {
        // Once Testsozlugunu okuyoruz
        TimeTracker.startClock("x");
        KokOkuyucu kokOkuyucu = new DuzYaziKokOkuyucu(
                "kaynaklar/tr/test/test-kokler.txt",
                dilBilgisi.kokOzelDurumlari(),
                dilBilgisi.alfabe(),
                dilAyarlari.kokTipiAdlari());
        List list = kokOkuyucu.hepsiniOku();
        System.out.println(" Sure: " + TimeTracker.stopClock("x"));

        // Sonra Bunu yazýyoruz
        DuzYaziKokYazici yazici = new DuzYaziKokYazici("kaynaklar/temp/test-duzyazi-kokler.txt");
        yazici.yaz(list);

        // DuzMetinSozlukOkuyucu ile olusan sozlugu tekrar oku
        DuzYaziKokOkuyucu okuyucu = new DuzYaziKokOkuyucu(
                "kaynaklar/temp/test-duzyazi-kokler.txt",
                dilBilgisi.kokOzelDurumlari(),
                dilBilgisi.alfabe(),
                dilAyarlari.kokTipiAdlari());
        List newList = okuyucu.hepsiniOku();

        assertEquals(list.size(), newList.size());
        for (int i = 0; i < list.size(); i++) {
            Kok kok1 = (Kok) list.get(i);
            Kok kok2 = (Kok) newList.get(i);
            KokOzelDurumu[] oz1 = kok1.ozelDurumDizisi();
            KokOzelDurumu[] oz2 = kok2.ozelDurumDizisi();
            assertTrue("kok1:" + kok1 + " kok2:" + kok2, kok1.icerik().equals(kok2.icerik()));
            assertEquals("kok1:" + kok1 + " kok2:" + kok2, kok1.tip(), kok2.tip());
            assertTrue("kok1:" + kok1 + " kok2:" + kok2, oz1.equals(oz2));
        }
    }
}
