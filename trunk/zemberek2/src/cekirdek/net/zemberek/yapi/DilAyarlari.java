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

package net.zemberek.yapi;

import net.zemberek.yapi.ek.EkOzelDurumUretici;
import net.zemberek.yapi.ek.EkUretici;

import java.util.Map;
import java.util.Locale;

/**
 * Bir dilin gerceklenmesi sirasinda kullanilaca sinif ve cesitli bilgilere erisimi saglar.
 * User: ahmet
 * Date: Sep 20, 2006
 */
public interface DilAyarlari {

    Locale locale();

    Class alfabeSinifi();

    Class ekYoneticiSinifi();

    Class heceBulucuSinifi();

    Class kokOzelDurumBilgisiSinifi();

    Class cozumlemeYardimcisiSinifi();

    String[] duzYaziKokDosyalari();

    /**
     * Duz yazi ile belirtilen kok dosyalarinda kokun tipinin hangi kelime ile ifade
     * edilecegi bir Map icerisinde belirtilir.
     * @return kisaAd-tip ikililerini tasiyan Map
     */
    Map<String, KelimeTipi> kokTipiAdlari();

    EkUretici ekUretici(Alfabe alfabe);

    EkOzelDurumUretici ekOzelDurumUretici(Alfabe alfabe);

    Map<KelimeTipi, String> baslangiEkAdlari();

    String ad();
}
