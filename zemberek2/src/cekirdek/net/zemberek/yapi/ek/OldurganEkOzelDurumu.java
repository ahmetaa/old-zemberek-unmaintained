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
 *  Ahmet A. Akýn, Mehmet D. Akýn.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.yapi.ek;

import net.zemberek.islemler.cozumleme.HarfDizisiKiyaslayici;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.TurkceHarf;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.ek.EkOzelDurumu;
import net.zemberek.yapi.ek.Ek;

/**
 * Bu ozel durum oldurganlik eki "t"'nin sadece bazi durumlarda olusmasini saglar.
 * bu sartlar,
 * - kok son harfi sesli ise uret. (aratmak, yatirtmak gibi.)
 * - kok son harf sessiz ise, ama r ya da l ise ve gece sayisi birden fazla ise uret.
 * (kopar-t-mak, kucul-t-mek, oturtmak,  )
 * TODO: bazi koklere yukaridaki sartlar saglansa bile oldurganlik eki eklenemez
 * Ornegin, geltmek,  olmaz. Bu durum kok ozel durumu ile saglanabilir.
 * User: ahmet
 * Date: Aug 29, 2005
 */
public class OldurganEkOzelDurumu extends EkOzelDurumu {

    private final HarfDizisi T;

    public OldurganEkOzelDurumu(Alfabe alfabe) {
        T = new HarfDizisi("t",alfabe);
    }

    public HarfDizisi cozumlemeIcinUret(Kelime kelime, HarfDizisi giris, HarfDizisiKiyaslayici kiyaslayici) {
        TurkceHarf son = kelime.sonHarf();
        if (son.sesliMi() || ((son.charDeger()=='r') || son.charDeger()==('l'))
                && kelime.icerik().sesliSayisi() > 1) {
            return T;
        }
        return null;
    }

    public HarfDizisi olusumIcinUret(Kelime kelime, Ek sonrakiEk) {
        return cozumlemeIcinUret(kelime, null, null);
    }
}
