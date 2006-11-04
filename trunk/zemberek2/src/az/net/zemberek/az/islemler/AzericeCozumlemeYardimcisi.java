package net.zemberek.az.islemler;

import net.zemberek.islemler.DenetlemeCebi;
import net.zemberek.islemler.cozumleme.CozumlemeYardimcisi;
import net.zemberek.yapi.*;


/**
 * Bu sinif Turkiye Turkcesine ozel cesitli islemleri icerir.
 * User: ahmet
 * Date: Sep 11, 2005
 */
public class AzericeCozumlemeYardimcisi implements CozumlemeYardimcisi {

    private Alfabe alfabe;
    private DenetlemeCebi cep;

    public AzericeCozumlemeYardimcisi(Alfabe alfabe, DenetlemeCebi cep) {
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
