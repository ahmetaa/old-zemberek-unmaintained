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

package net.zemberek.yapi.ek;

import net.zemberek.araclar.Kayitci;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Bu sinif dile ozel ek yonetici siniflar icin taban olarak kullanilir.
 * icerisinde cesitli ek bilgileri yer alir.
 * User: ahmet
 * Date: Sep 21, 2006
 */
public class TemelEkYonetici implements EkYonetici {

    protected static Logger logger = Kayitci.kayitciUret(TemelEkYonetici.class);

    public static final Ek BOS_EK = new Ek("BOS_EK");
    protected Map<String, Ek> ekler;
    protected Map<KelimeTipi, Ek> baslangicEkleri = new HashMap<KelimeTipi, Ek>();


    public TemelEkYonetici(Map<KelimeTipi, String> baslangicEkMap,
                           XmlEkOkuyucu okuyucu) throws IOException {

         long start = System.currentTimeMillis();
        okuyucu.xmlOku();
        ekler = okuyucu.getEkler();
        for (KelimeTipi tip : baslangicEkMap.keySet()) {
            Ek ek = ekler.get(baslangicEkMap.get(tip));
            if (ek != null)
                baslangicEkleri.put(tip, ek);
            else
                logger.warning(tip + " tipi icin baslangic eki " + baslangicEkMap.get(tip) + " bulunamiyor!");
        }
        logger.fine("ek okuma ve olusum suresii: " + (System.currentTimeMillis() - start) + "ms.");
    }

    /**
     * adi verilen Ek nesnesini bulur. Eger ek yok ise null doner.
     *
     * @param ekId - ek adi
     * @return istenen Ek nesnesi.
     */
    public Ek ek(String ekId) {
        Ek ek = ekler.get(ekId);
        if (ek == null)
            logger.severe("Ek bulunamiyor!" + ekId);
        return ekler.get(ekId);
    }

    /**
     * Kok nesnesinin tipine gore gelebilecek ilk ek'i dondurur.
     * Baslangic ekleri bilgisi dil tarafindan belirlenir.
     *
     * @param kok
     * @return ilk Ek, eger kok tipi baslangic ekleri <baslangicEkleri>
     *         haritasinda belirtilmemisse BOS_EK doner.
     */
    public Ek ilkEkBelirle(Kok kok) {
        Ek baslangicEki = baslangicEkleri.get(kok.tip());
        if (baslangicEki != null)
            return baslangicEki;
        else
            return BOS_EK;
    }
}
