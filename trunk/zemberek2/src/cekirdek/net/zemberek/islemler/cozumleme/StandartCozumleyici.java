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

package net.zemberek.islemler.cozumleme;

import net.zemberek.bilgi.kokler.KokBulucu;
import net.zemberek.yapi.*;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.EkYonetici;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: ahmet
 * Date: Jan 9, 2005
 */
public class StandartCozumleyici implements KelimeCozumleyici {

    private static Logger log = Logger.getLogger(StandartCozumleyici.class.getName());
    private final KokBulucu kokBulucu;
    private final HarfDizisiKiyaslayici harfDizisiKiyaslayici;
    private EkYonetici ekYonetici;
    private Alfabe alfabe;
    private CozumlemeYardimcisi yardimci;

    public StandartCozumleyici(KokBulucu kokBulucu,
                               HarfDizisiKiyaslayici kiyaslayci,
                               Alfabe alfabe,
                               EkYonetici ekYonetici,
                               CozumlemeYardimcisi yardimci) {
        this.kokBulucu = kokBulucu;
        this.harfDizisiKiyaslayici = kiyaslayci;
        this.ekYonetici = ekYonetici;
        this.alfabe = alfabe;
        this.yardimci = yardimci;
    }


    public boolean denetle(String strGiris) {
        return yardimci.cepteAra(strGiris) || (cozumle(strGiris, false).length == 1);
    }

    public Kelime[] cozumle(String strGiris) {
        return cozumle(strGiris, true);
    }

    /**
     * eger hepsiniCozumle=true ise dogru olabilecek tum kok ve ek kombinasyonlarini
     * dondurur.
     * Eger flag false ise ilk dogru cozumu tek elemanli dizi seklinde
     * dondurur.bu yontem hiz gerektiren denetleme islemi icin kullanilir.
     *
     * @param strGiris
     * @param hepsiniCozumle
     * @return tek ya da coklu kelime dizisi.
     */
    public Kelime[] cozumle(String strGiris, boolean hepsiniCozumle) {

        //on islemler
        String strIslenmis = alfabe.ayikla(strGiris);
        if (!alfabe.cozumlemeyeUygunMu(strIslenmis) || strIslenmis.length() == 0)
            return BOS_KELIME_DIZISI;

        //kok adaylarinin bulunmasi.
        List<Kok> kokler = kokBulucu.getAdayKokler(strIslenmis);

        if (log.isLoggable(Level.FINE)) log.fine("Giris: " + strGiris + ", Adaylar: " + kokler);
        HarfDizisi girisDizi = new HarfDizisi(strIslenmis, alfabe);
        boolean icerikDegisti = false;

        //cozumlerin bulunmasi
        List<Kelime> cozumler = new ArrayList(2);
        for (int i = kokler.size() - 1; i >= 0; i--) {
            if (icerikDegisti) {
                girisDizi = new HarfDizisi(strIslenmis, alfabe);
                icerikDegisti = false;
            }
            Kok kok = kokler.get(i);
            if (log.isLoggable(Level.FINER)) log.finest("Aday:" + kok.icerik());
            HarfDizisi kokDizi = new HarfDizisi(kok.icerik(), alfabe);

            //eger giris koke dogrudan esitse cozmeden kelimeyi olustur.
            if (harfDizisiKiyaslayici.kiyasla(kokDizi, girisDizi)) {
                Kelime kelime = kelimeUret(kok, kokDizi);
                if (yardimci.kelimeBicimiDenetle(kelime, strGiris)) {
                    if (!hepsiniCozumle) {
                        return new Kelime[] {kelime};
                    } else
                        cozumler.add(kelime);
                }
            } else {
                icerikDegisti = yardimci.kokGirisDegismiVarsaUygula(kok, kokDizi, girisDizi);
                List<Kelime> sonuclar = coz(kok, kokDizi, girisDizi, hepsiniCozumle);
                for (Kelime sonuc : sonuclar) {
                    if (yardimci.kelimeBicimiDenetle(sonuc, strGiris)) {
                        if (!hepsiniCozumle) {
                            return new Kelime[] {sonuc};
                        }
                        cozumler.add(sonuc);
                    }
                }
            }
        }
        return cozumler.toArray(new Kelime[cozumler.size()]);
    }

