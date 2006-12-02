/*
 * Created on 13.Eyl.2005
 *
 */
package net.zemberek.kullanim;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.ek.Ek;

import java.util.List;

public class TestCozumle {

    private static Zemberek zemberek;
    private static char I = Alfabe.CHAR_ii;
    private static char S = Alfabe.CHAR_ss;

    public static void cozumle(String str) {
        if (zemberek.kelimeDenetle(str) == true) {
            Kelime[] sonuc = zemberek.kelimeCozumle(str);
            System.out.println("Oluþan çözümleme sayýsý: " + sonuc.length);
            for (Kelime kelime: sonuc) {
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
            System.out.println(str + " Türkçe deðil");
        }
    }

    public static void main(String[] args) {
        zemberek = new Zemberek(new TurkiyeTurkcesi());
        cozumle("kedi");
        cozumle("kediciklerin");
        cozumle("getirttirebilirsiniz");
        cozumle("Çekoslovakyal"+I+"la"+S+"t"+I+"rabileceklerimizdenseniz");
        cozumle("Mrhaba");
    }
}
