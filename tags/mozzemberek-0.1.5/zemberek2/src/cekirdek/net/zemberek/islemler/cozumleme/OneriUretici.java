/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

/*
 * Created on 27.May.2005
 * MDA
 */
package net.zemberek.islemler.cozumleme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.zemberek.bilgi.ZemberekAyarlari;
import net.zemberek.islemler.KelimeKokFrekansKiyaslayici;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Alfabe;

public class OneriUretici {

    private KelimeCozumleyici cozumleyici, asciiToleransliCozumleyici;
    private ToleransliCozumleyici toleransliCozumleyici;
    private CozumlemeYardimcisi yardimci;
    private ZemberekAyarlari ayarlar;
    private Alfabe alfabe;


    public OneriUretici(Alfabe alfabe,
                        CozumlemeYardimcisi yardimci,
                        KelimeCozumleyici cozumleyici,
                        KelimeCozumleyici asciiToleransliCozumleyici,
                        ToleransliCozumleyici toleransliCozumleyici,
                        ZemberekAyarlari ayarlar) {
        this.alfabe = alfabe;
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
            asciiTurkceOneriler = asciiToleransliCozumleyici.cozumle(kelime, CozumlemeSeviyesi.TUM_KOKLER);

        Set<String> ayriYazimOnerileri = Collections.emptySet();

        // Kelime yanlislikla bitisik yazilmis iki kelimeden mi olusmus?
        if (ayarlar.oneriBilesikKelimeKullan()) {
            for (int i = 1; i < kelime.length(); i++) {
                String s1 = kelime.substring(0, i);
                String s2 = kelime.substring(i, kelime.length());
                if (cozumleyici.cozumlenebilir(s1) && cozumleyici.cozumlenebilir(s2)) {

                    Set<String> set1 = new HashSet<String>();
                    Kelime[] kelimeler1 = cozumleyici.cozumle(s1, CozumlemeSeviyesi.TUM_KOKLER);
                    for (Kelime kelime1 : kelimeler1) {
                        yardimci.kelimeBicimlendir(kelime1);
                        set1.add(kelime1.icerik().toString());
                    }

                    Set<String> set2 = new HashSet<String>();
                    Kelime[] kelimeler2 = cozumleyici.cozumle(s2, CozumlemeSeviyesi.TUM_KOKLER);
                    for (Kelime kelime1 : kelimeler2) {
                        yardimci.kelimeBicimlendir(kelime1);
                        set2.add(kelime1.icerik().toString());
                    }

                    if (ayriYazimOnerileri.size() == 0) {
                        ayriYazimOnerileri = new HashSet<String>();
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
        for (Kelime oneri : oneriList) {
            final HarfDizisi ic = oneri.icerik();
            // girisin bas harfi buyuk ise ona gore onerilerin de bas harfini buyut.
            if (ic.length() > 0 && Character.isUpperCase(kelime.charAt(0))) {
                ic.harfDegistir(0, alfabe.buyukHarf(ic.harf(0)));
            }

            // eger hepsi buyuk harf ise girisin cikisin da hepsi buyuk harf olsun.
            boolean hepsiBuyukHarf = true;
            for (int j = 0; j < kelime.length(); j++) {
                if (!Character.isUpperCase(kelime.charAt(j))) {
                    hepsiBuyukHarf = false;
                    break;
                }
            }

            if (hepsiBuyukHarf) {
                for (int j = 0; j < ic.length(); j++)
                    ic.harfDegistir(j, alfabe.buyukHarf(ic.harf(j)));
            }

            sonucListesi.add(oneri.icerik().toString());
        }

        //Çift sonuçları liste sirasını bozmadan iptal et.
        List<String> tekilListe = new ArrayList<String>(new LinkedHashSet<String>(sonucListesi));

        // Son olarak yer kalmışsa ayrı yazılım önerilerini ekle
        for (String oneri : ayriYazimOnerileri) {
            if (tekilListe.size() < ayarlar.getOneriMax()) {
                tekilListe.add(oneri);
            } else {
                break;
            }
        }

        return tekilListe.toArray(new String[tekilListe.size()]);

    }
}
