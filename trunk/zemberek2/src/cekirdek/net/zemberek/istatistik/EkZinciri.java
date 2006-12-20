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

/*
 * Created on 29.Haz.2004
 */
package net.zemberek.istatistik;

import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.EkYonetici;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MDA & GBA
 */
public class EkZinciri implements Comparable {
    List ekler = null;
    double kullanimFrekansi = 0.0d;
    int kullanimSayisi;
    String eklerStr = "";

    public EkZinciri() {
        ekler = new ArrayList();
    }

    /**
     * @param ekler
     */
    public EkZinciri(List ekler) {
        StringBuffer buf = new StringBuffer();
        this.ekler = ekler;
        for (int i = 0; i < ekler.size(); i++) {
            buf.append(((Ek) ekler.get(i)).ad());
            //buf.append(' ');
        }
        kullanimSayisi = 0;
        kullanimFrekansi = 0;
        eklerStr = buf.toString();
    }

    /**
     * Strin olarak verilen bir Ek listesinden EkZinciri nesnesi olu�turur.
     *
     * @param eklerStrRep : Boşluyklarla ayrılmış Ek listesi..
     *                    FIIL_YALIN FIIL_DIK ISIM_SAHIPLIK_SEN ISIM_HAL_I
     *                    gibi.
     *                    <p/>
     *                    Buy constructor istatistik dosyalar� okunurken kullan�l�r.
     */
    public EkZinciri(EkYonetici ekYonetici, String eklerStrRep, double oran) {
        // �nce bo�luklarla ayr�lm�� ek listesini alal�m.
        // FIIL_YALIN FIIL_DIK ISIM_SAHIPLIK_SEN ISIM_HAL_I 
        // gibi.
        String[] parsedEkler = eklerStrRep.trim().split(" ");
        for (int i = 0; i < parsedEkler.length; i++) {
            Ek ek = ekYonetici.ek(parsedEkler[i]);
            if (ek != null) {
                // Liste henüz oluşturulmamışsa oluştur.
                if (this.ekler == null) {
                    this.ekler = new ArrayList(2);
                }
                this.ekler.add(ek);
            }
            eklerStr += parsedEkler[i];
        }
        kullanimFrekansi = oran;
    }


    public int compareTo(Object o) {
        EkZinciri giris = (EkZinciri) o;
        return (giris.kullanimFrekansi - this.kullanimFrekansi > 0 ? 1 : -1);
    }

    public String getStringRep() {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < ekler.size(); i++) {
            s.append(((Ek) ekler.get(i)).ad());
            s.append(' ');
        }
        return s.toString();
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < ekler.size()-1; i++) {
            s.append(((Ek) ekler.get(i)).ad());
            s.append(' ');
        }
        return s.toString();
//
//        String res = eklerStr + " Frekans: " + kullanimFrekansi;
//        return res;
    }

    public String getEklerStr() {
        return eklerStr;
    }

    public List getEkler() {
        return ekler;
    }

    public void ekEkle(Ek ek) {
        ekler.add(ek);
    }

    public double getKullanimFrekansi() {
        return kullanimFrekansi;
    }

    public int getKullanimSayisi() {
        return kullanimSayisi;
    }
}
