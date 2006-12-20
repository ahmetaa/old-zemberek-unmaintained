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

import net.zemberek.araclar.Kayitci;
import net.zemberek.yapi.Alfabe;

import java.util.logging.Logger;

/**
 * User: ahmet
 * Date: Sep 16, 2006
 */
public abstract class TemelEkOzelDurumUretici implements EkOzelDurumUretici {

    protected static Logger logger = Kayitci.kayitciUret(TemelEkOzelDurumUretici.class);

    protected Alfabe alfabe;

    public enum TemelEkOzelDurumuTipi implements EkOzelDurumTipi {

        SON_HARF_YUMUSAMA,
        OLDURGAN,
        ON_EK,
        ZAMAN_KI;

        public String ad() {
            return name();
        }
    }

    public EkOzelDurumu uret(String ad) {
        if (!mevcut(TemelEkOzelDurumuTipi.values(), ad))
            return null;
        else
            switch (TemelEkOzelDurumuTipi.valueOf(ad)) {
                case SON_HARF_YUMUSAMA:
                    return new SonHarfYumusamaOzelDurumu();
                case OLDURGAN:
                    return new OldurganEkOzelDurumu(alfabe);
                case ON_EK:
                    return new OnEkOzelDurumu();
                case ZAMAN_KI:
                    return new ZamanKiOzelDurumu();
                default:
                    return null;
            }
    }

    /**
     * efektif olmayan bir tip denetimi.
     *
     * @param tipler
     * @param ad
     * @return eger kisaAd ile belirtilen tip var ise true.
     */
    protected boolean mevcut(EkOzelDurumTipi[] tipler, String ad) {
        for (EkOzelDurumTipi tip : tipler) {
            if (tip.ad().equals(ad))
                return true;
        }
        return false;
    }


}
