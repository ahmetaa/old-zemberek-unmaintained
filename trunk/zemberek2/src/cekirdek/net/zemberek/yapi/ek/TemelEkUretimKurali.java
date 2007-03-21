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

import java.util.*;

/**
 * Turk dilleri icin cesitli uretim kurallarini belirler. Bazi kurallar sadece belli dillerde
 * kullanilir.
 */
public enum TemelEkUretimKurali implements EkUretimKurali {
    SESLI_AE,
    SESLI_AA,
    SESLI_IU,
    SESSIZ_Y,
    SERTLESTIR,
    KAYNASTIR,
    HARF;

    // ek uretim kural kelimesinde kullanilan parcalarin dilbilgisi kurali karsiliklarini tutan tablo.
    private static final Map<Character, EkUretimKurali> kuralTablosu = new HashMap();

    static {
        kuralTablosu.put('A', SESLI_AE);
        kuralTablosu.put('I', SESLI_IU);
        kuralTablosu.put('E', SESLI_AA);
        kuralTablosu.put('Y', SESSIZ_Y);
        kuralTablosu.put('+', KAYNASTIR);
        kuralTablosu.put('>', SERTLESTIR);
    }

    private static final Set<Character> sesliKurallari =
            new HashSet<Character>(Arrays.asList('A', 'I', 'E', 'Y'));
    private static final Set<Character> harfKurallari =
            new HashSet<Character>(Arrays.asList('+', '>', '@'));

    public static class TemelKuralBilgisi implements EkKuralBilgisi {

        public Set<Character> sesliKuralKarakterleri() {
            return sesliKurallari;
        }

        public Set<Character> harfKuralKarakterleri() {
            return harfKurallari;
        }

        public Map<Character, EkUretimKurali> karakterKuralTablosu() {
            return kuralTablosu;
        }
        
        public EkUretimKurali harfEklemeKurali() {
            return HARF;
        }
    }

}
