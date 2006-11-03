/*
 * Created on 11.Eyl.2005
 *
 */
package net.zemberek.kullanim;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;


public class TestHecele {

    public static void heceYaz(String[] heceler) {
        for (int i = 0; i < heceler.length; i++) {
            System.out.print(heceler[i]);
            if (i < heceler.length - 1) {
                System.out.print(" - ");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Zemberek zemberek = new Zemberek(new TurkiyeTurkcesi());
        heceYaz(zemberek.hecele("Merhaba"));
        heceYaz(zemberek.hecele("javacýlardanmýþ"));
        heceYaz(zemberek.hecele("Türklerin"));
    }
}
