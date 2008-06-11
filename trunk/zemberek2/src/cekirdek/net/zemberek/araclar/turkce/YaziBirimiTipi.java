/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.araclar.turkce;

public enum YaziBirimiTipi {

    KELIME, NOKTALAMA, BOSLUK, CUMLE, PARAGRAF, DIGER;

    public String toString() {
        return name();
    }
}
