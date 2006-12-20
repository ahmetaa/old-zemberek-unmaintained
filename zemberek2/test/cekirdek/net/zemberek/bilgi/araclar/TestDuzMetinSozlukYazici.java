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
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.Arrays;

/**
 * @author MDA & GBA
 */
public class TestDuzMetinSozlukYazici extends TemelTest {

    @Test
    public void testYazici() throws IOException {
        
        new File(TEMP_DIR+"test-duzyazi-kokler.txt").delete();

        // Once Testsozlugunu okuyoruz
        KokOkuyucu kokOkuyucu = new DuzYaziKokOkuyucu(
                TR_TEST_TEXT+"test-sozluk.txt",
                dilBilgisi.kokOzelDurumlari(),
                dilBilgisi.alfabe(),
                dilAyarlari.kokTipiAdlari());
        List<Kok> list = kokOkuyucu.hepsiniOku();

        // Sonra Bunu yazýyoruz
        DuzYaziKokYazici yazici = new DuzYaziKokYazici(
                TEMP_DIR+"test-duzyazi-kokler.txt",
                dilAyarlari.kokTipiAdlari());
        yazici.yaz(list);

        // DuzMetinSozlukOkuyucu ile olusan sozlugu tekrar oku
        DuzYaziKokOkuyucu okuyucu = new DuzYaziKokOkuyucu(
                TEMP_DIR+"test-duzyazi-kokler.txt",
                dilBilgisi.kokOzelDurumlari(),
                dilBilgisi.alfabe(),
                dilAyarlari.kokTipiAdlari());
        List<Kok> newList = okuyucu.hepsiniOku();

        assertEquals(list.size(), newList.size());
        for (int i = 0; i < list.size(); i++) {
            Kok kok1 = list.get(i);
            Kok kok2 = newList.get(i);
            KokOzelDurumu[] oz1 = kok1.ozelDurumDizisi();
            KokOzelDurumu[] oz2 = kok2.ozelDurumDizisi();
            assertTrue("kok1:" + kok1 + " kok2:" + kok2, kok1.icerik().equals(kok2.icerik()));
            assertEquals("kok1:" + kok1 + " kok2:" + kok2, kok1.tip(), kok2.tip());
            assertTrue("kok1:" + kok1 + " kok2:" + kok2, Arrays.equals(oz1, oz2));
        }
    }

}
