package net.zemberek.yapi.kok;

import net.zemberek.yapi.HarfDizisi;

/**
 * son harfi yumusatir. normal sert harf yumusama kurali gecerlidir.
 */
public class Yumusama implements HarfDizisiIslemi {

    public void uygula(HarfDizisi dizi) {
        dizi.sonHarfYumusat();
    }

}
