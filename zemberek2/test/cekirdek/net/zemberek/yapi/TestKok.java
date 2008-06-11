/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi;

import net.zemberek.TemelTest;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.*;
import net.zemberek.yapi.kok.KokOzelDurumBilgisi;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 */
public class TestKok extends TemelTest {


    KokOzelDurumBilgisi koz;

    @Before
    public void once() throws IOException {
        super.once();
        koz = dilBilgisi.kokOzelDurumlari();
    }

    @Test
    public void testDegismisIcerikOlustur() {
        Kok kok = new Kok("ara", KelimeTipi.FIIL);
        kok.ozelDurumEkle(koz.ozelDurum(SIMDIKI_ZAMAN));
        ozelDurumTest(kok, "ar");

        kok = new Kok("kitap", KelimeTipi.ISIM);
        kok.ozelDurumEkle(koz.ozelDurum(SESSIZ_YUMUSAMASI));
        ozelDurumTest(kok, "kitab");

        String str = "al" + Alfabe.CHAR_ii + "n";
        kok = new Kok(str, KelimeTipi.ISIM);
        kok.ozelDurumEkle(koz.ozelDurum(ISIM_SESLI_DUSMESI));
        ozelDurumTest(kok, "aln");

        kok = new Kok("nakit", KelimeTipi.ISIM);
        kok.ozelDurumEkle(koz.ozelDurum(ISIM_SESLI_DUSMESI));
        kok.ozelDurumEkle(koz.ozelDurum(SESSIZ_YUMUSAMASI));
        ozelDurumTest(kok, "nakd");

        kok = new Kok("ben", KelimeTipi.ZAMIR);
        kok.ozelDurumEkle(koz.ozelDurum(TEKIL_KISI_BOZULMASI));
        ozelDurumTest(kok, "ban");

        kok = new Kok("sen", KelimeTipi.ZAMIR);
        kok.ozelDurumEkle(koz.ozelDurum(TEKIL_KISI_BOZULMASI));
        ozelDurumTest(kok, "san");

        kok = new Kok("de", KelimeTipi.FIIL);
        kok.ozelDurumEkle(koz.ozelDurum(FIIL_KOK_BOZULMASI));
        ozelDurumTest(kok, "di");

        kok = new Kok("ye", KelimeTipi.FIIL);
        kok.ozelDurumEkle(koz.ozelDurum(FIIL_KOK_BOZULMASI));
        ozelDurumTest(kok, "yi");
    }

    private void ozelDurumTest(Kok kok, String beklenen) {
        String[] results = koz.ozelDurumUygula(kok);
        assertTrue(results.length > 0);
        String sonuc = results[0];
        assertEquals(sonuc, beklenen);
    }

    @Test
    public void testEqual() {
        Kok kok1 = new Kok("kitap", KelimeTipi.ISIM);
        Kok kok2 = new Kok("kitap", KelimeTipi.ISIM);
        Kok kok3 = new Kok("kitab", KelimeTipi.ISIM);
        assertTrue(kok1.equals(kok2));
        assertTrue(!kok1.equals(kok3));
    }

    @Test
    public void asilIcerikUret() {
        Kok k1 = new Kok("ahmet", KelimeTipi.OZEL);
        assertEquals(k1.asilIcerikUret(alfabe), "Ahmet");

        Kok k2 = new Kok("ahmet", KelimeTipi.ISIM);
        assertEquals(k2.asilIcerikUret(alfabe), "ahmet");

        Kok k3 = new Kok("blah", KelimeTipi.KISALTMA);
        k3.setAsil("BLAH.");
        assertEquals(k3.asilIcerikUret(alfabe), "BLAH.");
    }

}
