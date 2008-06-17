/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.zemberek.araclar.turkce.YaziBirimi;
import net.zemberek.araclar.turkce.YaziBirimiTipi;
import net.zemberek.araclar.turkce.YaziIsleyici;
import net.zemberek.islemler.cozumleme.KelimeCozumleyici;

/**
 */
public class TurkceYaziTesti {
    private static Logger log = Logger.getLogger("TurkceYaziTesti.class");
    KelimeCozumleyici cozumleyici, asciiCozumleyici;
    public static final int HIC = 0;
    public static final int AZ = 1;
    public static final int ORTA = 2;
    public static final int YUKSEK = 3;
    public static final int KESIN = 4;

    public TurkceYaziTesti(KelimeCozumleyici cozumleyici, KelimeCozumleyici asciiCozumleyici) {
        this.cozumleyici = cozumleyici;
        this.asciiCozumleyici = asciiCozumleyici;
    }

    private double turkceOranla(String yazi) {
        int cozulenler = 0, asciiCozulenler = 0, cozulemeyenler = 0;
        List<YaziBirimi> analizDizisi = YaziIsleyici.analizDizisiOlustur(yazi);
        for (int i = 0; i < analizDizisi.size(); i++) {
            YaziBirimi birim = (YaziBirimi) analizDizisi.get(i);
            if (birim.tip == YaziBirimiTipi.KELIME) {
                if (cozumleyici.cozumlenebilir(birim.icerik))
                    cozulenler++;
                else if (asciiCozumleyici.cozumlenebilir(birim.icerik))
                    asciiCozulenler++;
                else
                    cozulemeyenler++;
            }
        }
        int toplam = cozulenler + asciiCozulenler;
        if (toplam == 0 || (toplam + cozulemeyenler) == 0)
            return 0.0d;
        if (cozulemeyenler == 0)
            return 1.0d;
        double sonuc = 1.0d - (double) cozulemeyenler / (double) (toplam + cozulemeyenler);
        if (log.isLoggable(Level.FINER))
            log.finer("cozulenler:" + cozulenler + "  ascii Cozulenler:" + asciiCozulenler
                    + "cozulemeyenler:" + cozulemeyenler + "  oran:" + sonuc);
        return sonuc;
    }

    public int turkceTest(String giris) {
        double sonuc = turkceOranla(giris);
        if (sonuc <= 0.1d)
            return HIC;
        if (sonuc > 0.1d && sonuc <= 0.35d)
            return AZ;
        if (sonuc > 0.35d && sonuc <= 0.65d)
            return ORTA;
        if (sonuc > 0.65d && sonuc <= 0.95d)
            return YUKSEK;
        return KESIN;
    }


}
