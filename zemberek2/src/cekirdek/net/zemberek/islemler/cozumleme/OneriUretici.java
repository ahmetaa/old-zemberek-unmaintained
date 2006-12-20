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

/*
 * Created on 27.May.2005
 * MDA
 */
package net.zemberek.islemler.cozumleme;

import net.zemberek.bilgi.ZemberekAyarlari;
import net.zemberek.islemler.KelimeKokFrekansKiyaslayici;
import net.zemberek.yapi.Kelime;

import java.util.*;

public class OneriUretici {

    private KelimeCozumleyici cozumleyici, asciiToleransliCozumleyici;
    private ToleransliCozumleyici toleransliCozumleyici;
    private CozumlemeYardimcisi yardimci;
    private ZemberekAyarlari ayarlar;


    public OneriUretici(CozumlemeYardimcisi yardimci,
                        KelimeCozumleyici cozumleyici,
                        KelimeCozumleyici asciiToleransliCozumleyici,
                        ToleransliCozumleyici toleransliCozumleyici,
                        ZemberekAyarlari ayarlar) {
        this.yardimci = yardimci;
        this.toleransliCozumleyici = toleransliCozumleyici;
        this.cozumleyici = cozumleyici;
        this.asciiToleransliCozumleyici = asciiToleransliCozumleyici;
        this.ayarlar = ayarlar;
    }

    /**
     * Verilen kelime için öneri üretir.
     * Yapılan öneriler şu şekildedir:
     * - Kökte 1, ekte 1 mesafeye kadar olmak üzere Levenshtein düzeltme mesafesine uyan tüm öneriler
     * - Deasciifier'den dönüş değeri olarak gelen öneriler
     * - Kelimenin ayrık iki kelimeden oluşması durumu için öneriler
     *
     * @param kelime : Öneri yapılması istenen giriş kelimesi
     * @return String[] olarak öneriler
     *         Eğer öneri yoksa sifir uzunluklu dizi.
     */
    public String[] oner(String kelime) {
        // Once hatalı kelime için tek kelimelik önerileri bulmaya çalış
        Kelime[] oneriler = toleransliCozumleyici.cozumle(kelime);
        
        //Deasciifierden bir şey var mı?
        Kelime[] asciiTurkceOneriler = new Kelime[0];
        if (ayarlar.oneriDeasciifierKullan())
            asciiTurkceOneriler = asciiToleransliCozumleyici.cozumle(kelime);

        Set<String> ayriYazimOnerileri = Collections.EMPTY_SET;

        // Kelime yanlislikla bitisik yazilmis iki kelimeden mi olusmus?
        if (ayarlar.oneriBilesikKelimeKullan()) {
            for (int i = 1; i < kelime.length(); i++) {
                String s1 = kelime.substring(0, i);
                String s2 = kelime.substring(i, kelime.length());
                if (cozumleyici.denetle(s1) && cozumleyici.denetle(s2)) {

                    Set<String> set1 = new HashSet();
                    Kelime[] kelimeler1 = cozumleyici.cozumle(s1);
                    for (Kelime kelime1 : kelimeler1) {
                        yardimci.kelimeBicimlendir(kelime1);
                        set1.add(kelime1.icerik().toString());
                    }

                    Set<String> set2 = new HashSet();
                    Kelime[] kelimeler2 = cozumleyici.cozumle(s2);
                    for (Kelime kelime1 : kelimeler2) {
                        yardimci.kelimeBicimlendir(kelime1);
                        set2.add(kelime1.icerik().toString());
                    }

                    if (ayriYazimOnerileri.size() == 0) {
                        ayriYazimOnerileri = new HashSet();
                    }

                    for (String str1 : set1) {
                        for (String str2 : set2) {
                            ayriYazimOnerileri.add(str1 + " " + str2);
                        }
                    }
                }
            }
        }

        // erken donus..
        if (oneriler.length == 0 && ayriYazimOnerileri.size() == 0 && asciiTurkceOneriler.length == 0) {
            return new String[0];
        }

        // Onerileri puanlandırmak için bir listeye koy
        ArrayList<Kelime> oneriList = new ArrayList<Kelime>();
        oneriList.addAll(Arrays.asList(oneriler));
        oneriList.addAll(Arrays.asList(asciiTurkceOneriler));

        // Frekansa göre sırala
        Collections.sort(oneriList, new KelimeKokFrekansKiyaslayici());

        // Dönüş listesi string olacak, Yeni bir liste oluştur. 
        ArrayList<String> sonucListesi = new ArrayList<String>();
        for (Kelime anOneriList : oneriList) {
            sonucListesi.add(anOneriList.icerik().toString());
        }

        //Çift sonuçları liste sirasını bozmadan iptal et.
        ArrayList<String> rafineListe = new ArrayList<String>();
        for (String aday : sonucListesi) {
            boolean aynisiVar = false;
            for (String aRafineListe : rafineListe) {
                if (aday.equals(aRafineListe)) {
                    aynisiVar = true;
                    break;
                }
            }
            if (!aynisiVar && rafineListe.size() < ayarlar.getOneriMax()) {
                rafineListe.add(aday);
            }
        }
        	
        // Son olarak yer kalmışsa ayrı yazılım önerilerini ekle
        for (String oneri : ayriYazimOnerileri) {
            if (rafineListe.size() < ayarlar.getOneriMax())
                rafineListe.add(oneri);
            else
                break;
        }

        return rafineListe.toArray(new String[rafineListe.size()]);
    }
}
