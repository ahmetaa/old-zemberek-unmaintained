/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import net.zemberek.islemler.cozumleme.CozumlemeYardimcisi;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.EkSiralayici;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.EkYonetici;
import net.zemberek.yapi.ek.TemelEkYonetici;

/**
 */
public class KelimeUretici {

    private final Alfabe alfabe;
    private final CozumlemeYardimcisi yardimci;
    private final EkYonetici ekYonetici;

    public KelimeUretici(Alfabe alfabe, EkYonetici ekler, CozumlemeYardimcisi yardimci) {
        this.ekYonetici = ekler;
        this.alfabe = alfabe;
        this.yardimci = yardimci;
    }

    /**
     * Dogru sirali ek listesi ve uygun kok ile olusacak kelimeyi uretir.
     *
     * @param kok   : kelime koku
     * @param ekler : dogru sekilde sirali ek listesi.
     * @return String, eger ek listesi dogru ve koke uygun ise olusan kelime, yoksa "".
     */
    public String kelimeUret(Kok kok, List<Ek> ekler) {
        // defensive copying.
        List<Ek> eks = new ArrayList<Ek>(ekler);
        if (eks.size() > 0) {
            Ek ilkEk = ekYonetici.ilkEkBelirle(kok);
            if (!eks.get(0).equals(ilkEk))
                eks.add(0, ilkEk);
        }
        UretimNesnesi ure = uretimNesnesiUret(kok, eks);
        if (ure == null)
            return "";
        else
            return ure.olusum;
    }


    public String kelimeUret(Kok kok, Ek... ekler) {
        if (ekler == null || ekler.length == 0)
            return kelimeUret(kok, Collections.<Ek>emptyList());
        else
            return kelimeUret(kok, Arrays.asList(ekler));
    }

    /**
     * rasgele dizili bir ek listesini kullanarak olasi kelime uretir.
     *
     * @param kok   Kelime koku
     * @param ekler rasgele sirali ekler.
     * @return eger eklerin tamami uygun sekilde dizilebiliyorsa o dizilimle uretilen kelime stringi.
     *         eger eklerin tamami dizilemezse ya da ekler listesi bos ise bos String doner
     */
    String sirasizEklerleUret(Kok kok, List<Ek> ekler) {
        //defensive copying.
        List<Ek> eks = new ArrayList<Ek>(ekler);
        Ek ilkEk = ekYonetici.ilkEkBelirle(kok);
        if (ilkEk == TemelEkYonetici.BOS_EK)
            return kok.asil();
        if (eks.contains(ilkEk))
            eks.remove(ilkEk);
        List<List<Ek>> sonuclar = new EkSiralayici(eks, ilkEk).olasiEkDizilimleriniBul();
        if (sonuclar.isEmpty())
            return "";
        else {
            UretimNesnesi ure = uretimNesnesiUret(kok, sonuclar.get(0));
            if (ure == null)
                return "";
            else
                return ure.olusum;
        }
    }

    /**
     * Kok ve Ek listesi tasiyan bir kelimeyi String listesi seklinde parcalara ayirir.
     * Kelime {kok={kitap, ISIM} ekler = {ISIM_SAHIPLIK_BEN, ISIM_YONELME_E}} icin
     * {kitap,Im,a} dizisi doner.
     *
     * @param kelime : kelime
     * @return kok ve ek icerikleri (String[]) cinsinden dizi. Eger ek listesi bos ise ya da
     *         sadece yalin ek var ise sadece kok icerigi doner. Kokun ozel durum ile bozulmus hali degil
     *         orjinal icerigini iceren dizi doner.
     *         TODO:
     *         simdilik ozel adlarda bas harf kucuk olarak donuyor. Ayrica ozel yazimli koklerin orjinali
     *         degil ayiklanmis hali doner.
     */
    public String[] ayristir(Kelime kelime) {
        UretimNesnesi ure = uretimNesnesiUret(kelime.kok(), kelime.ekler());
        if (ure == null)
            return new String[0];
        return ure.olusumlar.toArray(new String[ure.olusumlar.size()]);
    }


    private UretimNesnesi uretimNesnesiUret(Kok kok, List<Ek> ekler) {

        if (kok == null)
            return new UretimNesnesi("");
        UretimNesnesi ure = new UretimNesnesi(kok.icerik());
        Kelime kelime = new Kelime(kok, alfabe);

        if (ekler.size() > 1) {
            HarfDizisi ozelDurumSonrasi = kok.ozelDurumUygula(alfabe, ekler.get(1));
            if (ozelDurumSonrasi != null)
                kelime.setIcerik(ozelDurumSonrasi);
            else
                return sonucBicimlendir(ure, kelime);
        } else {
            return sonucBicimlendir(ure, kelime);
        }

        yardimci.kokGirisDegismiVarsaUygula(kok, kelime.icerik(), null);

        for (int i = 0; i < ekler.size(); i++) {

            Ek ek = ekler.get(i);

            // eger incelenen ek onceki ekten sonra gelemezse cik.
            if (i > 0) {
                Ek oncekiEk = ekler.get(i - 1);
                if (!oncekiEk.ardindanGelebilirMi(ek)) {
                    return null;
                }
            }

            //olusum icin kural belirle ve eki olustur.
            HarfDizisi ekOlusumu;
            if (i < ekler.size() - 1)
                ekOlusumu = new HarfDizisi(ek.olusumIcinUret(kelime, ekler.get(i + 1)));
            else
                ekOlusumu = new HarfDizisi(ek.olusumIcinUret(kelime, TemelEkYonetici.BOS_EK));

            //eki kelimeye ve ek olusumlarina ekle.
            kelime.icerikEkle(ekOlusumu);
            if (ekOlusumu.length() > 0)
                ure.olusumlar.add(ekOlusumu.toString());
            kelime.ekEkle(ek);
        }

        //son duzeltmeleri uygula.
        return sonucBicimlendir(ure, kelime);
    }

    private UretimNesnesi sonucBicimlendir(UretimNesnesi ure, Kelime kelime) {
        yardimci.kelimeBicimlendir(kelime);
        ure.olusum = kelime.icerikStr();
        return ure;
    }

    class UretimNesnesi {

        String olusum = "";
        List<String> olusumlar = new ArrayList<String>(4);

        public UretimNesnesi(String ilkolusum) {
            this.olusum = ilkolusum;
            olusumlar.add(ilkolusum);
        }
    }
}
