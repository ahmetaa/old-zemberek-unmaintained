/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
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
