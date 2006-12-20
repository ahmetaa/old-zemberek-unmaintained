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

import net.zemberek.araclar.Kayitci;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.EkYonetici;

import java.util.*;
import java.util.logging.Logger;

/**
 * User: ahmet
 * Date: Aug 29, 2006
 */
public class TemelKokOzelDurumBilgisi {

    protected static Logger logger = Kayitci.kayitciUret(TemelKokOzelDurumBilgisi.class);

    protected EkYonetici ekYonetici;
    protected Alfabe alfabe;
    protected Map<KokOzelDurumTipi, KokOzelDurumu> ozelDurumlar = new HashMap();
    protected Map<String, KokOzelDurumu> kisaAdOzelDurumlar = new HashMap();

    public static final int MAX_OZEL_DURUM_SAYISI = 30;
    protected KokOzelDurumu[] ozelDurumDizisi = new KokOzelDurumu[MAX_OZEL_DURUM_SAYISI];

    public TemelKokOzelDurumBilgisi(EkYonetici ekYonetici, Alfabe alfabe) {
        this.ekYonetici = ekYonetici;
        this.alfabe = alfabe;
    }

    public KokOzelDurumu ozelDurum(int indeks) {
        if (indeks < 0 || indeks >= ozelDurumDizisi.length)
            throw new IndexOutOfBoundsException("istenilen indekksli ozel durum mevcut degil:" + indeks);
        return ozelDurumDizisi[indeks];
    }

    public KokOzelDurumu kisaAdIleOzelDurum(String ozelDurumKisaAdi) {
        return kisaAdOzelDurumlar.get(ozelDurumKisaAdi);
    }


    protected KokOzelDurumu.Uretici uretici(KokOzelDurumTipi tip, HarfDizisiIslemi islem) {

        // bir adet kok ozel durumu uretici olustur.
        KokOzelDurumu.Uretici uretici = new KokOzelDurumu.Uretici(tip, islem);

        // eger varsa kok adlarini kullanarak iliskili ekleri bul ve bir Set'e ata.
        String[] ekAdlari = tip.ekAdlari();
        if (ekAdlari.length > 0) {
            Set set = new HashSet(ekAdlari.length);
            for (String s : ekAdlari) {
                Ek ek = ekYonetici.ek(s);
                if (ek != null) {
                    set.add(ek);
                } else {
                    logger.warning(s + " eki bulunamadigindan kok ozel durumuna eklenemedi!");
                }
            }
            // ureticiye seti ata.
            uretici.gelebilecekEkler(set);
        }
        return uretici;
    }

    protected void ekle(KokOzelDurumu.Uretici uretici) {
        //tum
        KokOzelDurumu ozelDurum = uretici.uret();
        ozelDurumlar.put(ozelDurum.tip(), ozelDurum);
        ozelDurumDizisi[ozelDurum.indeks()] = ozelDurum;
        kisaAdOzelDurumlar.put(ozelDurum.kisaAd(), ozelDurum);
    }

    protected void bosOzelDurumEkle(KokOzelDurumTipi... tipler) {
        for (KokOzelDurumTipi tip : tipler) {
            ekle(uretici(tip,new BosHarfDizisiIslemi()));
        }
    }

    public KokOzelDurumu ozelDurum(String kisaAd) {
        return kisaAdOzelDurumlar.get(kisaAd);
    }

    public KokOzelDurumu ozelDurum(KokOzelDurumTipi tip) {
        return ozelDurumlar.get(tip);
    }
}
