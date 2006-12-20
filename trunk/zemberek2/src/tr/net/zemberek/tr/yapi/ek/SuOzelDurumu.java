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

package net.zemberek.tr.yapi.ek;

import net.zemberek.yapi.ek.EkOzelDurumu;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Kelime;
import net.zemberek.islemler.cozumleme.HarfDizisiKiyaslayici;
import net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri;

/**
 * 'su' kokune kaynastirma iceren cesitli ekler eklendiginde normal kaynastirma harfi yerine
 * 'y' harfi kullanilir.
 */
public class SuOzelDurumu extends EkOzelDurumu {

    public HarfDizisi cozumlemeIcinUret(Kelime kelime, HarfDizisi giris, HarfDizisiKiyaslayici kiyaslayici) {
        if(kelime.gercekEkYok() && kelime.kok().ozelDurumIceriyormu(TurkceKokOzelDurumTipleri.SU_OZEL_DURUMU))
           return ekUretici.cozumlemeIcinEkUret(kelime.icerik(), giris, uretimBilesenleri);
        return null;
    }

    public HarfDizisi olusumIcinUret(Kelime kelime, Ek sonrakiEk) {
        return cozumlemeIcinUret(kelime, null, null);
    }
}
