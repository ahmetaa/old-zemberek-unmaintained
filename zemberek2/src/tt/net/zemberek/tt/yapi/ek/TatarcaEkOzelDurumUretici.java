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

package net.zemberek.tt.yapi.ek;

import net.zemberek.yapi.ek.TemelEkOzelDurumUretici;
import net.zemberek.yapi.ek.EkOzelDurumTipi;
import net.zemberek.yapi.ek.EkOzelDurumu;
import net.zemberek.yapi.Alfabe;

public class TatarcaEkOzelDurumUretici extends TemelEkOzelDurumUretici {

    public TatarcaEkOzelDurumUretici(Alfabe alfabe) {
        this.alfabe = alfabe;
    }

    enum TatarcaEkOzelDurumTipi implements EkOzelDurumTipi {
        //TODO: buraya ilgili ozel durumlar eklenmeli
        TEST_EK_OZEL_DURUMU;

        public String ad() {
            return name();
        }
    }

    @Override
    public EkOzelDurumu uret(String ad) {

        EkOzelDurumu oz = super.uret(ad);
        if (oz != null)
            return oz;

        if (!mevcut(TatarcaEkOzelDurumTipi.values(), ad)) {
            logger.severe("Ozel durum adina karsilik dusen ek ozel durum tipi bulunamadi:" + ad);
            return null;
        }

        switch (TatarcaEkOzelDurumTipi.valueOf(ad)) {
            //TODO: buraya ilgili ozel durumlar eklenmeli
            case TEST_EK_OZEL_DURUMU:
                return null;
        }
        return oz;
    }
}
