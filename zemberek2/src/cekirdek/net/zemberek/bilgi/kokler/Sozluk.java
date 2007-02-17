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
 * Created on 07.Mar.2004
 */
package net.zemberek.bilgi.kokler;

import net.zemberek.yapi.Kok;
import net.zemberek.yapi.KelimeTipi;

import java.util.Collection;
import java.util.List;

/**
 * Birden fazla sozlukle calisabilmek icin Sozluk arayuzu. 
 *
 * @author MDA
 */
public interface Sozluk {
    /**
     * str seklinde yazilan tum kelime koklerini dondurur. str kokun istisna hali de olabilir.
     *
     * @param str
     * @return kok listesi.
     */
    public List<Kok> kokBul(String str);


    public Kok kokBul(String str, KelimeTipi tip);

    /**
     * sozluk icindeki normal ya da kok ozel durumu seklindeki tum kok iceriklerini bir
     * Koleksiyon nesnesi olarak dondurur.
     *
     * @return tum kokleri iceren Collection nesnesi
     */
    public Collection<Kok> tumKokler();

    /**
     * sozluge kok ekler.
     *
     * @param kok
     */
    public void ekle(Kok kok);

    /**
     * Bu metod kökbulucu fabrikası elde etmek için kullanılır. Gerçekleyen sözlük sınıfları bu
     * metodda kendi Kök bulucu fabrikası gerçeklemelerinin bir instancesini geri döndürmelidirler.
     *
     * @return Sözlük
     * @see AgacSozluk
     */
    public KokAdayiBulucuUretici getKokBulucuFactory();

}

