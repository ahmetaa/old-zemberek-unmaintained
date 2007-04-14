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
 * Created on 18.Eki.2004
 */
package net.zemberek.bilgi.kokler;

import java.util.ArrayList;
import java.util.List;

import net.zemberek.yapi.Kok;

/**
 * Çözümleyicinin verilen bir kelime için aday kökleri bulması için kullanılır.
 * Giriş kelimesinin ilk harfinden başlanarak ağaçta ilerlenir. ilerleyecek
 * yer kalmayana veya kelime bitene dek ağaçta yürünürve rastlanan tüm kökler
 * aday olarak toplanır.
 * 
 * Örneğin, "Balerinler" kelimesi için "bal, bale ve balerin" köklerini taşıyan
 * bir liste döndürür.
 *
 * @author MDA
 */
public class KesinKokAdayiBulucu implements KokAdayiBulucu {
    KokAgaci agac = null;

    public KesinKokAdayiBulucu(KokAgaci agac) {
        this.agac = agac;
    }

    public List<Kok> adayKokleriBul(final String giris) {
        List<Kok> adaylar = new ArrayList<Kok>(3);
        int girisIndex = 0;
        KokDugumu node = agac.getKokDugumu();

        while (girisIndex < giris.length()) {
            node = node.altDugumBul(giris.charAt(girisIndex));
            if (node == null) break;
            if (node.getKok() != null) {
                if (giris.startsWith((String) node.kelime())) {
                    node.tumKokleriEkle(adaylar);
                }
            }
            girisIndex++;
        }
        return adaylar;
    }
}

