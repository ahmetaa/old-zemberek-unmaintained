/*
 *  ***** BEGIN LICENSE BLOCK *****
 *
 *  Version: MPL 1.1
 *
 *  The contents of this file are subject to the Mozilla Public License Version
 *  1.1 (the "License"); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an "AS IS" basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 *  for the specific language governing rights and limitations under the
 *  License.
 *
 *  The Original Code is "Zemberek Dogal Dil Isleme Kutuphanesi"
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akin, Mehmet D. Akin.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
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