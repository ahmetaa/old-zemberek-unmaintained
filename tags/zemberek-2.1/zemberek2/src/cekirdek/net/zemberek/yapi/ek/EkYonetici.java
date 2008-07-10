/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.ek;

import net.zemberek.yapi.Kok;

/**
 * User: ahmet
 * Date: Aug 24, 2005
 */
public interface EkYonetici {

    /**
     * istenilen isimli ek'i dondurur
     *
     * @param ad
     * @return ek, eger o isimde ek yok ise null.
     */
    Ek ek(String ad);

    /**
     * Kok nesnesinin tipine gore gelebilecek ilk ek'i dondurur.
     * Baslangic ekleri bilgisi dil tarafindan belirlenir.
     *
     * @param kok
     * @return ilk Ek, eger kok tipi baslangic ekleri <baslangicEkleri>
     *         haritasinda belirtilmemisse BOS_EK doner.
     */
    Ek ilkEkBelirle(Kok kok);

}
