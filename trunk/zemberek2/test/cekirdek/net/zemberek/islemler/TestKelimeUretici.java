package net.zemberek.islemler;

import net.zemberek.TemelTest;
import net.zemberek.TestUtils;
import net.zemberek.bilgi.kokler.KokBulucu;
import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.islemler.cozumleme.KesinHDKiyaslayici;
import net.zemberek.islemler.cozumleme.StandartCozumleyici;
import net.zemberek.tr.yapi.ek.TurkceEkAdlari;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.ek.Ek;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 */
public class TestKelimeUretici extends TemelTest {

    private KelimeUretici kelimeUretici;
    private StandartCozumleyici cozumleyici;
    private Sozluk kokler;

    @Before
    public void once() throws IOException {
        super.once();
        kelimeUretici = new KelimeUretici(alfabe, dilBilgisi.cozumlemeYardimcisi());
        //Normal denetleyici-cozumleyici olusumu
        KokBulucu kokBulucu = dilBilgisi.kokler().getKokBulucuFactory().getKesinKokBulucu();
        cozumleyici = new StandartCozumleyici(kokBulucu, new KesinHDKiyaslayici(), alfabe, dilBilgisi.ekler(), dilBilgisi.cozumlemeYardimcisi());
        kokler = dilBilgisi.kokler();
    }

    private Ek ek(String ad) {
        return dilBilgisi.ekler().ek(ad);
    }

    @Test
    public void testKelimeUret() {

        Collection set = kokler.kokBul("armut");
        Kok kok = (Kok) set.iterator().next();
        List ekler = new ArrayList();
        ekler.add(ek(TurkceEkAdlari.ISIM_KOK));
        ekler.add(ek(TurkceEkAdlari.ISIM_SAHIPLIK_BIZ_IMIZ));
        ekler.add(ek(TurkceEkAdlari.ISIM_TANIMLAMA_DIR));
        assertEquals("armudumuzdur", kelimeUretici.kelimeUret(kok, ekler));

        set = kokler.kokBul("sabret");
        kok = (Kok) set.iterator().next();
        ekler = new ArrayList();
        ekler.add(ek(TurkceEkAdlari.FIIL_KOK));
        ekler.add(ek(TurkceEkAdlari.FIIL_YETENEK_EBIL));
        ekler.add(ek(TurkceEkAdlari.FIIL_GELECEKZAMAN_ECEK));
        ekler.add(ek(TurkceEkAdlari.FIIL_KISI_BIZ));
        assertEquals("sabredebilece\u011fiz", kelimeUretici.kelimeUret(kok, ekler));
    }

    @Test
    public void testUretim() {
        Collection kokSet = kokler.kokBul("kekik");
        if (kokSet != null) {
            // es sesli kokler olabilir ama biz set icindeki ilk koku aliyoruz.
            // burada tipe gore filtreleme vs yapilabilir.
            Kok kok = (Kok) kokSet.iterator().next();

            List ekler = new ArrayList();
            // aslinda kelime ureticiye ilk ek olan yalin eklerin gonderilmemesi daha iyi olurdu..
            // ama simdilik boyle.
            ekler.add(ek(TurkceEkAdlari.ISIM_KOK));
            ekler.add(ek(TurkceEkAdlari.ISIM_SAHIPLIK_BIZ_IMIZ));
            ekler.add(ek(TurkceEkAdlari.ISIM_TANIMLAMA_DIR));
            String sonuc = kelimeUretici.kelimeUret(kok, ekler);
            System.out.println(sonuc);
        } else
            System.out.println("kok bulunamadi.");
    }

    /**
     * fonksiyonel olusum testi. hepsi-dogru.txt dosyasindaki kelimeleri cozumleyip geri olusturur.
     *
     * @throws java.io.IOException
     */
    @Test
    public void testCozGeriOlustur() throws IOException {
        List<String> kelimeler = TestUtils.satirlariOku("kaynaklar/tr/test/hepsi-dogru.txt");
        for (String s : kelimeler) {
            Kelime[] cozumler = cozumleyici.cozumle(s);
            for (Kelime kelime : cozumler) {
                String uretilen = kelimeUretici.kelimeUret(kelime.kok(), kelime.ekler());
                assertEquals("cozumlenen:" + s + ", olusan:" + uretilen + " ile ayni degil", s, uretilen);
            }
        }
    }
}
