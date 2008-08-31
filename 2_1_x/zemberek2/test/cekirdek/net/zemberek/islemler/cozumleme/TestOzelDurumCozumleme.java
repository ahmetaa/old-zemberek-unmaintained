/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler.cozumleme;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import net.zemberek.TemelTest;
import net.zemberek.TestUtils;
import net.zemberek.bilgi.araclar.DuzYaziKokOkuyucu;
import net.zemberek.bilgi.araclar.KokOkuyucu;
import net.zemberek.bilgi.kokler.AgacSozluk;
import net.zemberek.bilgi.kokler.KokAdayiBulucu;
import net.zemberek.bilgi.kokler.Sozluk;

import org.junit.Before;
import org.junit.Test;

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
        KokAdayiBulucu kokBulucu = sozluk.kokBulucuFactory().kesinKokBulucu();
        cozumleyici = new StandartCozumleyici(
                kokBulucu,
                new KesinHDKiyaslayici(),
                this.alfabe,
                dilBilgisi.ekler(),
                dilBilgisi.cozumlemeYardimcisi());
    }

    @Test
    public void testCozumleDogrular() throws IOException {
        List<String> dogrular = TestUtils.satirlariOku("kaynaklar/tr/test/ozeldurum-hepsi-dogru.txt");
        for (String s: dogrular){
            assertTrue("cozumleme hatasi:" + s, cozumleyici.cozumlenebilir(s));
        }
    }

    @Test
    public void testCozumleYanlislar() throws IOException {
        List<String> yanlislar = TestUtils.satirlariOku("kaynaklar/tr/test/ozeldurum-hepsi-yanlis.txt");
        for (String s : yanlislar) {
            assertTrue("cozumleme hatasi:" + s, !cozumleyici.cozumlenebilir(s));
        }
    }
}
