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

package net.zemberek.yapi.kok;

import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;

/**
 * User: ahmet
 * Date: Aug 28, 2006
 */
public interface KokOzelDurumBilgisi {

    KokOzelDurumu ozelDurum(KokOzelDurumTipi tip);

    /**
     * kisaAd ile belirtilen ozel durumu dondurur.
     * @param kisaAd
     * @return ozelDurum ya da null.
     */
    KokOzelDurumu ozelDurum(String kisaAd);

    KokOzelDurumu ozelDurum(int indeks);

    String[] ozelDurumUygula(Kok kok);

    /**
     * Bazi ozel durumlar dogrudan kaynak kok dosyasinda yer almaz. bu ozel durumlari bu metod
     * tespit eder ve koke ekler.
     *
     * @param kok
     */
    void ozelDurumBelirle(Kok kok);

    /**
     * Duz yazi kok listesinden okunan dile ozel ozel durumlarin kok'e atanmasi islemi burada yapilir.
     *
     * @param kok
     * @param okunanIcerik
     * @param parcalar
     */
    void duzyaziOzelDurumOku(Kok kok, String okunanIcerik, String[] parcalar);

    /**
     * Ozellikle duz yazi dosyadan kok okumada kok icerigi tip ve dile gore on islemeden gecirilebilir
     * Ornegin turkiye turkcesinde eger kok icinde "mek" mastar eki bulunuyorsa bu silinir.
     * @param tip
     * @param icerik
     */
    void kokIcerikIsle(Kok kok, KelimeTipi tip, String icerik);

}
