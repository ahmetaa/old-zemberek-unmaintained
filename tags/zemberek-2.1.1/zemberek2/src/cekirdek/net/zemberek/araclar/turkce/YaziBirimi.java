/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.araclar.turkce;

/**
 */
public class YaziBirimi {
    public YaziBirimiTipi tip;
    public String icerik;

    public YaziBirimi(YaziBirimiTipi tip, String icerik) {
        this.tip = tip;
        this.icerik = icerik;
    }

    public String toString() {
        return "{" +
                "tip=" + tip +
                ", icerik='" + icerik + "'" +
                "}";
    }

}
