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
 *  The Original Code is Zemberek Do?al Dil ??leme Kütüphanesi.
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Ak?n, Mehmet D. Ak?n.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

/*
 * Created on 09.Eki.2004
 */
package net.zemberek.istatistik;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author MDA
 */
public class IkiliIstatistikleri implements Istatistik{
    public static final int MAX_KELIME_ZINCIRI = 500000;
    private String oncekiKelime = null;
    private HashMap<String, KelimeZinciri> kelimeZincirleri = new HashMap<String, KelimeZinciri>(100);
    private ArrayList<KelimeZinciri> siraliKelimeZincirleri = new ArrayList<KelimeZinciri>(100);

    private static long memSaver = 0;

    public void sonucGuncelle(String giris) {
        memSaver++;
        // Kelime zincirleri
        if (oncekiKelime == null) {
            oncekiKelime = giris;
        } else {
            String key = oncekiKelime + "-" + giris;
            KelimeZinciri zincir = kelimeZincirleri.get(key);
            oncekiKelime = giris;
            if (zincir == null) {
                if (memSaver < MAX_KELIME_ZINCIRI) {
                    zincir = new KelimeZinciri(key);
                    kelimeZincirleri.put(key, zincir);
                }
            } else {
                zincir.hit();
            }
        }
    }

    public void guncelle() {
    }

    @SuppressWarnings("unchecked")
	public void tamamla() {
        siraliKelimeZincirleri.addAll(kelimeZincirleri.values());
        Collections.sort(siraliKelimeZincirleri);
    }

    public ArrayList getSiraliKelimeZincirleri() {
        return siraliKelimeZincirleri;
    }


}
