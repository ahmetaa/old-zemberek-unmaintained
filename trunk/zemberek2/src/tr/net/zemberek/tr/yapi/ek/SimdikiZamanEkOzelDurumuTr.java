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
 *  The Original Code is Zemberek Do?al Dil ??leme Kütüphanesi.
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Ak?n, Mehmet D. Ak?n.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.tr.yapi.ek;

import net.zemberek.islemler.cozumleme.HarfDizisiKiyaslayici;
import net.zemberek.tr.yapi.TurkceSesliUretici;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.TurkceHarf;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.EkOzelDurumu;

/**
 * Ekler icin simdiki zaman ozel durumu. Simdiki zaman eki sesli ile biten bir koke (ya da eke)
 * eklendiginde son sesliyi dusurur.
 * <p/>
 * User: ahmet
 * Date: Aug 15, 2005
 */
public class SimdikiZamanEkOzelDurumuTr extends EkOzelDurumu {

    private final Alfabe alfabe;
    private TurkceSesliUretici sesliUretci;

    public SimdikiZamanEkOzelDurumuTr(Alfabe alfabe) {
        this.alfabe = alfabe;
        sesliUretci = new TurkceSesliUretici(alfabe);
    }

    public HarfDizisi cozumlemeIcinUret(Kelime kelime, HarfDizisi giris, HarfDizisiKiyaslayici kiyaslayici) {
        if (kiyaslayici == null) return null;
        // eki olustur.
        HarfDizisi ek = ekUretici.cozumlemeIcinEkUret(kelime.icerik(), giris, uretimBilesenleri);
        TurkceHarf ekHarfi = sesliUretci.sesliBelirleIU(kelime.icerik());
        HarfDizisi olusum = new HarfDizisi("yor", alfabe);
        olusum.ekle(0, ekHarfi);
        int harfPozisyonu = kelime.boy() + ek.length();
        if (kiyaslayici.aradanKiyasla(giris, olusum, harfPozisyonu))
            return ek;
        return null;
    }

    public HarfDizisi olusumIcinUret(Kelime kelime, Ek sonrakiEk){
        if(sonrakiEk.ad().equals(TurkceEkAdlari.FIIL_SIMDIKIZAMAN_IYOR))
          return ekUretici.olusumIcinEkUret(kelime.icerik(),sonrakiEk, uretimBilesenleri);
        return null;
    }

}
