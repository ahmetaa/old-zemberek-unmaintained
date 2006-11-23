/*
 * Created on 27.May.2005
 * MDA
 */
package net.zemberek.islemler.cozumleme;

import net.zemberek.islemler.KelimeKokFrekansKiyaslayici;
import net.zemberek.yapi.Kelime;

import java.util.*;

public class OneriUretici {

    private KelimeCozumleyici cozumleyici, asciiToleransliCozumleyici;
    private ToleransliCozumleyici toleransliCozumleyici;
    private CozumlemeYardimcisi yardimci;
    public static final int ONER_MAX = 12;


    public OneriUretici(CozumlemeYardimcisi yardimci,
                        KelimeCozumleyici cozumleyici,
                        KelimeCozumleyici asciiToleransliCozumleyici,
                        ToleransliCozumleyici toleransliCozumleyici
    ) {
        this.yardimci = yardimci;
        this.toleransliCozumleyici = toleransliCozumleyici;
        this.cozumleyici = cozumleyici;
        this.asciiToleransliCozumleyici = asciiToleransliCozumleyici;
    }

    /**
     * Verilen kelime için öneri üretir.
     * Yapýlan öneriler þu þekildedir:
     * - Kökte 1, ekte 1 mesafeye kadar olmak üzere Levenshtein düzeltme mesafesine uyan tüm öneriler
     * - Deasciifier'den dönüþ deðeri olarak gelen öneriler
     * - Kelimenin ayrýk iki kelimeden oluþmasý durumu için öneriler
     *
     * @param kelime : Öneri yaýlmasý istenen giriþ kelimesi
     * @return String[] olarak öneriler
     *         Eðer öneri yoksa sifir uzunluklu dizi.
     */
    public String[] oner(String kelime) {
        // Once hatalý kelime için tek kelimelik önerileri bulmaya çalýþ
        Kelime[] oneriler = toleransliCozumleyici.cozumle(kelime);
        //Deasciifierden bir þey var mý?
        Kelime[] asciiTurkceOneriler;

        asciiTurkceOneriler = asciiToleransliCozumleyici.cozumle(kelime);

        Set<String> ayriYazimOnerileri = Collections.EMPTY_SET;

        // Kelime yanlislikla bitisik yazilmis iki kelimeden mi olusmus?

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

        // erken donus..
        if (oneriler.length == 0 && ayriYazimOnerileri.size() == 0 && asciiTurkceOneriler.length == 0) {
            return new String[0];
        }

        // Onerileri puanlandýrmak için bir listeye koy
        ArrayList<Kelime> oneriList = new ArrayList<Kelime>();
        oneriList.addAll(Arrays.asList(oneriler));
        oneriList.addAll(Arrays.asList(asciiTurkceOneriler));

        Collections.sort(oneriList, new KelimeKokFrekansKiyaslayici());

        // Dönüþ listesi string olacak, Yeni bir liste oluþtur. 
        ArrayList<String> sonucListesi = new ArrayList();
        for (Kelime anOneriList : oneriList) {
            sonucListesi.add(anOneriList.icerik().toString());
        }

        //çift sonuçlari liste sirasini bozmadan iptal et.
        ArrayList<String> rafineListe = new ArrayList();
        for (String aday : sonucListesi) {
            boolean aynisiVar = false;
            for (String aRafineListe : rafineListe) {
                if (aday.equals(aRafineListe)) {
                    aynisiVar = true;
                    break;
                }
            }
            if (!aynisiVar && rafineListe.size() < ONER_MAX) {
                rafineListe.add(aday);
            }
        }

        for (String oneri : ayriYazimOnerileri) {
            if (rafineListe.size() < ONER_MAX)
                rafineListe.add(oneri);
            else
                break;
        }

        return rafineListe.toArray(new String[rafineListe.size()]);
    }
}
