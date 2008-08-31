/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.kok;

import net.zemberek.yapi.HarfDizisi;

/**
 * kelimeye sesli harf eklendiginde son sessizin tekrarlanmasina neden olur.
 * hak-> hakka, red->reddi gibi.
 */
public class Ciftleme implements HarfDizisiIslemi {

    public void uygula(HarfDizisi dizi) {
        if (dizi.length() > 0)
            dizi.ekle(dizi.harf(dizi.length() - 1));
    }
}
