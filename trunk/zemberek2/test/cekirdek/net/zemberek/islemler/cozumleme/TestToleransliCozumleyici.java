/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler.cozumleme;

import net.zemberek.TemelTest;
import net.zemberek.TestGirdisi;
import net.zemberek.TestUtils;
import net.zemberek.bilgi.araclar.DuzYaziKokOkuyucu;
import net.zemberek.bilgi.araclar.KokOkuyucu;
import net.zemberek.bilgi.kokler.AgacSozluk;
import net.zemberek.bilgi.kokler.KokAdayiBulucu;
import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.yapi.Kelime;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 */
public class TestToleransliCozumleyici extends TemelTest {

    static ToleransliCozumleyici cozumleyici;
    static KokOkuyucu kokOkuyucu;
    static KokAdayiBulucu kokBulucu = null;

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
        KokAdayiBulucu kokBulucu = sozluk.kokBulucuFactory().toleransliKokBulucu(1);
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




