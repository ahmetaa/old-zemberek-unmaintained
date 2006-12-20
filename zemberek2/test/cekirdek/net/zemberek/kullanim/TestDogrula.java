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
 * Created on 13.Eyl.2005
 *
 */
package net.zemberek.kullanim;

import net.zemberek.araclar.TimeTracker;
import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;

public class TestDogrula {

    private static Zemberek zemberek;

    public static void denetle(String str) {
        if (zemberek.kelimeDenetle(str) == true) {
            System.out.println(str + " Türkçe bir kelime");
        } else {
            System.out.println(str + " Türkçe deðil");
        }
    }

    public static void main(String[] args) {
        TimeTracker.startClock("x");
        zemberek = new Zemberek(new TurkiyeTurkcesi());
        System.out.println(TimeTracker.stopClock("x"));

        denetle("kediciklerin");
        denetle("Çekoslovakyalýlaþtýrabileceklerimizdenseniz");
        denetle("Mrhaba");
        denetle("yürekleniþiþebiliyordurlarýna");
    }
}
