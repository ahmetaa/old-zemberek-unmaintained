/*
 * Created on 09.Nis.2004
 */
package net.zemberek.bilgi.araclar;

import net.zemberek.TemelTest;
import net.zemberek.araclar.TimeTracker;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.kok.KokOzelDurumu;

import java.io.IOException;
import java.util.List;

/**
 * @author MDA & GBA
 */
public class TestDuzMetinSozlukYazici extends TemelTest {
    public void testYazici() throws IOException {
        // Once Testsozlugunu okuyoruz
        TimeTracker.startClock("x");
        KokOkuyucu kokOkuyucu = new DuzYaziKokOkuyucu(
                "kaynaklar/tr/test/test-kokler.txt",
                dilBilgisi.kokOzelDurumlari(),
                dilBilgisi.alfabe(),
                dilAyarlari.kelimeTipiAdlari());
        List list = kokOkuyucu.hepsiniOku();
        System.out.println(" Sure: " + TimeTracker.stopClock("x"));

        // Sonra Bunu yazýyoruz
        DuzYaziKokYazici yazici = new DuzYaziKokYazici("kaynaklar/temp/test-duzyazi-kokler.txt");
        yazici.yaz(list);

        // DuzMetinSozlukOkuyucu ile olusan sozlugu tekrar oku
        DuzYaziKokOkuyucu okuyucu = new DuzYaziKokOkuyucu(
                "kaynaklar/temp/test-duzyazi-kokler.txt",
                dilBilgisi.kokOzelDurumlari(),
                dilBilgisi.alfabe(),
                dilAyarlari.kelimeTipiAdlari());
        List newList = okuyucu.hepsiniOku();

        assertEquals(list.size(), newList.size());
        for (int i = 0; i < list.size(); i++) {
            Kok kok1 = (Kok) list.get(i);
            Kok kok2 = (Kok) newList.get(i);
            KokOzelDurumu[] oz1 = kok1.ozelDurumDizisi();
            KokOzelDurumu[] oz2 = kok2.ozelDurumDizisi();
            assertTrue("kok1:" + kok1 + " kok2:" + kok2, kok1.icerik().equals(kok2.icerik()));
            assertEquals("kok1:" + kok1 + " kok2:" + kok2, kok1.tip(), kok2.tip());
            assertTrue("kok1:" + kok1 + " kok2:" + kok2, oz1.equals(oz2));
        }
    }
}
