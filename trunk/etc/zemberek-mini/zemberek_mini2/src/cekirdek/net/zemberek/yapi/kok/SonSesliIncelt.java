package net.zemberek.yapi.kok;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;

/**
 * Bu islem sadece saat-ler turu ozel durumlarda kullanilir.
 */
public class SonSesliIncelt implements HarfDizisiIslemi {

    Alfabe alfabe;

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
            if (!dizi.harf(i).inceSesliMi())
                dizi.harfDegistir(i, alfabe.kalinSesliIncelt(dizi.harf(i)));
        }
    }
}
