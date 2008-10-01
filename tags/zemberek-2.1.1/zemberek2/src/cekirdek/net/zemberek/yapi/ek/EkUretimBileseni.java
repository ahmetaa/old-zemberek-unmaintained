/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.ek;

import net.zemberek.yapi.TurkceHarf;

/**
 * uretim bilesen sinifi, uretim kural kelimesindeki bilesenleri temsil eder.
 * degistirilemez, thread safe.
 * Her kural icin bir harf bilgisi bulunmayabilir. bu durumda TANIMSIZ_HARF kullanilir.
 */
public final class EkUretimBileseni {

    public final EkUretimKurali kural;
    public final TurkceHarf harf;

    public EkUretimBileseni(EkUretimKurali kural, TurkceHarf harf) {
        this.kural = kural;
        this.harf = harf;
    }

    public String toString() {
        return "kural=" + kural + ", harf=" + (harf == null ? "" : "" + harf.charDeger());
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final EkUretimBileseni that = (EkUretimBileseni) o;

        if (harf != null ? !harf.equals(that.harf) : that.harf != null) return false;
        if (kural != that.kural) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (kural != null ? kural.hashCode() : 0);
        result = 29 * result + (harf != null ? harf.hashCode() : 0);
        return result;
    }
}