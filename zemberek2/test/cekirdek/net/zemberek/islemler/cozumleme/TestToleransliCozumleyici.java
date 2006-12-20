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

package net.zemberek.islemler.cozumleme;

import net.zemberek.TemelTest;
import net.zemberek.TestGirdisi;
import net.zemberek.TestUtils;
import net.zemberek.bilgi.araclar.DuzYaziKokOkuyucu;
import net.zemberek.bilgi.araclar.KokOkuyucu;
import net.zemberek.bilgi.kokler.AgacSozluk;
import net.zemberek.bilgi.kokler.KokBulucu;
import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.yapi.Kelime;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 */
public class TestToleransliCozumleyici extends TemelTest {

    static KelimeCozumleyici cozumleyici;
    static KokOkuyucu kokOkuyucu;
    static KokBulucu kokBulucu = null;

    @Before
    public void once() throws IOException {
        super.once();
        KokOkuyucu kokOkuyucu = new DuzYaziKokOkuyucu(
                "kaynaklar/tr/test/test-sozluk.txt",
                dilBilgisi.kokOzelDurumlari(),
                alfabe,
                dilAyarlari.kokTipiAdlari());
        Sozluk sozluk = new AgacSozluk(kokOkuyucu, alfabe, dilBilgisi.kokOzelDurumlari());
        //Normal denetleyici-cozumleyici olusumu
        KokBulucu kokBulucu = sozluk.getKokBulucuFactory().getToleransliKokBulucu(1);
        cozumleyici = new ToleransliCozumleyici(
                kokBulucu,
                dilBilgisi.ekler(),
                alfabe,
                dilBilgisi.cozumlemeYardimcisi());

    }

    @Test
    public void testToleransliCozumleyici() throws IOException {
        List<TestGirdisi> l = TestUtils.girdileriOku("kaynaklar/tr/test/toleransli-cozumleme-test.txt");
        for (TestGirdisi girdi : l) {
            System.out.println("giris:" + girdi.anahtar);
            Set<String> cozumler = new HashSet<String>();
            for (Kelime kel : cozumleyici.cozumle(girdi.anahtar))
                cozumler.add(kel.icerik().toString());
            for (String beklenen : girdi.degerler)
                assertTrue("bulunamayan kelime:" + beklenen, cozumler.contains(beklenen));
        }
    }

}




