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

import net.zemberek.islemler.cozumleme.HarfDizisiKiyaslayici;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.EkOzelDurumu;

public class KiEkOzelDurumu extends EkOzelDurumu {

    private final HarfDizisi KI;

    public KiEkOzelDurumu(Alfabe alfabe) {
        KI = new HarfDizisi("ki", alfabe);
    }

    public HarfDizisi cozumlemeIcinUret(Kelime kelime, HarfDizisi giris, HarfDizisiKiyaslayici kiyaslayici) {
        if (kiyaslayici == null) return null;
        // eki olustur.
        HarfDizisi ek = ekUretici.cozumlemeIcinEkUret(kelime.icerik(), giris, uretimBilesenleri);
        //olusum.ekle(0, ekHarfi);
        int harfPozisyonu = kelime.boy() + ek.length();
        if (kiyaslayici.aradanKiyasla(giris, KI, harfPozisyonu))
            return ek;
        return null;
    }

    public HarfDizisi olusumIcinUret(Kelime kelime, Ek sonrakiEk) {
        if (sonrakiEk.ad().equals(TurkmenceEkAdlari.AT_YERGORKEZYAN_KI))
            return ekUretici.olusumIcinEkUret(kelime.icerik(), sonrakiEk, uretimBilesenleri);
        return null;
    }
}
