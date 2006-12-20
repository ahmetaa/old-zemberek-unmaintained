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

/*
 * Created on 19.Haz.2005
 *
 */
package net.zemberek.istatistik;

import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.TemelEkYonetici;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class EkIstatistikleri implements Istatistik {

    private HashMap ekIstatistikBilgileri = new HashMap();

    public void istatistikGuncelle(Kelime kelime) {
        List ekler = kelime.ekler();
        // kelime sonu için bir null ek ekliyoruz.
        ekler.add(TemelEkYonetici.BOS_EK);
        for (int i = 0; i < ekler.size() - 1; i++) {
            Ek ek = (Ek) ekler.get(i);
            EkIstatistikBilgisi ekBilgisi = (EkIstatistikBilgisi) ekIstatistikBilgileri.get(ek.ad());
            if (ekBilgisi == null) {
                ekBilgisi = new EkIstatistikBilgisi(ek);
                ekIstatistikBilgileri.put(ek.ad(), ekBilgisi);
            }
            Ek ardisilEk = (Ek) ekler.get(i + 1);
            ekBilgisi.ardisilEkEkle(ardisilEk);
        }
    }

    public void tamamla() {
        for (Iterator i = ekIstatistikBilgileri.values().iterator(); i.hasNext();) {
            EkIstatistikBilgisi ekBilgisi = (EkIstatistikBilgisi) i.next();
            ekBilgisi.duzenle();
        }
    }

    public String toString() {
        StringBuffer b = new StringBuffer();
        for (Iterator i = ekIstatistikBilgileri.values().iterator(); i.hasNext();) {
            EkIstatistikBilgisi ekBilgisi = (EkIstatistikBilgisi) i.next();
            b.append(ekBilgisi.toString());
        }
        return b.toString();
    }

    public void guncelle() {
    }


}
