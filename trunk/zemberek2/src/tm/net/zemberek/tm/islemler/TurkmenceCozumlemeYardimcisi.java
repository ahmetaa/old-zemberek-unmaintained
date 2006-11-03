package net.zemberek.tm.islemler;

import net.zemberek.islemler.DenetlemeCebi;
import net.zemberek.islemler.cozumleme.CozumlemeYardimcisi;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;

/**
 * Bu sinif Turkiye Turkcesine ozel cesitli islemleri icerir.
 * User: ahmet
 * Date: Sep 11, 2005
 */
public class TurkmenceCozumlemeYardimcisi implements CozumlemeYardimcisi {

    private Alfabe alfabe;
    private DenetlemeCebi cep;

    public TurkmenceCozumlemeYardimcisi(Alfabe alfabe,
                                        DenetlemeCebi cep) {
        this.alfabe = alfabe;
        this.cep = cep;
    }

    public void kelimeBicimlendir(Kelime kelime) {
    }

    public boolean kelimeBicimiDenetle(Kelime kelime, String giris) {
        return giris.length() != 0;
    }

    public boolean kokGirisDegismiVarsaUygula(Kok kok, HarfDizisi kokDizi, HarfDizisi girisDizi) {
        return false;
    }

    public boolean cepteAra(String str) {
        return false;
    }
}
