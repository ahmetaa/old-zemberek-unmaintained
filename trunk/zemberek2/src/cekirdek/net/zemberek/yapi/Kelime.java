/*
 *  ***** BEGIN LICENSE BLOCK *****
 *
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
 *  The Original Code is "Zemberek Dogal Dil Isleme Kutuphanesi"
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akin, Mehmet D. Akin.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.yapi;

import net.zemberek.yapi.ek.Ek;

import java.util.ArrayList;
import java.util.List;

public class Kelime implements Cloneable {

    private static final HarfDizisi BOS_ICERIK = new HarfDizisi(0);
    private HarfDizisi icerik=BOS_ICERIK;
    private Kok kok;
    private List<Ek> ekler = new ArrayList<Ek>(3);
    private KelimeTipi tip;

    public Kelime() {
    }

    public Ek[] ekDizisi() {
        return (Ek[]) ekler.toArray();
    }

    public Kelime clone() {
        Kelime kopya = new Kelime();
        //kok'u kopyalamaya gerek yok. referans esitlemesi yeter
        kopya.kok = kok;
        kopya.icerik = new HarfDizisi(icerik);
        kopya.ekler = new ArrayList(ekler);
        kopya.tip = this.tip;
        return kopya;
    }

    public List<Ek> ekler() {
        return ekler;
    }

    /**
     * Gosterim amacli bir metod. ek zincirinin anlasilir sekilde yazilmasini saglar.
     *
     * @return ek zinciri dizisi yazimi.
     */
    public String ekZinciriStr() {
        StringBuilder buf = new StringBuilder();
        for (Ek ek : ekler) {
            buf.append(ek.ad()).append(", ");
        }
        if (buf.length() > 2)
            buf.delete(buf.length() - 2, buf.length());
        return buf.toString();
    }

    public Kelime(Kok kok, Alfabe alfabe) {
        this.kok = kok;
        icerik = new HarfDizisi(kok.icerik(), alfabe);
        tip = kok.tip();
    }

    public Kelime(Kok kok, HarfDizisi dizi) {
        this.kok = kok;
        this.icerik = dizi;
        tip = kok.tip();
    }

    public void setIcerik(HarfDizisi icerik) {
        this.icerik = icerik;
    }

    public int ekSayisi() {
        return ekler.size();
    }

    public TurkceHarf sonHarf() {
        return icerik.sonHarf();
    }

    public HarfDizisi icerik() {
        return icerik;
    }

    public int boy() {
        return icerik.length();
    }

    public Ek sonEk() {
        return ekler.get(ekler.size() - 1);
    }

    public String icerikStr() {
        return icerik.toString();
    }

    public void ekEkle(Ek ek) {
        ekler.add(ek);
    }

    public Kok kok() {
        return kok;
    }

    public String toString() {
        StringBuilder str = new StringBuilder(" [ Kok: " + kok.icerik() + ", " + kok.tip() + " ] ");
        if(ekler.size()>1)
           str.append(" Ekler: ");
        for (int i = 1; i < ekler.size(); i++) {
            str.append(ekler.get(i).ad());
            if(i<ekler.size()-1)
              str.append(" + ");
        }
        return str.toString();
    }

    public void icerikEkle(HarfDizisi eklenecek) {
        icerik.ekle(eklenecek);
    }

    /**
     * Kelime icerisinde sadece kok ya da kok tipini belirten baslangic eki var ise bu metod
     * true dondurur. Eger baska bir ek eklenmis ise true doner.
     * @return
     */
    public boolean gercekEkYok() {
        return ekler.size()<2;
    }
}
