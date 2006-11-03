package net.zemberek.yapi.ozeldurum;

import net.zemberek.TemelTest;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.kok.KokOzelDurumBilgisi;

import java.io.IOException;

/**
 */
public class TestOzelDurumlar extends TemelTest {

   KokOzelDurumBilgisi koz ;

    public void setUp() throws IOException {
        super.setUp();
        koz = dilBilgisi.kokOzelDurumlari();
    }

    public void testYumusama() {
        HarfDizisi dizi = hd("kitap");
        dilBilgisi.kokOzelDurumlari().ozelDurum("YUM").uygula(dizi);
        assertEquals("kitab", dizi.toString());
    }

    public void testCiftleme() {
        HarfDizisi dizi =hd("hat");
         dilBilgisi.kokOzelDurumlari().ozelDurum("CIFT").uygula(dizi);
        assertEquals("hatt", dizi.toString());
    }

    public void testAraSesliDusmesi() {
        HarfDizisi dizi = hd("burun");
         dilBilgisi.kokOzelDurumlari().ozelDurum("DUS").uygula(dizi);
        assertEquals("burn", dizi.toString());
    }

    public void testKokDegisimleri() {
        Kok kok= new Kok("bahset", KelimeTipi.FIIL);
        kok.ozelDurumEkle(koz.ozelDurum("GEN"));
        kok.ozelDurumEkle(koz.ozelDurum("YUM"));
        String[] sonuclar = koz.ozelDurumUygula(kok);
        assertTrue(sonuclar.length==1);
        assertEquals(sonuclar[0], "bahsed");
    }


}
