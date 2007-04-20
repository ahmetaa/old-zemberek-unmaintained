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

import net.zemberek.yapi.Alfabe;

import java.util.*;

/**
 * Ek bilgi dosyasinda yer alan ek kural kelimeinin cozumlenmesinde kullanilir.
 *
 * Basit bir tokenizer. Kural kelimesini dile gore ozel kural bilgilerini kullanarak
 * EkUretimBileseni listesine donusturur.
 */
public class EkKuralKelimesiCozumleyici {

    private Alfabe alfabe;
    /**
     * Ek kural bilgisi nesnesi dile ozel ek kural kelime enum sinifindan elde edilir.
     */
    private EkKuralBilgisi ekKuralBilgisi;


    public EkKuralKelimesiCozumleyici(Alfabe alfabe, EkKuralBilgisi ekKuralBilgisi) {
        this.alfabe = alfabe;
        this.ekKuralBilgisi = ekKuralBilgisi;
    }

    public List<EkUretimBileseni> cozumle(String uretimKelimesi) {
        if (uretimKelimesi == null || uretimKelimesi.length() == 0)
            return Collections.emptyList();
        List<EkUretimBileseni> bilesenler = new ArrayList<EkUretimBileseni>();
        Iterator<EkUretimBileseni> it = new BilesenIterator(uretimKelimesi.trim().replaceAll("[ ]", ""));
        while (it.hasNext())
            bilesenler.add(it.next());
        return bilesenler;
    }

    class BilesenIterator implements Iterator<EkUretimBileseni> {

        private int pointer;
        private final String uretimKelimesi;


        public BilesenIterator(String uretimKelimesi) {
            this.uretimKelimesi = uretimKelimesi;
        }

        public boolean hasNext() {
            return uretimKelimesi != null && pointer < uretimKelimesi.length();
        }

        public EkUretimBileseni next() {
            if (!hasNext()) {
                throw new NoSuchElementException("bilesen kalmadi!");
            }
            char p = uretimKelimesi.charAt(pointer++);
            //ardisil harf ile iliskili kuralmi
            if (ekKuralBilgisi.harfKuralKarakterleri().contains(p)) {
                if (pointer == uretimKelimesi.length())
                    throw new IllegalArgumentException(p + " kuralindan sonra normal harf bekleniyordu!");
                char h = uretimKelimesi.charAt(pointer++);
                if (ekKuralBilgisi.sesliKuralKarakterleri().contains(h))
                    throw new IllegalArgumentException(p + " kuralindan sonra sesli uretim kurali gelemez:" + h);
                return new EkUretimBileseni(ekKuralBilgisi.karakterKuralTablosu().get(p), alfabe.harf(h));
            } else if (ekKuralBilgisi.sesliKuralKarakterleri().contains(p)) {
                return new EkUretimBileseni(ekKuralBilgisi.karakterKuralTablosu().get(p), Alfabe.TANIMSIZ_HARF);
            } else if (alfabe.harf(p) != null && Character.isLowerCase(p)) {
                return new EkUretimBileseni(ekKuralBilgisi.harfEklemeKurali(), alfabe.harf(p));
            } else {
                throw new IllegalArgumentException(p + "  simgesi cozumlenemiyor.. kelime:" + uretimKelimesi);
            }
        }

        public void remove() {
        }
    }
}