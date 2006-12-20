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

/*
 * Created on 26.Ara.2004
 *
 */
package net.zemberek.istatistik;

import net.zemberek.yapi.Kok;

/**
 * @author MDA
 */
public class KokCebi {

    private Kok[] cep = null;

    public KokCebi(int cepBoyu) {
        cep = new Kok[cepBoyu];
    }

    public Kok getKok(int indeks) {
        return cep[indeks];
    }

    public void ekle(Kok kok, int indeks) {
        cep[indeks] = kok;
        kok.setIndeks(indeks);
    }

}
