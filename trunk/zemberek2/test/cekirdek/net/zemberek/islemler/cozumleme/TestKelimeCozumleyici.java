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


