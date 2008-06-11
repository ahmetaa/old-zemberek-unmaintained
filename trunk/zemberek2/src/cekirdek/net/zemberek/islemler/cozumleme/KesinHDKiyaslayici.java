/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler.cozumleme;

import net.zemberek.yapi.HarfDizisi;

/**
 */
public class KesinHDKiyaslayici implements HarfDizisiKiyaslayici {
    public final boolean kiyasla(HarfDizisi h1, HarfDizisi h2) {
        return h1.equals(h2);
    }

    public final boolean bastanKiyasla(HarfDizisi h1, HarfDizisi h2) {
        return h1.bastanKiyasla(h2);
    }

    public final boolean aradanKiyasla(HarfDizisi h1, HarfDizisi h2, int index) {
        return h1.aradanKiyasla(index, h2);
    }
}
