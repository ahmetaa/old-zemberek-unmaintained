/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler.cozumleme;

import net.zemberek.yapi.HarfDizisi;

/**
 */
public interface HarfDizisiKiyaslayici {

    public boolean kiyasla(HarfDizisi h1, HarfDizisi h2);

    public boolean bastanKiyasla(HarfDizisi h1, HarfDizisi h2);

    public boolean aradanKiyasla(HarfDizisi h1, HarfDizisi h2, int baslangic);
}
