/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tr.yapi.kok;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.kok.HarfDizisiIslemi;

/**
 * Turkcede 'nk' ile biten bazi koklere sert sesli eklendiginde sonraki k yumusak g'ye degil g harfine donusur.
 * 'cenk-cenge' 'denk-dengi' 'Celenk-celenge' gibi.
 */
public class YumusamaNk implements HarfDizisiIslemi {

    private final HarfDizisi NK;
    private Alfabe alfabe;


    public YumusamaNk(Alfabe alfabe) {
        this.alfabe = alfabe;
        NK = new HarfDizisi("nk", alfabe);
    }

    public void uygula(HarfDizisi dizi) {
        if (dizi.aradanKiyasla(dizi.length() - 2, NK))
            dizi.harfDegistir(dizi.length() - 1, alfabe.harf('g'));
    }
}
