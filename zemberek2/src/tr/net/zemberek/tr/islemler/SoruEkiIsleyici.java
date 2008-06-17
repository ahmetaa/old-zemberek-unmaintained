/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tr.islemler;

import net.zemberek.tr.yapi.ek.TurkceEkAdlari;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.ek.EkYonetici;

public class SoruEkiIsleyici {

    EkYonetici ekYonetici;


    public SoruEkiIsleyici(EkYonetici yonetici) {
        this.ekYonetici = yonetici;
    }

    /**
     * Gelen kelimler icinde soru "koku" bulursa bunu onceki kelimeye ek olarak ekler.
     * aslnda eklenip eklenemeyeceginin testinin yapilmasi gerekir ama duzgun yazilmis cumlelerde
     * isleyecegini saniyourm
     *
     * @param cumleKelimeleri
     * @return yeni kelime dizisi. soru kokleri eke donustugunden yeni kelime dizisinde bu kokler yer almaz.
     */
    public Kelime[] soruEkiVarsaBirlestir(Kelime [] cumleKelimeleri) {
        //soru koku cumleden silineceginden yeni bir diziye gerek var..
        Kelime[] yeniKelimeler = new Kelime[cumleKelimeleri.length];
        int j = 0;
        //cumle kelimelerini tarayalim bastan sona.
        for (int i = 0; i < cumleKelimeleri.length; i++) {
            Kelime kelime = cumleKelimeleri[i];
            // ilk kelime degilse ve kelime aslinda soru eki ise..
            if (i > 0 && kelime.kok().tip()==KelimeTipi.SORU) {
                // onceki kelimeyi al ve sonuna soru eki ekle.
                // daha sonra soru "kokunden" sonra gelen tum ekleri de ekle.
                Kelime oncekiKelime = cumleKelimeleri[i - 1];
                oncekiKelime.ekEkle(ekYonetici.ek(TurkceEkAdlari.FIIL_SORU_MI));
                if (kelime.ekSayisi() > 1)
                    oncekiKelime.ekler().addAll(kelime.ekler().subList(1, kelime.ekler().size()));
            } else
                yeniKelimeler[j++] = kelime;
        }
        return yeniKelimeler;
    }
}
