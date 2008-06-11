/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.kok;

import net.zemberek.yapi.HarfDizisi;

/**
 * son harfi yumusatir. normal sert harf yumusama kurali gecerlidir.
 */
public class Yumusama implements HarfDizisiIslemi {

    public void uygula(HarfDizisi dizi) {
        dizi.sonHarfYumusat();
    }

}
