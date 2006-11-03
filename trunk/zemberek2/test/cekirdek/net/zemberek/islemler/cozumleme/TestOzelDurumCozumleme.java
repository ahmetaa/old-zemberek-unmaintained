package net.zemberek.islemler.cozumleme;

import net.zemberek.TemelTest;
import net.zemberek.araclar.turkce.TurkceMetinOkuyucu;
import net.zemberek.bilgi.araclar.DuzYaziKokOkuyucu;
import net.zemberek.bilgi.araclar.KokOkuyucu;
import net.zemberek.bilgi.kokler.KokBulucu;
import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.bilgi.kokler.AgacSozluk;

import java.io.IOException;

/**
 */
public class TestOzelDurumCozumleme extends TemelTest {

    protected KelimeCozumleyici cozumleyici;

    public void setUp() throws IOException {
        super.setUp();
        KokOkuyucu kokOkuyucu = new DuzYaziKokOkuyucu(
                "kaynaklar/tr/test/ozeldurum-sozlugu.txt",
                dilBilgisi.kokOzelDurumlari(),
                alfabe,
                dilAyarlari.kelimeTipiAdlari());
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
