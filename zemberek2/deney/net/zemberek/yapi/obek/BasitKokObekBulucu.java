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

package net.zemberek.yapi.obek;

import net.zemberek.yapi.Kok;

import java.util.List;
import java.util.ArrayList;

public class BasitKokObekBulucu {
    private KokObekDeposu depo;

    public BasitKokObekBulucu(KokObekDeposu depo) {
        this.depo = depo;
    }

    /**
     * bir dizi kok icerisindeki olasi kok obek dizilimlerini bir liste icerisinde dondurur.
     * Ornegin "eli kalem tutan yardim etsin dort goz gozu gormeyince"  cumlesindeki koklerin
     * dizi halinde gonderildigini ve tum kelime obeklerinin dpeoda bulundugu var sayilirsa asagidaki
     * kok obekleri doner:
     * " el, kalem, tutmak"
     * " yardim etmek "
     * " dort, goz "
     * " goz, goz, gormek "
     * dikkat edilirse sistem tum olasi kok obeklerini buluyor, "dort goz" ya da "goz gozu gormemek" icin
     * TODO: bu islem henuz ilkel ve efektif degil.
     * @param kokler
     * @return
     */
    public List<KokObegi> adayBul(Kok... kokler) {
        List<KokObegi> adaylar = new ArrayList<KokObegi>();
        for (int i = 0; i < kokler.length; i++) {
            if (kokler.length > 1 && i <= kokler.length - 2)
                for (int j = i + 1; j < kokler.length; j++) {
                    Kok[] testDizi = new Kok[j - i + 1];
                    System.arraycopy(kokler, i, testDizi, 0, j - i + 1);
                    KokObegi obek = new KokObegi(testDizi);
                    if (depo.var(obek))
                        adaylar.add(obek);
                }
        }
        return adaylar;
    }

    public static void main(String[] args) {

        Kok ka = new Kok("a") ;
        Kok kb = new Kok("b");
        Kok kc = new Kok("c");
        Kok kd = new Kok("d");
        Kok ke = new Kok("e");

        KokObekDeposu depo = new KokObekDeposu();
        depo.ekle(ka, kb);
        depo.ekle(kb, ka);
        depo.ekle(ka, kb, kc);
        depo.ekle(kb, kc);
        depo.ekle(kb, kc, kd, ke);
        depo.ekle(kd, ke);

        BasitKokObekBulucu bulucu = new BasitKokObekBulucu(depo);

        List<KokObegi> sonuc = bulucu.adayBul(ka,kb,kc,kd,ke);
        for (KokObegi obek : sonuc) {
            System.out.println("obek = " + obek);
        }
    }
}
