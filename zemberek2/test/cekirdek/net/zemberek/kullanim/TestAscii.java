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

package net.zemberek.kullanim;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.Kelime;

public class TestAscii {

    private static Zemberek zemberek;

    public static void asciiYap(String str) {
        System.out.println(str + " -> " + zemberek.asciiyeDonustur(str));
    }

    public static void asciidenTurkceye(String str) {
       Kelime[] adaylar = zemberek.asciiCozumle(str);
        System.out.print(str + " -> ");
        for (int i = 0; i < adaylar.length; i++) {
            System.out.print(adaylar[i].icerik().toString());
            if (i < adaylar.length - 1) {
                System.out.print(" - ");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        zemberek = new Zemberek(new TurkiyeTurkcesi());
        asciiYap("þebek");
        asciiYap("þaþýrtmýþ");
        asciiYap("düðümsüzlükmüþ");

        asciidenTurkceye("sebek");
        asciidenTurkceye("sasirtmis");
        asciidenTurkceye("dugumsuzlukmus");

        // Belirsizlik
        asciidenTurkceye("siraci");
        asciidenTurkceye("olmus");
    }
}
