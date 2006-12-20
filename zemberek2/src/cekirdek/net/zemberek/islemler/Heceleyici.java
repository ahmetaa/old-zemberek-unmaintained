/*
 *  ***** BEGIN LICENSE BLOCK *****
 *  Version: MPL 1.1
 *
 *  The contents of this file are subject to the Mozilla Public License Version
 *  1.1 (the "License"); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an "AS IS" basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 *  for the specific language governing rights and limitations under the
 *  License.
 *
 *  The Original Code is Zemberek Doðal Dil Ýþleme Kütüphanesi.
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akýn, Mehmet D. Akýn.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.islemler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.HeceBulucu;

/**
 * Heceleyici
 * User: aakin Date: Mar 6, 2004
 */
public class Heceleyici {

    private final Alfabe alfabe;
    private final HeceBulucu heceBulucu;

    public Heceleyici(Alfabe alfabe, HeceBulucu heceBulucu) {
        this.alfabe = alfabe;
        this.heceBulucu = heceBulucu;
    }

    /**
     * Gelen String'i turkce heceleme kurallarina gore hecelerine ayirir. Sonucta
     * heceleri bir liste icinde dondurur. Eger heceleme yapilamazsa bos liste doner.
     *
     * @param giris
     * @return sonHeceHarfSayisi String dizisi
     */
    public String[] hecele(String giris) {
        giris = alfabe.ayikla(giris);
        HarfDizisi kelime = new HarfDizisi(giris, alfabe);
        List<String> list = new ArrayList();
        while (kelime.length() > 0) {
            int index = heceBulucu.sonHeceHarfSayisi(kelime);
            if (index < 0) {
                list.clear();
                return new String[0];
            }
            int basla = kelime.length() - index;
            list.add(kelime.toString(basla));
            kelime.kirp(basla);
        }
        Collections.reverse(list);
        return list.toArray(new String[list.size()]);
    }

    /**
     * girisin hecelenebir olup olmadigini bulur.
     *
     * @param giris
     * @return hecelenebilirse true, aksi halde false.
     */
    public boolean hecelenebilirmi(String giris) {
        HarfDizisi kelime = new HarfDizisi(giris, alfabe);
        while (kelime.length() > 0) {
            int index = heceBulucu.sonHeceHarfSayisi(kelime);
            if (index < 0)
                return false;
            int basla = kelime.length() - index;
            kelime.kirp(basla);
        }
        return true;
    }

    /**
     * Verilen kelime için sonHeceHarfSayisi indekslerini bir dizi içinde döndürür
     *
     * @param giris : Hece indeksleri belirlenecek
     * @return Hece indekslerini tutan bir int[]
     *         Örnek: "merhaba" kelimesi için 0,3,5
     *         "türklerin" kelimesi için 0,4,6
     */
    public int[] heceIndeksleriniBul(String giris) {
        giris = alfabe.ayikla(giris);
        HarfDizisi kelime = new HarfDizisi(giris, alfabe);
        int[] tmpHeceIndeksleri = new int[50];
        int heceIndeks = 0;
        while (kelime.length() > 0) {
            int index = heceBulucu.sonHeceHarfSayisi(kelime);
            if (index < 0) {
                return null;
            }
            int basla = kelime.length() - index;
            tmpHeceIndeksleri[heceIndeks++] = basla;
            if (heceIndeks > 50) return null;
            kelime.kirp(basla);
        }
        int[] heceIndeksleri = new int[heceIndeks];
        for (int i = 0; i < heceIndeks; i++) {
            heceIndeksleri[i] = tmpHeceIndeksleri[heceIndeks - i - 1];
        }
        return heceIndeksleri;
    }
}