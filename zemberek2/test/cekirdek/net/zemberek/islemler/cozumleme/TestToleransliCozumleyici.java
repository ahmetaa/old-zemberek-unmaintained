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

/**
 */
public class TestToleransliCozumleyici extends TemelTest {

    static KelimeCozumleyici cozumleyici;
    static KokOkuyucu kokOkuyucu;
    static KokBulucu kokBulucu = null;
    private List testKumesi = new ArrayList();

    @Before
    public void once() throws IOException {
        super.once();
        KokOkuyucu kokOkuyucu = new DuzYaziKokOkuyucu(
                "kaynaklar/tr/test/test-kokler.txt",
                dilBilgisi.kokOzelDurumlari(),
                alfabe,
                dilAyarlari.kokTipiAdlari());
        Sozluk sozluk = new AgacSozluk(kokOkuyucu, alfabe, dilBilgisi.kokOzelDurumlari());
        //Normal denetleyici-cozumleyici olusumu
        KokBulucu kokBulucu = sozluk.getKokBulucuFactory().getToleransliKokBulucu(1);
        cozumleyici = new StandartCozumleyici(
                kokBulucu,
                new KesinHDKiyaslayici(),
                this.alfabe,
                dilBilgisi.ekler(),
                dilBilgisi.cozumlemeYardimcisi());

    }

    @Test
    public void testToleransliCozumleyici() throws IOException {
        List<TestGirdisi> l = TestUtils.girdileriOku("kaynaklar/tr/test/toleransli-cozumleme-test.txt");
        for (TestGirdisi girdi : l) {
            System.out.println("giris:" + girdi.anahtar);
            Set<String> beklenenler = TestUtils.makeSet(girdi.degerler);
            for (Kelime kelime : cozumleyici.cozumle(girdi.anahtar))
                assertTrue(beklenenler.contains(kelime.icerikStr()));
        }
    }

}




