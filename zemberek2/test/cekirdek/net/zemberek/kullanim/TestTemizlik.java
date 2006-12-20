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
 *  The Original Code is Zemberek Doğal Dil İşleme Kütüphanesi.
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akın, Mehmet D. Akın.
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

public class TestTemizlik {

    private static Zemberek zemberek;

    public static void temizle(String str) {
        System.out.println("�nceki hali: " + str);
        System.out.println("Temizlenmi� hali: " + zemberek.temizle(str));
    }

    public static void main(String[] args) {
        zemberek = new Zemberek(new TurkiyeTurkcesi());
        String str = "Bu yazıyı windows cp-1254 isimli ucube kod sayfasını kullanarak "
                + "yazıyorum. Bakalım kod sayfası farklarından kaynaklanan "
                + " problemler için güzel bir örnek oluşturabilecek mi?";
        temizle(str);
    }

}
