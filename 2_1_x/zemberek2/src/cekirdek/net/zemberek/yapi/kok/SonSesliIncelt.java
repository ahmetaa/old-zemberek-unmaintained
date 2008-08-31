/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.kok;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.TurkceHarf;

/**
 * Bu islem sadece saat-ler turu ozel durumlarda kullanilir.
 */
public class SonSesliIncelt implements HarfDizisiIslemi {

    private final Alfabe alfabe;

    public SonSesliIncelt(Alfabe alfabe) {
        this.alfabe = alfabe;
    }

    /**
     * en son kalin sesli harfi bulup onu ince formu ile degistirir.
     * ornegin saat -> saAt haline donusur. ince a harfi icin TurkceAlfabe sinifini inceleyin
     *
     * @param dizi
     */
    public void uygula(HarfDizisi dizi) {
        for (int i = dizi.length() - 1; i >= 0; i--) {
            final TurkceHarf h = dizi.harf(i);
            if (h.sesliMi() && !h.inceSesliMi())
                dizi.harfDegistir(i, alfabe.kalinSesliIncelt(dizi.harf(i)));
        }
    }
}
