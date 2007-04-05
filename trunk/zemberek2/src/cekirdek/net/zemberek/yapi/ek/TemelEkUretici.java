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

import static net.zemberek.yapi.ek.TemelEkUretimKurali.KAYNASTIR;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.TurkceHarf;

import java.util.List;
import java.util.Set;


public abstract class TemelEkUretici implements EkUretici {

    public boolean sesliIleBaslayabilir(List<EkUretimBileseni> bilesenler) {
        for (EkUretimBileseni bilesen : bilesenler) {
            if (bilesen.kural == KAYNASTIR) continue;
            return bilesen.harf.sesliMi() || bilesen.kural.isSesliUretimKurali();
        }
        return false;
    }

    public HarfDizisi olusumIcinEkUret(HarfDizisi ulanacak, Ek sonrakiEk, List<EkUretimBileseni> bilesenler) {
        //TODO: gecici olarak bu sekilde
        return cozumlemeIcinEkUret(ulanacak, null, bilesenler);
    }

    public Set<TurkceHarf> olasiBaslangicHarfleri(List<EkUretimBileseni> bilesenler) {
        return null;
    }

}
