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

package net.zemberek.yapi.kok;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.TurkceHarf;

/**
 * Bu islem sadece saat-ler turu ozel durumlarda kullanilir.
 */
public class SonSesliIncelt implements HarfDizisiIslemi {

    Alfabe alfabe;

    public SonSesliIncelt(Alfabe alfabe) {
        this.alfabe = alfabe;
    }

    /**
     * en son kalin sesli harfi bulup onu ince formu ile degistirir.
     * ornegin saat -> saAt haline donusur. ince a harfi icin TurkceAlfabe sinifini inceleyin
     *
     * @param dizi
     */
    public void uygula(HarfDizisi dizi) {
        for (int i = dizi.length() - 1; i >= 0; i--) {
            final TurkceHarf h = dizi.harf(i);
            if (h.sesliMi() && !h.inceSesliMi())
                dizi.harfDegistir(i, alfabe.kalinSesliIncelt(dizi.harf(i)));
        }
    }
}
