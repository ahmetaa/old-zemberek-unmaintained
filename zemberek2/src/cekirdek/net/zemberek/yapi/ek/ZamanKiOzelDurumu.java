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

import net.zemberek.yapi.ek.EkOzelDurumu;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.TurkceHarf;
import net.zemberek.yapi.Alfabe;
import net.zemberek.islemler.cozumleme.HarfDizisiKiyaslayici;


/**
 * -ki zaman eki bazi durumlarda -ki yerine kU seklinde olusur. (dun-kU gibi.)
 * Bu ozel durum sayilacagindan bi sinifa ihtiyac duyuldu.
 * User: ahmet
 * Date: Aug 15, 2005
 */
public class ZamanKiOzelDurumu extends EkOzelDurumu {

    public HarfDizisi cozumlemeIcinUret(Kelime kelime, HarfDizisi giris, HarfDizisiKiyaslayici kiyaslayici) {
        TurkceHarf sonSesli = kelime.icerik().sonSesli();
        if (sonSesli.charDeger()=='u' || sonSesli.charDeger()==Alfabe.CHAR_uu)
            return ekUretici.cozumlemeIcinEkUret(kelime.icerik(), giris, uretimBilesenleri);
        else
            return null;
    }

    public HarfDizisi olusumIcinUret(Kelime kelime, Ek sonrakiEk){
        return cozumlemeIcinUret(kelime, null, null);
    }
}
