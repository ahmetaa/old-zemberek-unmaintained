/*
 *  ***** BEGIN LICENSE BLOCK *****
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
 *  The Original Code is Zemberek Doðal Dil Ýþleme Kütüphanesi.
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akýn, Mehmet D. Akýn, Guychmyrat Amanmyradov.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.tm.yapi.ek;

import net.zemberek.tm.yapi.TurkmenceSesliUretici;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.ek.EkOzelDurumTipi;
import net.zemberek.yapi.ek.EkOzelDurumUretici;
import net.zemberek.yapi.ek.EkOzelDurumu;
import net.zemberek.yapi.ek.TemelEkOzelDurumUretici;

/**
 * User: ahmet
 * Date: Sep 22, 2006
 */
public class TurkmenceEkOzelDurumUretici extends TemelEkOzelDurumUretici {

    public TurkmenceEkOzelDurumUretici(Alfabe alfabe) {
        this.alfabe = alfabe;
    }

    enum TurkmenceEkOzelDurumTipi implements EkOzelDurumTipi {

        ME_ISLIK,
        SON_SESLI_DUSUM,
        KI_YERGORKEZYAN,
        EDILGEN;

        public String ad() {
            return name();
        }
    }

    public EkOzelDurumu uret(String ad) {

        EkOzelDurumu oz = super.uret(ad);
        if (oz != null)
            return oz;

        if (!mevcut(TurkmenceEkOzelDurumTipi.values(), ad)) {
            logger.severe("Ozel durum adina karsilik dusen ek ozel durum tipi bulunamadi:" + ad);
            return null;
        }

        switch (TurkmenceEkOzelDurumTipi.valueOf(ad)) {
            case ME_ISLIK:
                return new MeEkOzelDurumu(alfabe, new TurkmenceSesliUretici(alfabe));
            case SON_SESLI_DUSUM:
                return new SonHarfDusumOzelDurumu();
            case KI_YERGORKEZYAN:
                return new KiEkOzelDurumu(alfabe);
            case EDILGEN:
                return new EdilgenOzelDurumu(alfabe);
        }
        return oz;
    }

}
