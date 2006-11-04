package net.zemberek.az.yapi;

import net.zemberek.yapi.HeceBulucu;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Alfabe;


public class AzericeHeceBulucu implements HeceBulucu {

    Alfabe alfabe;


    public AzericeHeceBulucu(Alfabe alfabe) {
        this.alfabe = alfabe;
    }

    public int sonHeceHarfSayisi(HarfDizisi dizi) {
        return 0;
    }
}
