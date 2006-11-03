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
