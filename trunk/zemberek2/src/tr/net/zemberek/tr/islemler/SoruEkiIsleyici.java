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

package net.zemberek.tr.islemler;

import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.ek.EkYonetici;
import net.zemberek.tr.yapi.ek.TurkceEkAdlari;

public class SoruEkiIsleyici {

    EkYonetici ekYonetici;


    public SoruEkiIsleyici(EkYonetici yonetici) {
        this.ekYonetici = yonetici;
    }

    /**
     * Gelen kelimler icinde soru "koku" bulursa bunu onceki kelimeye ek olarak ekler.
     * aslnda eklenip eklenemeyeceginin testinin yapilmasi gerekir ama duzgun yazilmis cumlelerde
     * isleyecegini saniyourm
     *
     * @param cumleKelimeleri
     * @return yeni kelime dizisi. soru kokleri eke donustugunden yeni kelime dizisinde bu kokler yer almaz.
     */
    public Kelime[] soruEkiVarsaBirlestir(Kelime [] cumleKelimeleri) {
        //soru koku cumleden silineceginden yeni bir diziye gerek var..
        Kelime[] yeniKelimeler = new Kelime[cumleKelimeleri.length];
        int j = 0;
        //cumle kelimelerini tarayalim bastan sona.
        for (int i = 0; i < cumleKelimeleri.length; i++) {
            Kelime kelime = cumleKelimeleri[i];
            // ilk kelime degilse ve kelime aslinda soru eki ise..
            if (i > 0 && kelime.kok().tip().equals(KelimeTipi.SORU)) {
                // onceki kelimeyi al ve sonuna soru eki ekle.
                // daha sonra soru "kokunden" sonra gelen tum ekleri de ekle.
                Kelime oncekiKelime = cumleKelimeleri[i - 1];
                oncekiKelime.ekler().add(ekYonetici.ek(TurkceEkAdlari.FIIL_SORU_MI));
                if (kelime.ekler().size() > 1)
                    oncekiKelime.ekler().addAll(kelime.ekler().subList(1, kelime.ekler().size()));
            } else
                yeniKelimeler[j++] = kelime;
        }
        return yeniKelimeler;
    }
}
