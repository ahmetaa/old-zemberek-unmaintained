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

package net.zemberek.az.yapi.kok;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.ek.EkYonetici;
import net.zemberek.yapi.kok.KokOzelDurumu;
import net.zemberek.yapi.kok.TemelKokOzelDurumBilgisi;
import net.zemberek.yapi.kok.Yumusama;
import net.zemberek.yapi.kok.KokOzelDurumBilgisi;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ahmet
 * Date: Jun 18, 2006
 */
public class AzericeKokOzelDurumBilgisi
        extends TemelKokOzelDurumBilgisi implements KokOzelDurumBilgisi {

    public AzericeKokOzelDurumBilgisi(EkYonetici ekler, Alfabe alfabe) {
        super(ekler, alfabe);
        uret();
    }

    private void uret() {
        //TODO: buraya ilgili ozel durumlar eklenmeli
        ekle(uretici(AzericeKokOzelDurumTipleri.SESSIZ_YUMUSAMASI, new Yumusama()).
                sesliEkIleOlusur(true).
                yapiBozucu(true));
    }


    public String[] ozelDurumUygula(Kok kok) {
        //kok icinde ozel durum yok ise cik..
        if (!kok.ozelDurumVarmi())
            return new String[0];

        HarfDizisi hdizi = new HarfDizisi(kok.icerik(), alfabe);

        List degismisIcerikler = new ArrayList(1);

        boolean yapiBozucuOzelDurumvar = false;
        // kok uzerindeki ozel durumlar basta sona taranip ozel durum koke uygulaniyor.

        for (KokOzelDurumu ozelDurum : kok.ozelDurumDizisi()) {
                ozelDurum.uygula(hdizi);
            if (ozelDurum.yapiBozucumu())
                yapiBozucuOzelDurumvar = true;
        }

        if (yapiBozucuOzelDurumvar)
            degismisIcerikler.add(hdizi.toString());

        return (String[]) degismisIcerikler.toArray(new String[degismisIcerikler.size()]);
    }

    public void ozelDurumBelirle(Kok kok) {
    }

    public void duzyaziOzelDurumOku(Kok kok, String okunanIcerik, String[] parcalar) {
        for (int i = 2; i < parcalar.length; i++) {
            String ozelDurumAdi = parcalar[i];
            //diger ozel durumlarin elde edilmesi..
            KokOzelDurumu oz = ozelDurum(ozelDurumAdi);
            if (oz != null) {
                kok.ozelDurumEkle(oz);
            } else {
                logger.warning("Hatali kok bileseni" + kok.icerik() + " Token: " + ozelDurumAdi);
            }
        }
    }

    public void kokIcerikIsle(Kok kok, KelimeTipi tip, String icerik){}
}
