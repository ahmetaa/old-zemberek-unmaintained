package net.zemberek.yapi.obek;

import net.zemberek.yapi.*;
import static net.zemberek.yapi.KelimeTipi.*;
import net.zemberek.bilgi.kokler.KokAdayiBulucu;
import net.zemberek.bilgi.KaynakYukleyici;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.islemler.cozumleme.KelimeCozumleyici;
import net.zemberek.islemler.cozumleme.StandartCozumleyici;
import net.zemberek.islemler.cozumleme.KesinHDKiyaslayici;
import net.zemberek.islemler.KokBulucu;
import net.zemberek.islemler.KelimeTabanliKokBulucu;

import java.util.*;
import java.io.IOException;
import java.io.BufferedReader;

public class DuzyaziKelimeObegiOkuyucu {

    KokBulucu kokBulucu;


    public DuzyaziKelimeObegiOkuyucu(KokBulucu kokBulucu) {
        this.kokBulucu = kokBulucu;
    }

    public List<KelimeObegi> tumunuOku(String kaynak) throws IOException {
        BufferedReader reader = new KaynakYukleyici("utf-8").getReader(kaynak);
        List<KelimeObegi> koList = new ArrayList<KelimeObegi>();

        while (reader.ready()) {

            String s = reader.readLine().trim();
            if (s.startsWith("#"))
                continue;
            String kelimeDizi[] = s.replaceAll(" \\t+", "").split("[\\| ]");

            // en az iki kok bulunmasi gerekir.
            if (s.length() < 2)
                continue;

            List<KelimeObekBileseni> bilesenList = new ArrayList<KelimeObekBileseni>();

            kelimeDizi = parantezKontrol(kelimeDizi);

            // kok adaylarini bul. normalde kok tip bilgisi yer alsa daha iyi.

            for (int i = 0; i < kelimeDizi.length; i++) {

                Kok[] kokAdaylari = kokBulucu.kokBul(kelimeDizi[i]);

                if (kokAdaylari.length == 0)
                    continue;

                Kok aday = kokAdaylari[0];

                if (kokAdaylari.length > 1) {
                    // kok icerigi boyuna gore buyukten kucuge sirala.
                    Arrays.sort(kokAdaylari, new Comparator<Kok>() {
                        public int compare(Kok o1, Kok o2) {
                            return o2.icerik().length() - o1.icerik().length();
                        }
                    });

                    // son kok ise fiili yoksa fiil olmayan koku tercih et.
/*                    for (Kok kok : kokAdaylari) {
                        if (i == kelimeDizi.length - 1 && kok.tip() == FIIL) {
                            aday = kok;
                            break;
                        }
                        if (i < kelimeDizi.length - 1 && kok.tip() != FIIL) {
                            aday = kok;
                            break;
                        }
                    }*/
                }


                if (kokAdaylari.length > 0) {
                    KelimeObekBileseni bilesen = new KelimeObekBileseni(aday, EkKalipTipi.YALIN);
                    bilesenList.add(bilesen);
                }
            }
            if (bilesenList.size() > 1)
                koList.add(new KelimeObegi(bilesenList.toArray(new KelimeObekBileseni[bilesenList.size()])));

        }
        return koList;
    }


    /**
     * dandik sekilde parantez icindeki kelimeleri eliyoruz.
     *
     * @param strs
     * @return
     */
    private String[] parantezKontrol(String[] strs) {
        List<String> news = new ArrayList<String>();
        boolean parantezIci = false;
        for (String str : strs) {
            if (str.length() == 0)
                continue;
            if (str.startsWith("(") && str.endsWith(")"))
                continue;
            if ("(".equals(str) || str.startsWith("("))
                parantezIci = true;
            if (")".equals(str) || str.endsWith(")"))
                parantezIci = false;
            if (!parantezIci) {
                news.add(str);
            }
        }
        return news.toArray(new String[news.size()]);
    }

    public static void main(String[] args) throws IOException {
        DilBilgisi db = new TurkceDilBilgisi(new TurkiyeTurkcesi());

        KokAdayiBulucu kokAdayiBulucu = db.kokler().kokBulucuFactory().kesinKokBulucu();
        KelimeCozumleyici cozumleyici = new StandartCozumleyici(
                kokAdayiBulucu,
                new KesinHDKiyaslayici(),
                db.alfabe(),
                db.ekler(),
                db.cozumlemeYardimcisi());
        KokBulucu kokBulucu = new KelimeTabanliKokBulucu(cozumleyici, db.alfabe());
        DuzyaziKelimeObegiOkuyucu o = new DuzyaziKelimeObegiOkuyucu(kokBulucu);
        List<KelimeObegi> obekler = o.tumunuOku("kelime-obekleri.txt");
        for (KelimeObegi obek : obekler) {
            System.out.println(obek);
        }
    }

}
