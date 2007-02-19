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

package net.zemberek.bilgi.kokler;

import java.util.ArrayList;
import java.util.List;

import net.zemberek.araclar.MetinAraclari;
import net.zemberek.yapi.Kok;

/**
 * Hata toleranslı kök bulucu hatalı girişler için öneri üretmekte kullanılır.
 * <p/>
 * Ağacın "e" harfi ile başlayan kolu aşağıdaki gibi olsun:
 * <p/>
 * <pre>
 * e
 * |---l(el)
 * |  |---a(ela)
 * |  |  |--s-(elastik)
 * |  |
 * |  |---b
 * |  |  |--i-(elbise)
 * |  |
 * |  |---m
 * |  |  |--a(elma)
 * |  |  |  |--c-(elmacık)
 * |  |  |  |--s-(elmas)
 * |  | ...
 * | ...
 * ...
 * </pre>
 * <p/>
 * "elm" girişi için ağaç üzerinde ilerlerken hata mesafesi 1 olduğu müddetçe 
 * ilerlenir. bu sırada "el, ela, elma" kökleri toplanır.
 *  @author MDA
 */
public class ToleransliKokAdayiBulucu implements KokAdayiBulucu {

    private KokAgaci agac = null;
    private int tolerans = 0;
    private int distanceCalculationCount = 0;

    public int getDistanceCalculationCount() {
        return distanceCalculationCount;
    }

    public ToleransliKokAdayiBulucu(KokAgaci agac, int tolerans) {
        this.agac = agac;
        this.tolerans = tolerans;
    }

    public List<Kok> getAdayKokler(String giris) {
        return benzerKokleriBul(giris);
    }

    private String giris = null;
    private List<Kok> adaylar = null;

    private List<Kok> benzerKokleriBul(String giris) {
        this.giris = giris;
        adaylar = new ArrayList<Kok>();
        yuru(agac.getKokDugumu(), "");
        return adaylar;
    }

    private void yuru(KokDugumu dugum, String olusan) {
        String tester = olusan;
        tester += dugum.getHarf();
        if (dugum.getKok() != null) {
            distanceCalculationCount++;
            if (MetinAraclari.parcasiDuzeltmeMesafesiIcinde((String) dugum.getKelime(), giris, tolerans)) {
            	// Aday kök bulundu
                adaylar.add(dugum.getKok());
            } else {
                // Mesafe sınırı aşıldı.
                return;
            }
        } else {
            if (!MetinAraclari.parcasiDuzeltmeMesafesiIcinde(tester.trim(), giris, tolerans)) {
            	// Ara stringde mesafe sınırı aşıldı
                return;
            }
        }

        for (KokDugumu altDugum : dugum.altDugumDizisiGetir()) {
            if (altDugum != null) {
                this.yuru(altDugum, tester);
            }
        }
    }

}