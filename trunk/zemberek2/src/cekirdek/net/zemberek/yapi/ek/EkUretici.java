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

import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.TurkceHarf;

import java.util.List;
import java.util.Set;

/**
 * ek uretim kuralinin islenmesinde kullanilan sinif icin ortak arayuz.
 * Her lehce kendi uretim sinifini kullanir.
 * User: ahmet
 * Date: Aug 22, 2005
 */
public interface EkUretici {

    /**
     * Kelime Cozumleme islemi icin ek uretimi.
     * @param ulanacak
     * @param giris
     * @param bilesenler
     * @return uretilen ek, HarfDizisi cinsinden.
     */
    HarfDizisi cozumlemeIcinEkUret(HarfDizisi ulanacak,
                                   HarfDizisi giris,
                                   List<EkUretimBileseni> bilesenler);

    /**
     * Kelime uretimi icin ek uretimi.
     * @param ulanacak
     * @param sonrakiEk
     * @param bilesenler
     * @return uretilen ek, HarfDizisi cinsinden.
     */
    HarfDizisi olusumIcinEkUret(HarfDizisi ulanacak,
                                Ek sonrakiEk,
                                List<EkUretimBileseni> bilesenler);

    /**
     * Ek bilesenlerini kullarak bir ekin hangi harflerle baslayacagini kestirip sonuclari
     * bir set icerisinde dondurur.
     * @param bilesenler
     * @return olasi baslangic harfleri bir Set icerisinde.
     */
    Set<TurkceHarf> olasiBaslangicHarfleri(List<EkUretimBileseni> bilesenler);

    /**
     * bilesenlere gore en basta sesli harf olup olamayacagini belirler.
     * @param bilesenler
     * @return
     */
    boolean sesliIleBaslayabilir(List<EkUretimBileseni> bilesenler);
}
