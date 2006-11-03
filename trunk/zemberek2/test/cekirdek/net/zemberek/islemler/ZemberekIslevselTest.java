package net.zemberek.islemler;

import net.zemberek.TemelTest;
import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;

/**
 * User: ahmet
 * Date: Dec 10, 2005
 */
public class ZemberekIslevselTest extends TemelTest {

    Zemberek zemberek = new Zemberek(new TurkiyeTurkcesi());

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

    public void tesAsciiDonucturucu() {
        String giris = "Ibrik";
        String[] sonuclar = zemberek.asciidenTurkceye(giris);
        assertTrue(sonuclar.length == 1);
        assertEquals(sonuclar.length, "ibrik");
        giris = "Irmak";
        sonuclar = zemberek.asciidenTurkceye(giris);
        assertTrue(sonuclar.length == 1);
        assertEquals(sonuclar[0], "\u0131rmak");
    }

    public void testKokEkle()
    {
        assertFalse(zemberek.kelimeDenetle("zembenklik"));
        Sozluk kokler = zemberek.dilBilgisi().kokler();
        Kok kok = new Kok("zembenk", KelimeTipi.ISIM);
        kok.ozelDurumEkle(dilBilgisi.kokOzelDurumlari().ozelDurum(TurkceKokOzelDurumTipleri.SESSIZ_YUMUSAMASI_NK));
        kokler.ekle(kok);
        assertTrue(zemberek.kelimeDenetle("zembenklik"));
        assertTrue(zemberek.kelimeDenetle("zembenge"));
    }

}
