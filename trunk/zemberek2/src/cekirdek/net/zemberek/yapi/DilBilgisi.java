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

import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.islemler.cozumleme.CozumlemeYardimcisi;
import net.zemberek.yapi.ek.EkYonetici;
import net.zemberek.yapi.kok.KokOzelDurumBilgisi;

/**
 * Herhangi bir turkDili'nin asagidaki tum metodlari saglamasi gerekir.
 * User: ahmet
 * Date: Sep 6, 2005
 */
public interface DilBilgisi {

    /**
     * Dile ozel alfabe nesnesini dondurur.
     * @return alfabe.
     */
    Alfabe alfabe();

    /**
     * Dile ozgu ek oynetici nesnesini dondurur.
     * @return ekyonetici
     */
    EkYonetici ekler();

    /**
     * Kok bilgilerine ve kok secicilere erisimi saglayan
     * dile ozel Sozluk nesnesini dondurur.
     * @return sozluk
     */
    Sozluk kokler();

    /**
     * Dile ozgu kok ozel durumu bilgilerini tasiyan nesneyi dondurur.
     * @return ozeldurumbilgisi
     */
    KokOzelDurumBilgisi kokOzelDurumlari();

    /**
     * eger varsa dile ozgu hece bulma nesnesi.
     * @return hecebulma nesnesi
     */
    Heceleyici heceBulucu();

    /**
     * dile ozgu cozumleme yardimcisi nesnesi. bu nesne cozumleme sirasinda kullanilan
     * cesitli on ve art isleme, cep mekanizmalarini tasir.
     * @return cozumleme yardimcisi
     */
    CozumlemeYardimcisi cozumlemeYardimcisi();

}
