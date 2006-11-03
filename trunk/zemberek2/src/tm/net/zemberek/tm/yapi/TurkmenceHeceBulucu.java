package net.zemberek.tm.yapi;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.HeceBulucu;

/**
 * User: ahmet
 * Date: Sep 22, 2006
 */
public class TurkmenceHeceBulucu implements HeceBulucu {

    private Alfabe alfabe;

    public TurkmenceHeceBulucu(Alfabe alfabe) {
        this.alfabe = alfabe;
    }

    public int sonHeceHarfSayisi(HarfDizisi dizi) {
        return -1;
    }
}
