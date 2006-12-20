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

package net.zemberek.yapi.ek;

import net.zemberek.islemler.cozumleme.HarfDizisiKiyaslayici;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Kelime;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Ek ozel durumu ek'e benzer bir yapiya sahiptir. Farkli olarak bazi ozel durumlarda yer alan
 * onek listesi de bu sinifin bir parametresidir.
 * User: ahmet
 * Date: Aug 24, 2005
 */
public abstract class EkOzelDurumu {

    protected String ad;
    protected Set onEkler= Collections.EMPTY_SET;
    protected EkUretici ekUretici;
    protected List<EkUretimBileseni> uretimBilesenleri;

    public abstract HarfDizisi cozumlemeIcinUret(Kelime kelime, HarfDizisi giris, HarfDizisiKiyaslayici kiyaslayici);

    public HarfDizisi olusumIcinUret(Kelime kelime, Ek sonrakiEk)
    {
        return ekUretici.olusumIcinEkUret(kelime.icerik(), sonrakiEk, uretimBilesenleri);
    }

    public String ad() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public Set getOnEkler() {
        return onEkler;
    }

    public void setOnEkler(Set onEkler) {
        this.onEkler = onEkler;
    }

    public void setEkKuralCozumleyici(EkUretici ekUretici) {
        this.ekUretici = ekUretici;
    }

    public void setUretimBilesenleri(List<EkUretimBileseni> uretimBilesenleri) {
        this.uretimBilesenleri = uretimBilesenleri;
    }

    public List<EkUretimBileseni> uretimBilesenleri() {
        return uretimBilesenleri;
    }
}
