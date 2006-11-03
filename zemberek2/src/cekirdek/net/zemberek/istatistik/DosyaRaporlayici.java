/*
 * Created on 19.Haz.2005
 *
 */
package net.zemberek.istatistik;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class DosyaRaporlayici extends TemelRaporlayici {
    FileOutputStream fos = null;

    public DosyaRaporlayici(Istatistikler istatistikler, String dosyaAdi) {
        this.istatistikler = istatistikler;
        try {
            fos = new FileOutputStream(dosyaAdi);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void raporla() {
        this.raporla(fos, "UTF-8");
    }
}
