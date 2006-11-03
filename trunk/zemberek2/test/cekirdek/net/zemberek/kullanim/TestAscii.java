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
