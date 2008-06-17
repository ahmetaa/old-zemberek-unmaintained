/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import net.zemberek.TemelTest;
import net.zemberek.TestUtils;
import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;

import org.junit.Before;
import org.junit.Test;

/**
 * User: ahmet
 * Date: Dec 10, 2005
 */
public class ZemberekIslevselTest extends TemelTest {

    Zemberek zemberek;

    @Before
    public void once() throws IOException {
        super.once();
        zemberek = new Zemberek(dilAyarlari);
    }

    @Test
    public void testAsciidenDonusturucuSiralama() {
        //asciiden donustur.
        String giris = "cok";
        String[] sonuclar = zemberek.asciidenTurkceye(giris);
        //essesliler eleneceginden iki sonuc donmeli
        assertTrue(sonuclar.length == 2);
        //kok kullanim frekansina gore siralanmis olmasi gerekir.
        assertEquals(sonuclar[0], "\u00e7ok");
        assertEquals(sonuclar[1], "\u00e7\u00f6k");
    }

    @Test
    public void tesAsciiDonucturucu() {
        String giris = "Ibrik";
        String[] sonuclar = zemberek.asciidenTurkceye(giris);
        assertTrue(sonuclar.length == 1);
        assertEquals(sonuclar[0], "ibrik");
        giris = "Irmak";
        sonuclar = zemberek.asciidenTurkceye(giris);
        assertTrue(sonuclar.length == 1);
        assertEquals(sonuclar[0], "\u0131rmak");
    }

    @Test
    public void testKokEkle() {
        assertFalse(zemberek.kelimeDenetle("zembenklik"));
        Sozluk kokler = zemberek.dilBilgisi().kokler();
        Kok kok = new Kok("zembenk", KelimeTipi.ISIM);
        kok.ozelDurumEkle(dilBilgisi.kokOzelDurumlari().ozelDurum(TurkceKokOzelDurumTipleri.SESSIZ_YUMUSAMASI_NK));
        kokler.ekle(kok);
        assertTrue(zemberek.kelimeDenetle("zembenklik"));
        assertTrue(zemberek.kelimeDenetle("zembenge"));
    }

    @Test
    public void stringKokBul(){
        KokBulucu kb = zemberek.kokBulucu();
        String [] beklenen = {"elma", "elmas"};
        String [] hesaplanan = kb.stringKokBul("elmas\u0131");
        TestUtils.assertObjectArrayContentsEqual(beklenen, hesaplanan);
       
        beklenen = new String[] {"kul", "Kulu"};
        hesaplanan = kb.stringKokBul("Kulu");
        TestUtils.assertObjectArrayContentsEqual(beklenen, hesaplanan);

        beklenen = new String[] {"kurt", "kur"};
        hesaplanan = kb.stringKokBul("kurda");
        TestUtils.assertObjectArrayContentsEqual(beklenen, hesaplanan);
    }

    @Test
    public void heceleme() {
        String[] kelimeler =  {"ara", "siir", "siirt", "kalem", "turk"};
        for (String s : kelimeler) {
           assertTrue(zemberek.hecele(s).length>0);
        }
    }


}
