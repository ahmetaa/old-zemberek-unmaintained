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

package net.zemberek.az.yapi;

import static net.zemberek.az.yapi.ek.AzericeEkAdlari.AD_KOK;
import static net.zemberek.yapi.KelimeTipi.BAGLAC;
import static net.zemberek.yapi.KelimeTipi.EDAT;
import static net.zemberek.yapi.KelimeTipi.FIIL;
import static net.zemberek.yapi.KelimeTipi.HATALI;
import static net.zemberek.yapi.KelimeTipi.IMEK;
import static net.zemberek.yapi.KelimeTipi.ISIM;
import static net.zemberek.yapi.KelimeTipi.KISALTMA;
import static net.zemberek.yapi.KelimeTipi.OZEL;
import static net.zemberek.yapi.KelimeTipi.SAYI;
import static net.zemberek.yapi.KelimeTipi.SIFAT;
import static net.zemberek.yapi.KelimeTipi.SORU;
import static net.zemberek.yapi.KelimeTipi.UNLEM;
import static net.zemberek.yapi.KelimeTipi.YANKI;
import static net.zemberek.yapi.KelimeTipi.ZAMAN;
import static net.zemberek.yapi.KelimeTipi.ZAMIR;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import net.zemberek.az.islemler.AzericeCozumlemeYardimcisi;
import net.zemberek.az.yapi.ek.AzericeEkOzelDurumUretici;
import net.zemberek.az.yapi.ek.EkUreticiAz;
import net.zemberek.az.yapi.kok.AzericeKokOzelDurumBilgisi;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.DilAyarlari;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.ek.EkKuralBilgisi;
import net.zemberek.yapi.ek.EkOzelDurumUretici;
import net.zemberek.yapi.ek.EkUretici;
import net.zemberek.yapi.ek.TemelEkUretimKurali;
import net.zemberek.yapi.ek.TemelEkYonetici;

/**
 * User: ahmet
 * Date: Sep 6, 2005
 */
public class Azerice implements DilAyarlari {

    public Locale locale() {
        return new Locale("az");
    }

    public Class<Alfabe> alfabeSinifi() {
        return Alfabe.class;
    }

    public Class<TemelEkYonetici> ekYoneticiSinifi() {
        return TemelEkYonetici.class;
    }

    public Class<AzericeHeceleyici> heceleyiciSinifi() {
        return AzericeHeceleyici.class;
    }

    public Class<AzericeKokOzelDurumBilgisi> kokOzelDurumBilgisiSinifi() {
        return AzericeKokOzelDurumBilgisi.class;
    }

    public Class<AzericeCozumlemeYardimcisi> cozumlemeYardimcisiSinifi() {
        return AzericeCozumlemeYardimcisi.class;
    }

    public EkUretici ekUretici(Alfabe alfabe) {
        return new EkUreticiAz(alfabe);
    }

    public EkOzelDurumUretici ekOzelDurumUretici(Alfabe alfabe) {
        return new AzericeEkOzelDurumUretici(alfabe);
    }

    public String[] duzYaziKokDosyalari() {
        return new String[]{"kaynaklar/az/bilgi/kokler.txt"};
    }

    public EkKuralBilgisi ekKuralBilgisi() {
        return new TemelEkUretimKurali.TemelKuralBilgisi();
    }

    public Map<String, KelimeTipi> kokTipiAdlari() {
        //TODO: burasi azericeye gore duzenlenmeli
        Map<String, KelimeTipi> tipMap = new HashMap<String, KelimeTipi>();
        tipMap.put("AD", ISIM);
        tipMap.put("EY", FIIL);
        tipMap.put("SI", SIFAT);
        tipMap.put("RA", SAYI);
        tipMap.put("YA", YANKI);
        tipMap.put("ZA", ZAMIR);
        tipMap.put("SO", SORU);
        tipMap.put("IM", IMEK);
        tipMap.put("ZAMAN", ZAMAN);
        tipMap.put("HATALI", HATALI);
        tipMap.put("EDAT", EDAT);
        tipMap.put("BAGLAC", BAGLAC);
        tipMap.put("OZ", OZEL);
        tipMap.put("UN", UNLEM);
        tipMap.put("KI", KISALTMA);
        return tipMap;
    }

    public Map<KelimeTipi, String> baslangiEkAdlari() {
        Map<KelimeTipi, String> baslangicEkAdlari = new EnumMap<KelimeTipi, String>(KelimeTipi.class);
        //TODO: Baslangic ekleri simidlik sadece ISIM_KOK baslangic ekini gosteriyor..
        baslangicEkAdlari.put(ISIM, AD_KOK);
        baslangicEkAdlari.put(SIFAT, AD_KOK);
        baslangicEkAdlari.put(FIIL, AD_KOK);
        baslangicEkAdlari.put(ZAMAN, AD_KOK);
        baslangicEkAdlari.put(ZAMIR, AD_KOK);
        baslangicEkAdlari.put(SAYI, AD_KOK);
        baslangicEkAdlari.put(SORU, AD_KOK);
        baslangicEkAdlari.put(UNLEM, AD_KOK);
        baslangicEkAdlari.put(EDAT, AD_KOK);
        baslangicEkAdlari.put(BAGLAC, AD_KOK);
        baslangicEkAdlari.put(OZEL, AD_KOK);
        baslangicEkAdlari.put(IMEK, AD_KOK);
        baslangicEkAdlari.put(YANKI, AD_KOK);
        baslangicEkAdlari.put(KISALTMA, AD_KOK);
        return baslangicEkAdlari;
    }

    public String ad() {
        return "AZERICE";
    }
}