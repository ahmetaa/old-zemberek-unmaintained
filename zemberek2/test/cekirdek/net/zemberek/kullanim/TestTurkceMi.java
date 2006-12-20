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

package net.zemberek.kullanim;

import net.zemberek.erisim.Zemberek;
import net.zemberek.islemler.TurkceYaziTesti;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;

public class TestTurkceMi {

    private static Zemberek zemberek;

    public static void turkceMi(String str) {
        System.out.print("Türkçe testi yapýlan metin: " + str + " Sonuç : ");
        int sonuc = zemberek.dilTesti(str);
        switch (sonuc) {
            case TurkceYaziTesti.KESIN:
                System.out.println("Türkçe");
                break;
            case TurkceYaziTesti.YUKSEK:
                System.out.println("Yüksek oranda Türkçe");
                break;
            case TurkceYaziTesti.ORTA:
                System.out.println("Kýsmen Türkçe");
                break;
            case TurkceYaziTesti.AZ:
                System.out.println("Çok az Türkçe kelime içeriyor");
                break;
            case TurkceYaziTesti.HIC:
                System.out.println("Türkçe deðil");
                break;
        }
    }

    public static void main(String[] args) {
        zemberek = new Zemberek(new TurkiyeTurkcesi());
        turkceMi("Bugün çok neþeliyim, hayat çok güzel");
        turkceMi("Abi yaptýðýmýz son commitler nasty regresyonlara yol açmýþ. we are doomed yani.");
        turkceMi("Bugun cok neseliyim, hayat cok guzel");
        turkceMi("Obviously Java kicks some serious butts nowadays.");
    }
}
