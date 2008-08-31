/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.kok;

import net.zemberek.yapi.HarfDizisi;

/**
 * Harf dizisine olusum sirasinda parametre olarak gelen kelimeyi ular..
 */
public class Ulama implements HarfDizisiIslemi {

    private final HarfDizisi ulanacak;

    public Ulama(HarfDizisi ulanacak) {
        this.ulanacak = ulanacak;
    }

    public void uygula(HarfDizisi dizi) {
        dizi.ekle(ulanacak);
    }
}
