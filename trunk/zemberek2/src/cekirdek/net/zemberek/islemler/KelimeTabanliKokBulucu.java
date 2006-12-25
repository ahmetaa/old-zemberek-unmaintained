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
 *  Ahmet A. Akin
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.islemler;

import net.zemberek.islemler.cozumleme.KelimeCozumleyici;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.Kelime;

import java.util.*;

/**
 * basit kelime tabanli kok bulucu. simdilik sadece kelime frekansina gore siralama yapar.
 * (eger kelime frekans bilgisi mevcutsa)
 */
public class KelimeTabanliKokBulucu implements KokBulucu {

    KelimeCozumleyici cozumleyici;
    Alfabe alfabe;


    public KelimeTabanliKokBulucu(KelimeCozumleyici cozumleyici, Alfabe alfabe) {
        this.cozumleyici = cozumleyici;
        this.alfabe = alfabe;
    }

    /**
     * <b>EN:</b>Finds possible root words for an input word.
     * <p/>
     * <b>TR:</b>
     * girilen kelime icin olasi kelime koklerini Kok dizisi olarak dondurur.
     *
     * @param giris :  giris kelimesi
     * @return EN: root words as a Kok array.
     *         TR: girise uygun olabilecek kokler, Kok dizisi seklinde.
     */
    public Kok[] kokBul(String giris) {
        Kelime[] cozumler = cozumleVeSirala(giris);
        List<Kok> sonuclar = new ArrayList<Kok>(cozumler.length);
        for (Kelime kelime : cozumler) {
            if (!sonuclar.contains(kelime.kok()))
                sonuclar.add(kelime.kok());
        }
        return sonuclar.toArray(new Kok[sonuclar.size()]);
    }

    private Kelime[] cozumleVeSirala(String giris) {
        Kelime[] cozumler = cozumleyici.cozumle(giris);
        Arrays.sort(cozumler, new KelimeKokFrekansKiyaslayici());
        return cozumler;
    }

    /**
     * <b>EN:</b>Finds possible root words for an input word.
     * <p/>
     * <b>TR:</b>
     * girilen kelime icin olasi kelime koklerini String dizisi olarak dondurur.
     *
     * @param giris :  giris kelimesi
     * @return EN: root words as a String array.
     *         TR: girise uygun olabilecek kokler, String dizisi seklinde.
     */
    public String[] stringKokBul(String giris) {
        Kelime[] cozumler = cozumleVeSirala(giris);
        List<String> sonuclar = new ArrayList<String>(cozumler.length);
        for (Kelime kelime : cozumler) {
            String s = kelime.kok().asilIcerikUret(alfabe);
            if (!sonuclar.contains(s))
                sonuclar.add(s);
        }
        return sonuclar.toArray(new String[sonuclar.size()]);
    }
}
