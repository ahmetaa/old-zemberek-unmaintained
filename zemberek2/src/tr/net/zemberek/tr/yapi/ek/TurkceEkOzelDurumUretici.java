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

package net.zemberek.tr.yapi.ek;

import net.zemberek.araclar.Kayitci;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.ek.*;

import java.util.logging.Logger;

/**
 * User: ahmet
 * Date: Sep 16, 2006
 */
public class TurkceEkOzelDurumUretici extends TemelEkOzelDurumUretici {

    private static Logger logger = Kayitci.kayitciUret(TurkceEkOzelDurumUretici.class);

    public TurkceEkOzelDurumUretici(Alfabe alfabe) {
        this.alfabe = alfabe;
    }

    enum TurkceEkOzelDurumTipi implements EkOzelDurumTipi {

        BERABERLIK_IS,
        EDILGEN,
        GENIS_ZAMAN,
        SIMDIKI_ZAMAN,
        SU;

        public String ad() {
            return name();
        }
    }

    public EkOzelDurumu uret(String ad) {
        EkOzelDurumu oz = super.uret(ad);
        if (oz != null)
            return oz;

        if (!mevcut(TurkceEkOzelDurumTipi.values(), ad)) {
            logger.severe("Ozel durum adina karsilik dusen ek ozel durum tipi bulunamadi:" + ad);
            return null;
        }

        switch (TurkceEkOzelDurumTipi.valueOf(ad)) {
            case BERABERLIK_IS:
                return new BeraberlikIsOzelDurumu();
            case EDILGEN:
                return new EdilgenOzelDurumu(alfabe);
            case GENIS_ZAMAN:
                return new GenisZamanEkOzelDurumuTr();
            case SIMDIKI_ZAMAN:
                return new SimdikiZamanEkOzelDurumuTr(alfabe);
            case SU:
                return new SuOzelDurumu();

        }
        return oz;
    }
}
