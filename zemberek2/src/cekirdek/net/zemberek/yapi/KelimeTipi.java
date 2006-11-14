/**
 * Created by IntelliJ IDEA.
 * User: aakin
 * Date: Feb 18, 2004
 * Time: 11:49:00 AM
 * To change this template use File | Settings | File Templates.
 */
package net.zemberek.yapi;

public enum KelimeTipi {

    ISIM,
    FIIL,
    SIFAT,
    SAYI,
    YANKI,
    ZAMIR,
    SORU,
    IMEK,
    ZAMAN,
    EDAT,
    BAGLAC,
    OZEL,
    UNLEM,
    KISALTMA,
    HATALI;

    // Hızlı look-up için tiplerin indeksini bir dizide tutalım
    private static KelimeTipi[] degerler = KelimeTipi.values();
    
    public static KelimeTipi getTip(int indeks) {
        if(indeks<0 || indeks>=degerler.length)
          throw new ArrayIndexOutOfBoundsException("Girilen degerde indeksli KelimeTipi yok!");
        return degerler[indeks];
    }

    public int getIndeks() {
        return this.ordinal();
    }

}