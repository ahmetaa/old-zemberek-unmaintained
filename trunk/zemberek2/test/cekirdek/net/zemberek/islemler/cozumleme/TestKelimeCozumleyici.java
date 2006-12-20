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

package net.zemberek.islemler.cozumleme;

import net.zemberek.TemelTest;
import net.zemberek.TestUtils;
import net.zemberek.islemler.Heceleyici;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * User: aakin
 * Date: Mar 8, 2004
 */
public class TestKelimeCozumleyici extends TemelTest {

    static KelimeCozumleyici cozumleyici;
    static Heceleyici heceleyici;

    @Before
    public void once() throws IOException {
        super.once();
        cozumleyici = new StandartCozumleyici(
                dilBilgisi.kokler().getKokBulucuFactory().getKesinKokBulucu(),
                new KesinHDKiyaslayici(),
                this.alfabe,
                dilBilgisi.ekler(),
                dilBilgisi.cozumlemeYardimcisi());
        heceleyici = new Heceleyici(alfabe, dilBilgisi.heceBulucu());
    }

    /**
     * fonksiyonel cozumleme testi. dogru ve yanlis yazilmis kelime dosyalarini okuyup test eder.
     *
     * @throws IOException
     */
    @Test
    public void testDenetleDogruYanlis() throws IOException {
        List<String> dogrular = TestUtils.satirlariOku("kaynaklar/tr/test/hepsi-dogru.txt");
        for (String s : dogrular) {
            assertTrue("denetleme hatasi:" + s, cozumleyici.denetle(s));
        }
        List<String> yanlislar = TestUtils.satirlariOku("kaynaklar/tr/test/hepsi-yanlis.txt");
        for (String s : yanlislar) {
            assertTrue("denetleme hatasi:" + s, !cozumleyici.denetle(s));
        }
    }

}


