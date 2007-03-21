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

package net.zemberek.tt.yapi.kok;

import net.zemberek.yapi.kok.KokOzelDurumTipi;

/**
 * Created by IntelliJ IDEA.
 * User: ahmet
 * Date: Mar 19, 2007
 * Time: 10:19:26 PM
 * To change this template use File | Settings | File Templates.
 */
public enum TatarcaKokOzelDurumTipleri implements KokOzelDurumTipi {

    SESSIZ_YUMUSAMASI("YUM");

    private String kisaAd;
    private String[] ekAdlari = new String[0];

    TatarcaKokOzelDurumTipleri(String kisaAd, String... ekAdlari) {
        this.kisaAd = kisaAd;
        if (this.ekAdlari != null) {
            this.ekAdlari = ekAdlari;
        }
    }

    public String ad() {
        return this.name();
    }

    public String kisaAd() {
        return kisaAd;
    }

    public int indeks() {
        return ordinal();
    }

    public String[] ekAdlari() {
        return ekAdlari;
    }
}
