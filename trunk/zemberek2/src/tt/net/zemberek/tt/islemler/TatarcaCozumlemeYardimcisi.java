/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tt.islemler;

import net.zemberek.islemler.cozumleme.CozumlemeYardimcisi;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.HarfDizisi;

/**
 * Created by IntelliJ IDEA.
 * User: ahmet
 * Date: Mar 19, 2007
 * Time: 10:23:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class TatarcaCozumlemeYardimcisi implements CozumlemeYardimcisi {

    private Alfabe alfabe;

    public TatarcaCozumlemeYardimcisi(Alfabe alfabe) {
        this.alfabe = alfabe;
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
