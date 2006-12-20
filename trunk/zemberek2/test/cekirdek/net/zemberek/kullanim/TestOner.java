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

/*
 * Created on 13.Eyl.2005
 *
 */
package net.zemberek.kullanim;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.ek.Ek;

import java.util.List;

public class TestOner {

    private static Zemberek zemberek;

    public static void cozumle(String str) {
        if (zemberek.kelimeDenetle(str) == true) {
            Kelime[] sonuc = zemberek.kelimeCozumle(str);
            System.out.println("Oluþan çözümleme sayýsý: " + sonuc.length);
            for (Kelime kelime : sonuc) {
                Kok kok = kelime.kok();
                System.out.println("Kok :" + kok.icerik() + " Tipi : " + kok.tip().toString());
                List ekler = kelime.ekler();
                if (ekler != null) {
                    System.out.println("Ekler:");
                    for (int j = 0; j < ekler.size(); j++) {
                        Ek ek = (Ek) ekler.get(j);
                        System.out.println("Ek-" + j + " : " + ek.ad());
                    }
                }
            }
            System.out.println();
        } else {
            String[] oneriler = zemberek.oner(str);
            if (oneriler.length==0) {
                System.out.println(str + " Türkçe deðil, Öneri üretiliyor:");
                for (int i = 0; i < oneriler.length; i++) {
                    System.out.println("Oneri-" + " : " + oneriler[i]);
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        zemberek = new Zemberek(new TurkiyeTurkcesi());
        cozumle("Mrhaba");
        cozumle("teknolokiler");
        cozumle("seil");
    }
}
