/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.kullanim;

import net.zemberek.araclar.TimeTracker;
import net.zemberek.araclar.turkce.TurkceMetinOkuyucu;
import net.zemberek.erisim.Zemberek;
import net.zemberek.istatistik.DosyaRaporlayici;
import net.zemberek.istatistik.HeceIstatistikleri;
import net.zemberek.istatistik.Istatistikler;
import net.zemberek.istatistik.KonsolRaporlayici;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.Kelime;

public class TestIstatistik {

    private static Zemberek zemberek;
    private static Istatistikler istatistikler;

    public static void istatistikCikar(String dosya) {
        System.out.println("Okunan dosya: " + dosya);
        TurkceMetinOkuyucu tmo = new TurkceMetinOkuyucu();
        tmo.setStatistics(istatistikler);
        HeceIstatistikleri heceIst = istatistikler.getHeceIstatistikleri();
        String[] yazi = tmo.MetinOku(dosya);

        int kelimesayisi = 0;
        int eksayisi = 0;

        for (String s : yazi) {
            Kelime[] kelimeler = zemberek.kelimeCozumle(s);

            if (kelimeler == null || kelimeler.length == 0)
                continue;

            final Kelime ilk = kelimeler[0];
            istatistikler.kokIstatistikGuncelle(ilk.kok(), ilk);
            istatistikler.kelimeIstatistikGuncelle(ilk);
            istatistikler.ekIstatistikleriGuncelle(ilk);
            istatistikler.ikiliIstatistikGuncelle(ilk);
            istatistikler.getIkiliHarfIstatistikleri();
            String[] heceler = zemberek.hecele(s);
            for (String hece : heceler) {
                heceIst.guncelle(hece);
            }
            kelimesayisi++;
            eksayisi += ilk.ekler().size();
        }
        System.out.println("Kelime sayisi:"+kelimesayisi);
        System.out.println("Ekler+Kok sayisi:"+eksayisi);
        System.out.format("%nOran:%.4f%n", (double)eksayisi /  (double)kelimesayisi );
        System.out.println(TimeTracker.getElapsedTimeString("ist"));
    }

    public static void raporla() {
        istatistikler.sonlandir();
        istatistikler.setLimit(250,250,250);
        KonsolRaporlayici konsolRaporlayici = new KonsolRaporlayici(istatistikler);
        konsolRaporlayici.raporla(System.out, "UTF-8");
        DosyaRaporlayici dr = new DosyaRaporlayici(istatistikler, "istatistik.txt");
        dr.raporla();
    }

    public static void main(String[] args) {
        zemberek = new Zemberek(new TurkiyeTurkcesi());
        istatistikler = new Istatistikler(zemberek.dilBilgisi());
        TimeTracker.startClock("ist");
        //istatistikCikar("text/yazili_havuz.txt");
        istatistikCikar("kaynaklar/tr/metinler/buzyeli_vadisi_2.txt");
        //istatistikCikar("kitap/tolstoy_cocukluk_ve_genclik_yillari.txt");
        //istatistikCikar("kitap/Tolstoy_Dirilis.txt");
        raporla();
        TimeTracker.stopClock("ist");
    }
}
