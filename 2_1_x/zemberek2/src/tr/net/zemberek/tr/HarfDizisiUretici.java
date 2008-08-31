/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tr;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;

/**
 * User: ahmet
 * Date: May 4, 2006
 */
public class HarfDizisiUretici {

    Alfabe alfabe;

    public HarfDizisiUretici(Alfabe alfabe) {
        this.alfabe = alfabe;
    }

    public final HarfDizisi uret(String str) {
        return new HarfDizisi(str, alfabe);
    }
}
