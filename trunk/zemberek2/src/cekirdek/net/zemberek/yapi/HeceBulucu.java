package net.zemberek.yapi;

/**
 * User: ahmet
 * Date: Sep 10, 2005
 */
public interface HeceBulucu {

    /**
     * Giren harf dizisinin sonunda mantikli olarak yer alan hecenin harf
     * sayisini dondurur.
     *
     * @param dizi: harf dizisi.
     * @return int, 1,2,3 ya da 4 donerse giris dizisinin dizinin sondan o
     *         kadarharfi heceyi temsil eder -1 donerse hecenin bulunamadigi
     *         anlamina gelir.
     */
     int sonHeceHarfSayisi(HarfDizisi dizi);
}
