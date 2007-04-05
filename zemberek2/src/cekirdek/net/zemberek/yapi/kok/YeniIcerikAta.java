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

package net.zemberek.yapi.kok;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;

import java.util.Map;

/**
 * kuralsiz kok bozulmasi ozel durumlarinda kullanilir.
 * uretim parametresi olarak gelen Map icerisinde hangi kelimenin hangi kelimeye
 * donusecegi belirtilir.
 * ornegin
 * demek->diyen icin de->di donusumu, ben->bana icin ben->ban donusumu.
 */
public class YeniIcerikAta implements HarfDizisiIslemi {

    private Map<String, String> kokDonusum;
    private Alfabe alfabe;

    public YeniIcerikAta( Alfabe alfabe, Map<String, String>kokDonusum) {
        this.kokDonusum = kokDonusum;
        this.alfabe = alfabe;
    }

    public void uygula(HarfDizisi dizi) {
        String kelime = kokDonusum.get(dizi.toString());
        if (kelime != null) {
            dizi.sil();
            dizi.ekle(new HarfDizisi(kelime, alfabe));
        }
    }
}
