/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi;

public enum KelimeTipi {

    ISIM,
    FIIL,
    SIFAT,
    SAYI,
    YANKI,
    ZAMIR,
    ZARF,
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
          throw new ArrayIndexOutOfBoundsException("Girilen degerde indeksli KelimeTipi yok!:"+indeks);
        return degerler[indeks];
    }

    public int getIndeks() {
        return this.ordinal();
    }

}