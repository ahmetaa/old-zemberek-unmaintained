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

import net.zemberek.araclar.TimeTracker;
import net.zemberek.araclar.turkce.TurkceMetinOkuyucu;
import net.zemberek.erisim.Zemberek;
import net.zemberek.istatistik.Istatistikler;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;

public class TestPerformans {

    private static Zemberek zemberek = new Zemberek(new TurkiyeTurkcesi());

    private static Istatistikler istatistikler = new Istatistikler(zemberek.dilBilgisi());

    public static void denetle(String dosya) {
        TimeTracker.startClock("perf");
        System.out.println("Okunan dosya: " + dosya);
        TurkceMetinOkuyucu tmo = new TurkceMetinOkuyucu();
        tmo.setStatistics(istatistikler);
        String[] yazi = tmo.MetinOku(dosya);
        int dogrular = 0;
        int yanlislar = 0;
        for (int i = 0; i < yazi.length; i++) {
            boolean sonuc = zemberek.kelimeDenetle(yazi[i]);
            if (sonuc == true) {
                dogrular ++;
            } else {
                yanlislar ++;
            }
        }
        System.out.println("Dogrular: " + dogrular + " Yanlislar: " + yanlislar);
        System.out.println(TimeTracker.getElapsedTimeString("perf"));
        System.out.println("Saniyede : " + TimeTracker.getItemsPerSecond("perf", yazi.length) + " denetleme");
        TimeTracker.stopClock("perf");
    }

    public static void cozumle(String dosya) {
        TimeTracker.startClock("perf");
        System.out.println("Okunan dosya: " + dosya);
        TurkceMetinOkuyucu tmo = new TurkceMetinOkuyucu();
        tmo.setStatistics(istatistikler);
        String[] yazi = tmo.MetinOku(dosya);
        for (int i = 0; i < yazi.length; i++) {
            zemberek.kelimeCozumle(yazi[i]);
        }
        System.out.println(TimeTracker.getElapsedTimeString("perf"));
        System.out.println("Saniyede : " + TimeTracker.getItemsPerSecond("perf", yazi.length) + " cozumleme");
        TimeTracker.stopClock("perf");
    }

    public static void hecele(String dosya) {
        TimeTracker.startClock("perf");
        System.out.println("Okunan dosya: " + dosya);
        TurkceMetinOkuyucu tmo = new TurkceMetinOkuyucu();
        tmo.setStatistics(istatistikler);
        String[] yazi = tmo.MetinOku(dosya);
        for (int i = 0; i < yazi.length; i++) {
            zemberek.hecele(yazi[i]);
        }
        System.out.println(TimeTracker.getElapsedTimeString("perf"));
        System.out.println("Saniyede : " + TimeTracker.getItemsPerSecond("perf", yazi.length) + " heceleme");
        TimeTracker.stopClock("perf");
    }


    public static void main(String[] args) {
        denetle("kitap/Tolstoy_Dirilis.txt");
        cozumle("kitap/Tolstoy_Dirilis.txt");
        hecele("kitap/Tolstoy_Dirilis.txt");

    }
}
