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
 *    Mert
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.tr.yapi;

import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Heceleyici;

import java.util.*;

/**
 * User: ahmet
 * Date: Sep 10, 2005
 */
public class DenemeTurkceHeceleyici implements Heceleyici {

    /**
     * Henuz hecelenememe durumu cozumsuz..
     * @param kelime
     * @return
     */

    public List<String> hecele(HarfDizisi kelime) {
        ArrayList<String> heceler = new ArrayList();
        final int sesliSayisi = kelime.sesliSayisi();
        Set<Integer> heceyapicilar = new HashSet(sesliSayisi);

        final int boy = kelime.length();
        for (int i = 0; i < boy - 1; i++) {
            if ((!kelime.harf(i).sesliMi() && kelime.harf(i + 1).sesliMi())
                    || (kelime.harf(i).sesliMi() && i > 0 && kelime.harf(i - 1).sesliMi())) {
                heceyapicilar.add(i);
            }
        }

        int sonNokta = boy;
        for (int i = boy - 1; i > -1; i--) {
            if (heceyapicilar.contains(i)) {
                heceyapicilar.remove(i);
                if (heceyapicilar.size() == 0 && kelime.araDizi(0, boy - (boy - i)).sesliSayisi() == 0) {
                    heceler.add(kelime.subSequence(0, boy - (boy - sonNokta)).toString());
                    sonNokta = 0;
                } else {
                    heceler.add(kelime.subSequence(i, boy - (boy - sonNokta)).toString());
                    sonNokta = i;
                }
            }
        }
        if (sonNokta != 0) {
            heceler.add(kelime.subSequence(0, boy - (boy - sonNokta)).toString());
        }
        Collections.reverse(heceler);
        return heceler;
    }


}