    private Kelime kelimeUret(Kok kok, HarfDizisi dizi) {
        Kelime kelime = new Kelime(kok, dizi);
        kelime.ekEkle(ekYonetici.ilkEkBelirle(kelime.kok()));
        return kelime;
    }

    private List<Kelime> coz(Kok kok, HarfDizisi kokDizi, HarfDizisi giris, boolean tumunuCozumle) {

        Kelime kelime = kelimeUret(kok, kokDizi);
        BasitKelimeYigini kelimeYigini = new BasitKelimeYigini();
        Ek bulunanEk = kelime.sonEk();
        int ardisilEkSirasi = 0;
        List<Kelime> uygunSonuclar = Collections.emptyList();
        TurkceHarf ilkEkHarfi= giris.harf(kelime.boy());
        while (true) {
            //bulunan son ekten sonra gelebilecek eklerden siradakini al.
            Ek incelenenEk = bulunanEk.getArdisilEk(ardisilEkSirasi++);
            //siradaki ek yoksa incelenen ek yanlis demektir.
            // yigindan kelimenin onceki durumunu cek.
            if (incelenenEk == null) {
                //yigin bos ise basarisizlik.
                if (kelimeYigini.bosMu())
                    return uygunSonuclar;

                //kelimeyi ve bulunan eki onceki formuna donustur.
                BasitKelimeYigini.YiginKelime yiginKelime = kelimeYigini.al();
                kelime = yiginKelime.getKelime();
                bulunanEk = kelime.sonEk();
                ardisilEkSirasi = yiginKelime.getEkSirasi();
                ilkEkHarfi= giris.harf(kelime.boy());
                continue;
            }

            if (kelime.gercekEkYok() && kelime.kok().ozelDurumVarmi()) {
                if (!ozelDurumUygula(kelime, giris, incelenenEk)) {
                    continue;
                } else
                   ilkEkHarfi = giris.harf(kelime.boy());
            }

            if(!incelenenEk.ilkHarfDenetle(ilkEkHarfi))
               continue;
            
            HarfDizisi olusanEkIcerigi = incelenenEk.cozumlemeIcinUret(kelime, giris, harfDizisiKiyaslayici);
            if (olusanEkIcerigi == null || olusanEkIcerigi.length() == 0) {
                continue;
            }

            if (harfDizisiKiyaslayici.aradanKiyasla(giris,
                    olusanEkIcerigi,
                    kelime.boy())) {
                // ek dongusu testi
                //if (kelime.ekDongusuVarmi(incelenenEk)) continue;
                kelimeYigini.koy(kelime.clone(), ardisilEkSirasi);
                ardisilEkSirasi = 0;
                kelime.ekEkle(incelenenEk);
                kelime.icerikEkle(olusanEkIcerigi);
                ilkEkHarfi = giris.harf(kelime.boy());
                if (log.isLoggable(Level.FINE)) log.fine("ekleme sonrasi olusan kelime: " + kelime.icerik());

                bulunanEk = incelenenEk;

                if (harfDizisiKiyaslayici.kiyasla(kelime.icerik(), giris) && !incelenenEk.sonEkOlamazMi()) {
                    if (!tumunuCozumle) {
                        uygunSonuclar = new ArrayList(1);
                        uygunSonuclar.add(kelime);
                        return uygunSonuclar;
                    }
                    if (uygunSonuclar.isEmpty())
                        uygunSonuclar = new ArrayList(2);
                    uygunSonuclar.add(kelime.clone());
                }
            }
        }
    }

    private boolean ozelDurumUygula(Kelime kelime, HarfDizisi giris, Ek ek) {
        if (!kelime.kok().yapiBozucuOzelDurumVarmi())
            return true;
        HarfDizisi testKokIcerigi = kelime.kok().ozelDurumUygula(alfabe, ek);
        if (testKokIcerigi == null) return false;
        if (log.isLoggable(Level.FINER))
            log.finer("Ozel durum sonrasi:" + testKokIcerigi + "  ek:" + ek.ad());
        kelime.setIcerik(testKokIcerigi);
        return harfDizisiKiyaslayici.bastanKiyasla(giris, testKokIcerigi);
    }
}

