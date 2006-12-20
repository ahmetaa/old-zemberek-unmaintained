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
 *  Ahmet A. Akin, Mehmet D. Akin, Guychmyrat Amanmyradov.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.tm.yapi.ek;

import net.zemberek.tm.yapi.TurkmenceSesliUretici;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.TurkceHarf;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.EkUretici;
import net.zemberek.yapi.ek.EkUretimBileseni;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class EkUreticiTm implements EkUretici {

    private static Logger log = Logger.getLogger(EkUreticiTm.class.getName());
    private final TurkceHarf HARF_a;
    private final TurkceHarf HARF_aa;
    private TurkmenceSesliUretici sesliUretici;

    public EkUreticiTm(Alfabe alfabe) {
        HARF_a = alfabe.harf('a');
        HARF_aa = alfabe.harf(Alfabe.CHAR_aa);
        this.sesliUretici = new TurkmenceSesliUretici(alfabe);
    }

    public HarfDizisi cozumlemeIcinEkUret(HarfDizisi ulanacak, HarfDizisi giris, List<EkUretimBileseni> bilesenler) {
        HarfDizisi sonuc = new HarfDizisi();
        for (int i = 0; i < bilesenler.size(); i++) {
            EkUretimBileseni ekUretimBileseni = bilesenler.get(i);
            TurkceHarf harf = ekUretimBileseni.harf();
            final TurkceHarf sonSesli = ulanacak.sonSesli();
            switch (ekUretimBileseni.kural()) {
                case HARF:
                    sonuc.ekle(harf);
                    break;
                case SESSIZ_Y:
                    if (!ulanacak.sonSesli().inceSesliMi())
                        sonuc.ekle(HARF_a);
                    else
                        sonuc.ekle(HARF_aa);
                    break;
                case KAYNASTIR:
                    if (ulanacak.sonHarf().sesliMi())
                        sonuc.ekle(harf);
                    break;
                case SERTLESTIR:
                    if (ulanacak.sonHarf().sertMi())
                        sonuc.ekle(harf.sertDonusum());
                    else
                        sonuc.ekle(harf);
                    break;
                case SESLI_AA:
                    if (i == 0 && ulanacak.sonHarf().sesliMi())
                        break;
                    sonuc.ekle(sesliUretici.sesliBelirleAA(sonSesli));
                    break;
                case SESLI_AE:
                    if (i == 0 && ulanacak.sonHarf().sesliMi())
                        break;
                    sonuc.ekle(sesliUretici.sesliBelirleAE(sonSesli));
                    break;
                case SESLI_IU:
                    // eger eklenilecek olan kelime selsi ile bitiyorsa ekleme.
                    if (i == 0 && ulanacak.sonHarf().sesliMi())
                        break;
                    // eger eklenecek kelime birden fazla heceli ise i ekle.
                    if (ulanacak.sesliSayisi() > 1) {
                        sonuc.ekle(sesliUretici.sesliBelirleII(sonSesli));
                        break;
                    }
                    // eger bu son bilesen ise ve hece sayisi henuz ikiden fazla degilse
                    if (i == bilesenler.size() - 1) {
                        //eger giris yok ise (olusum icin ek uretiliyor) i olarak ekle.
                        if (giris == null)
                            sonuc.ekle(sesliUretici.sesliBelirleII(sonSesli));
                        else if (giris.sesliSayisi() >= 2 &&
                                !giris.sonHarf().sesliMi())
                            sonuc.ekle(sesliUretici.sesliBelirleIU(sonSesli));
                        else
                            sonuc.ekle(sesliUretici.sesliBelirleII(sonSesli));
                        break;
                    }
                    sonuc.ekle(sesliUretici.sesliBelirleIU(sonSesli));
                    break;
            }
        }
        return sonuc;
    }

    public HarfDizisi olusumIcinEkUret(HarfDizisi ulanacak, Ek sonrakiEk, List<EkUretimBileseni> bilesenler) {
        //TODO: gecici olarak bu sekilde
        return cozumlemeIcinEkUret(ulanacak, null, bilesenler);
    }

    public Set<TurkceHarf> olasiBaslangicHarfleri(List<EkUretimBileseni> bilesenler) {
        return null;
    }


}