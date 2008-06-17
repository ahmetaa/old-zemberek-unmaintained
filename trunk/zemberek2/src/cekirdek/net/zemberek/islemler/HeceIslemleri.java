/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler;

import java.util.List;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Heceleyici;

/**
 * HeceIslemleri
 * User: aakin Date: Mar 6, 2004
 */
public class HeceIslemleri {

    private final Alfabe alfabe;
    private final Heceleyici heceleyici;

    public HeceIslemleri(Alfabe alfabe, Heceleyici heceleyici) {
        this.alfabe = alfabe;
        this.heceleyici = heceleyici;
    }

    /**
     * Gelen String'i turkce heceleme kurallarina gore hecelerine ayirir. Sonucta
     * heceleri bir liste icinde dondurur. Eger heceleme yapilamazsa bos liste doner.
     *
     * @param giris hecelenecek kelime
     * @return sonHeceHarfSayisi String dizisi
     */
    public String[] hecele(String giris) {
        List<String> l = heceleyici.hecele(new HarfDizisi(giris, alfabe));
        return l.toArray(new String[l.size()]);
    }

    /**
     * girisin hecelenebir olup olmadigini bulur.
     *
     * @param giris hecelenecek kelime
     * @return hecelenebilirse true, aksi halde false.
     */
    public boolean hecelenebilirmi(String giris) {
        return heceleyici.hecele(new HarfDizisi(giris, alfabe)).size() != 0;
    }

    /**
     * Verilen kelime için sonHeceHarfSayisi indekslerini bir dizi içinde döndürür.
     * Open office gibi uygulamalar bu metodu kullaniyor.
     *
     * @param giris : Hece indeksleri belirlenecek
     * @return Hece indekslerini tutan bir int[]
     *         Örnek: "merhaba" kelimesi için 0,3,5
     *         "türklerin" kelimesi için 0,4,6
     */
    public int[] heceIndeksleriniBul(String giris) {
        giris = alfabe.ayikla(giris);
        String[] heceler = hecele(giris);
        int[] heceIndexleri = new int[heceler.length];

        if (heceler.length == 0)
            return null;

        int i = 0, sayac = 0;
        heceIndexleri[i++] = 0;

        for (String hece : heceler) {
            if (i == heceler.length)
                break;
            sayac += hece.length();
            heceIndexleri[i++] = sayac;
        }
        return heceIndexleri;
    }

}