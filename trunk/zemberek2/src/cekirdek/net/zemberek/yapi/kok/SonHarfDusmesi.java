/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.kok;

import net.zemberek.yapi.HarfDizisi;

/**
 * Basitce harf dizisinin son harfini siler.
 */
public class SonHarfDusmesi implements HarfDizisiIslemi {

    public void uygula(HarfDizisi dizi) {
        if (dizi.length() > 0)
            dizi.harfSil(dizi.length() - 1);
    }
}
