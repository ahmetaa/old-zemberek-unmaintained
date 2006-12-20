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
import net.zemberek.araclar.turkce.TurkceMetinOkuyucu;
import net.zemberek.bilgi.araclar.DuzYaziKokOkuyucu;
import net.zemberek.bilgi.araclar.KokOkuyucu;
import net.zemberek.bilgi.kokler.AgacSozluk;
import net.zemberek.bilgi.kokler.KokBulucu;
import net.zemberek.bilgi.kokler.Sozluk;
import static org.junit.Assert.assertTrue;
import org.junit.Before;

import java.io.IOException;

/**
 */
public class TestOzelDurumCozumleme extends TemelTest {

    protected KelimeCozumleyici cozumleyici;

    @Before
    public void once() throws IOException {
        super.once();
        KokOkuyucu kokOkuyucu = new DuzYaziKokOkuyucu(
                "kaynaklar/tr/test/ozeldurum-sozlugu.txt",
                dilBilgisi.kokOzelDurumlari(),
                alfabe,
                dilAyarlari.kokTipiAdlari());
        Sozluk sozluk = new AgacSozluk(kokOkuyucu, alfabe, dilBilgisi.kokOzelDurumlari());
        //Normal denetleyici-cozumleyici olusumu
        KokBulucu kokBulucu = sozluk.getKokBulucuFactory().getKesinKokBulucu();
        cozumleyici = new StandartCozumleyici(
                kokBulucu,
                new KesinHDKiyaslayici(),
                this.alfabe,
                dilBilgisi.ekler(),
                dilBilgisi.cozumlemeYardimcisi());
    }

    public void testCozumleDogrular() {
        TurkceMetinOkuyucu tmo = new TurkceMetinOkuyucu();
        String dogrular[] = tmo.MetinOku("kaynaklar/tr/test/ozeldurum-hepsi-dogru.txt");
        for (int i = 0; i < dogrular.length; i++) {
            assertTrue("cozumleme hatasi:" + dogrular[i], cozumleyici.denetle(dogrular[i]));
        }
    }

    public void testCozumleYanlislar() {
        TurkceMetinOkuyucu tmo = new TurkceMetinOkuyucu();
        String yanlislar[] = tmo.MetinOku("kaynaklar/tr/test/ozeldurum-hepsi-yanlis.txt");
        for (String aYanlislar : yanlislar) {
            assertTrue("cozumleme hatasi:" + aYanlislar, !cozumleyici.denetle(aYanlislar));
        }
    }
}
