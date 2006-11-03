/*
 * Created on 01.Nis.2004
 */
package net.zemberek.yapi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MDA
 */
public class Cumle {
    private ArrayList<Kelime> kelimeler = new ArrayList();

    public void addKelime(Kelime kelime) {
        kelimeler.add(kelime);
    }

    public List getKelimeler() {
        return kelimeler;
    }

    public String toString() {
        String str = "";
        for (Kelime kelime : kelimeler) {
            str += kelime.icerik() + " ";
        }
        return str;
    }
}
