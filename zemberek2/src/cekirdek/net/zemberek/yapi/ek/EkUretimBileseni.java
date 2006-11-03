package net.zemberek.yapi.ek;

import net.zemberek.yapi.TurkceHarf;

import java.io.Serializable;

/**
 * uretim bilesen sinifi, uretim kural kelimesindeki bilesenleri temsil eder.
 * degistirilemez, thread safe.
 */
public class EkUretimBileseni {

    private final UretimKurali kural;
    private final TurkceHarf harf;

    public EkUretimBileseni(UretimKurali kural, TurkceHarf harf) {
        this.kural = kural;
        this.harf = harf;
    }

    public UretimKurali kural() {
        return kural;
    }

    public TurkceHarf harf() {
        return harf;
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