package net.zemberek.islemler;

import net.zemberek.TemelTest;
import net.zemberek.tr.yapi.ek.TurkceEkAdlari;
import net.zemberek.yapi.EkSiralayici;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.EkYonetici;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: ahmet
 * Date: Sep 6, 2005
 */
public class TestEkSiralayici extends TemelTest {


    public void testEkSiralama() {
        EkYonetici ekler = dilBilgisi.ekler();
        //rasgele ek listesi olustur.
        Ek[] rasgele = new Ek[]{
                ekler.ek(TurkceEkAdlari.ISIM_KISI_BEN_IM),
                ekler.ek(TurkceEkAdlari.FIIL_YETENEK_EBIL),
                ekler.ek(TurkceEkAdlari.IMEK_SART_SE),
                ekler.ek(TurkceEkAdlari.FIIL_OLUMSUZLUK_ME),
                ekler.ek(TurkceEkAdlari.FIIL_GELECEKZAMAN_ECEK)};
        List<Ek> rasgeleList = new ArrayList(Arrays.asList(rasgele));
        Ek[] beklenen = new Ek[]{
                ekler.ek(TurkceEkAdlari.FIIL_OLUMSUZLUK_ME),
                ekler.ek(TurkceEkAdlari.FIIL_YETENEK_EBIL),
                ekler.ek(TurkceEkAdlari.FIIL_GELECEKZAMAN_ECEK),
                ekler.ek(TurkceEkAdlari.IMEK_SART_SE),
                ekler.ek(TurkceEkAdlari.ISIM_KISI_BEN_IM)};
        List<Ek> beklenenList = new ArrayList(Arrays.asList(beklenen));

        EkSiralayici siralayici = new EkSiralayici(rasgeleList, ekler.ek(TurkceEkAdlari.FIIL_KOK));
        List<List<Ek>> sonuc = siralayici.olasiEkDizilimleriniBul();

        assertTrue(sonuc.size()==1);
        assertTrue(sonuc.contains(beklenenList));

        System.out.println("rasgele ekler:" + rasgeleList);
        for (List<Ek> olasiDizilim : sonuc) {
            System.out.println("olasi dizilim:" + olasiDizilim);
        }
    }
}
