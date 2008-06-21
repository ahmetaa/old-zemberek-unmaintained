/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

/*
 * Created on 09.Nis.2004
 */
package net.zemberek.bilgi.araclar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import net.zemberek.TemelTest;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.kok.KokOzelDurumu;

import org.junit.Test;

/**
 * @author MDA & GBA
 */
public class TestDuzMetinSozlukYazici extends TemelTest {

    @Test
    public void testYazici() throws IOException {
        
        new File(TEMP_DIR+"test-duzyazi-kokler.txt").delete();

        // Once Testsozlugunu okuyoruz
        KokOkuyucu kokOkuyucu = new DuzYaziKokOkuyucu(
                TR_TEST_TEXT+"test-sozluk.txt",
                dilBilgisi.kokOzelDurumlari(),
                dilBilgisi.alfabe(),
                dilAyarlari.kokTipiAdlari());
        List<Kok> list = kokOkuyucu.hepsiniOku();

        // Sonra Bunu yaz�yoruz
        DuzYaziKokYazici yazici = new DuzYaziKokYazici(
                TEMP_DIR+"test-duzyazi-kokler.txt",
                dilAyarlari.kokTipiAdlari());
        yazici.yaz(list);

        // DuzMetinSozlukOkuyucu ile olusan sozlugu tekrar oku
        DuzYaziKokOkuyucu okuyucu = new DuzYaziKokOkuyucu(
                TEMP_DIR+"test-duzyazi-kokler.txt",
                dilBilgisi.kokOzelDurumlari(),
                dilBilgisi.alfabe(),
                dilAyarlari.kokTipiAdlari());
        List<Kok> newList = okuyucu.hepsiniOku();

        assertEquals(list.size(), newList.size());
        for (int i = 0; i < list.size(); i++) {
            Kok kok1 = list.get(i);
            Kok kok2 = newList.get(i);
            KokOzelDurumu[] oz1 = kok1.ozelDurumDizisi();
            KokOzelDurumu[] oz2 = kok2.ozelDurumDizisi();
            assertTrue("kok1:" + kok1 + " kok2:" + kok2, kok1.icerik().equals(kok2.icerik()));
            assertEquals("kok1:" + kok1 + " kok2:" + kok2, kok1.tip(), kok2.tip());
            assertTrue("kok1:" + kok1 + " kok2:" + kok2, Arrays.equals(oz1, oz2));
        }
    }

}
