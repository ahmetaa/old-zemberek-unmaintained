/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler.cozumleme;

import net.zemberek.TemelTest;
import net.zemberek.TestUtils;
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

    @Before
    public void once() throws IOException {
        super.once();
        cozumleyici = new StandartCozumleyici(
                dilBilgisi.kokler().kokBulucuFactory().kesinKokBulucu(),
                new KesinHDKiyaslayici(),
                this.alfabe,
                dilBilgisi.ekler(),
                dilBilgisi.cozumlemeYardimcisi());
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
            assertTrue("denetleme hatasi:" + s, cozumleyici.cozumlenebilir(s));
        }
        List<String> yanlislar = TestUtils.satirlariOku("kaynaklar/tr/test/hepsi-yanlis.txt");
        for (String s : yanlislar) {
            assertTrue("denetleme hatasi:" + s, !cozumleyici.cozumlenebilir(s));
        }
    }

}


