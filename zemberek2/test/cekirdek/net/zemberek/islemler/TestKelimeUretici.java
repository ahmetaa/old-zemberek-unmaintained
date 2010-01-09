/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler;

import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.*;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.zemberek.TemelTest;
import net.zemberek.TestUtils;
import net.zemberek.bilgi.kokler.KokAdayiBulucu;
import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.islemler.cozumleme.CozumlemeSeviyesi;
import net.zemberek.islemler.cozumleme.KesinHDKiyaslayici;
import net.zemberek.islemler.cozumleme.StandartCozumleyici;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.ek.Ek;

import org.junit.Before;
import org.junit.Test;

/**
 */
public class TestKelimeUretici extends TemelTest {

    private KelimeUretici kelimeUretici;
    private StandartCozumleyici cozumleyici;
    private Sozluk kokler;

    @Before
    public void once() throws IOException {
        super.once();
        kelimeUretici = new KelimeUretici(alfabe, dilBilgisi.ekler(), dilBilgisi.cozumlemeYardimcisi());
        //Normal denetleyici-cozumleyici olusumu
        KokAdayiBulucu kokBulucu = dilBilgisi.kokler().kokBulucuFactory().kesinKokBulucu();
        cozumleyici = new StandartCozumleyici(kokBulucu, new KesinHDKiyaslayici(), alfabe, dilBilgisi.ekler(), dilBilgisi.cozumlemeYardimcisi());
        kokler = dilBilgisi.kokler();

    }

    private Ek ek(String ad) {
        return dilBilgisi.ekler().ek(ad);
    }

    private List<Ek> ekListesi(String... ekAdlari) {
        List<Ek> ekler = new ArrayList<Ek>();
        for (String s : ekAdlari)
            ekler.add(dilBilgisi.ekler().ek(s));
        return ekler;
    }

    @Test
    public void testKelimeUret() {

        Kok kok = kokler.kokBul("sabret", KelimeTipi.FIIL);
        List<Ek> ekler = ekListesi(FIIL_YETENEK_EBIL, FIIL_GELECEKZAMAN_ECEK, FIIL_KISI_BIZ);
        assertEquals("sabredebilece\u011fiz", kelimeUretici.kelimeUret(kok, ekler));

        kok = kokler.kokBul("armut", KelimeTipi.ISIM);
        ekler = ekListesi(ISIM_SAHIPLIK_BIZ_IMIZ, ISIM_TANIMLAMA_DIR);
        assertEquals("armudumuzdur", kelimeUretici.kelimeUret(kok, ekler));

        Kelime almanyada = cozumleyici.cozumle("Almanya'da", CozumlemeSeviyesi.TEK_KOK)[0];
        assertEquals("Almanya'da", kelimeUretici.kelimeUret(almanyada.kok(), almanyada.ekler()));

        kok = kokler.kokBul("sabret", KelimeTipi.FIIL);
        ekler = ekListesi(FIIL_YETENEK_EBIL, FIIL_GELECEKZAMAN_ECEK, FIIL_KISI_BIZ);
        assertEquals("sabredebilece\u011fiz", kelimeUretici.kelimeUret(kok, ekler));

        kok = kokler.kokBul("et", KelimeTipi.FIIL);
        ekler = ekListesi(FIIL_GELECEKZAMAN_ECEK);
        assertEquals("edecek", kelimeUretici.kelimeUret(kok, ekler));

        kok = kokler.kokBul("et", KelimeTipi.FIIL);
        ekler = ekListesi(FIIL_KOK, FIIL_OLUMSUZLUK_ME);
        assertEquals("etme", kelimeUretici.kelimeUret(kok, ekler));
    }


    private static String I = String.valueOf(Alfabe.CHAR_ii);

    @Test
    public void testKelimeUretRasgeleDiziliEk() {

        Kok kok = kokler.kokBul("armut", KelimeTipi.ISIM);
        List<Ek> ekler = ekListesi(ISIM_YONELME_E, ISIM_TANIMLAMA_DIR, ISIM_SAHIPLIK_BIZ_IMIZ);
        assertEquals("armudumuzad" + I + "r", kelimeUretici.sirasizEklerleUret(kok, ekler));

        kok = kokler.kokBul("sabret", KelimeTipi.FIIL);
        ekler = ekListesi(FIIL_GELECEKZAMAN_ECEK, FIIL_YETENEK_EBIL, FIIL_KOK, FIIL_KISI_BIZ);
        assertEquals("sabredebilece\u011fiz", kelimeUretici.sirasizEklerleUret(kok, ekler));

    }

    /**
     * fonksiyonel olusum testi. hepsi-dogru.txt dosyasindaki kelimeleri cozumleyip geri olusturur.
     *
     * @throws java.io.IOException io hatasi durumunda..
     */
    @Test
    public void testCozGeriOlustur() throws IOException {
        List<String> kelimeler = TestUtils.satirlariOku("kaynaklar/tr/test/hepsi-dogru.txt");
        for (String s : kelimeler) {
            Kelime[] cozumler = cozumleyici.cozumle(s, CozumlemeSeviyesi.TEK_KOK);
            for (Kelime kelime : cozumler) {
                String uretilen = kelimeUretici.kelimeUret(kelime.kok(), kelime.ekler());
                assertEquals("cozumlenen:" + s + ", olusan:" + uretilen + " ile ayni degil", s, uretilen);
            }
        }
    }

    @Test
    public void testYumusama() {
        String kelime = "uyutuyor";
        Kelime[] kelimeler = cozumleyici.cozumle(kelime, CozumlemeSeviyesi.TUM_KOKLER);
        for (Kelime kel : kelimeler) {
            String uretilen = kelimeUretici.kelimeUret(kel.kok(), kel.ekler());
            System.out.println(uretilen);
            assertEquals(uretilen, kelime);
        }
    }

    @Test
    public void testEkAyristirma() {
        String l1[] = {"kedi", "le", "r", "im"};
        String l2[] = {"kedi", "ler", "im"};
        Kelime[] cozumler = cozumleyici.cozumle("kedilerim", CozumlemeSeviyesi.TEK_KOK);
        for (Kelime kel : cozumler) {
            if (kel.ekSayisi() == 4)
                assertEquals(l1, kelimeUretici.ayristir(kel));
            else
                assertEquals(l2, kelimeUretici.ayristir(kel));
        }
    }

    @Test
    public void testEkAyristir() {
        Kelime[] cozumler = cozumleyici.cozumle("yedeklemeden", CozumlemeSeviyesi.TEK_KOK);
        for (Kelime kel : cozumler) {
            String[] ayrim = kelimeUretici.ayristir(kel);
            System.out.println(Arrays.toString(ayrim));
        }
    }


}
