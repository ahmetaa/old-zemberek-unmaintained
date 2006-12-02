package net.zemberek.yapi.ozeldurum;

import net.zemberek.TemelTest;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.kok.KokOzelDurumBilgisi;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;

import java.io.IOException;

/**
 */
public class TestOzelDurumlar extends TemelTest {

   KokOzelDurumBilgisi koz ;

    @Before
    public void once() throws IOException {
        super.once();
        koz = dilBilgisi.kokOzelDurumlari();
    }

    @Before
    public void testYumusama() {
        HarfDizisi dizi = hd("kitap");
        dilBilgisi.kokOzelDurumlari().ozelDurum("YUM").uygula(dizi);
        assertEquals("kitab", dizi.toString());
    }

    @Before
    public void testCiftleme() {
        HarfDizisi dizi =hd("hat");
         dilBilgisi.kokOzelDurumlari().ozelDurum("CIFT").uygula(dizi);
        assertEquals("hatt", dizi.toString());
    }

    @Before
    public void testAraSesliDusmesi() {
        HarfDizisi dizi = hd("burun");
         dilBilgisi.kokOzelDurumlari().ozelDurum("DUS").uygula(dizi);
        assertEquals("burn", dizi.toString());
    }

    @Before
    public void testKokDegisimleri() {
        Kok kok= new Kok("bahset", KelimeTipi.FIIL);
        kok.ozelDurumEkle(koz.ozelDurum("GEN"));
        kok.ozelDurumEkle(koz.ozelDurum("YUM"));
        String[] sonuclar = koz.ozelDurumUygula(kok);
        assertTrue(sonuclar.length==1);
        assertEquals(sonuclar[0], "bahsed");
    }


}
