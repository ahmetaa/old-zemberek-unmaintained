/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.kok;

import net.zemberek.yapi.HarfDizisi;

/**
 * Basitce harf dizisinin sondan bir onceki harfini siler.
 * User: ahmet
 * Date: Aug 28, 2005
 */
public class AraSesliDusmesi implements HarfDizisiIslemi {

    public void uygula(HarfDizisi dizi) {
        if (dizi.length() >= 2)
            dizi.harfSil(dizi.length() - 2);
    }
}
