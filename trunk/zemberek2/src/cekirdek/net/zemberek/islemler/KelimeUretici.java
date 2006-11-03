package net.zemberek.islemler;

import net.zemberek.islemler.cozumleme.CozumlemeYardimcisi;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.TemelEkYonetici;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class KelimeUretici {

    private final Alfabe alfabe;
    private final CozumlemeYardimcisi yardimci;

    public KelimeUretici(Alfabe alfabe, CozumlemeYardimcisi yardimci) {
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
    public String kelimeUret(Kok kok, List ekler) {
        UretimNesnesi ure = uretimNesnesiUret(kok, ekler);
        return ure.olusum;
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
     *         simdilik ozle adlarda bas harf kucuk olarak donuyor. Ayrica ozel yazimli koklerin orjinali
     *         degil ayiklanmis hali doner.
     */
    public List<String> ayristir(Kelime kelime) {
        UretimNesnesi ure = uretimNesnesiUret(kelime.kok(), kelime.ekler());
        return ure.olusumlar;
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
                return ure;
        } else {
            return ure;
        }

        for (int i = 0; i < ekler.size(); i++) {

            Ek ek = ekler.get(i);

            // eger incelenen ek onceki ekten sonra gelemezse cik.
            if (i > 0) {
                Ek oncekiEk = ekler.get(i - 1);
                if (oncekiEk.ardindanGelebilirMi(ek)) {
                    return ure;
                }
            }

            //olusum icin kural belirle ve eki olustur.
            HarfDizisi ekOlusumu;
            if (i < ekler.size() - 1)
                ekOlusumu = new HarfDizisi(ek.olusumIcinUret(kelime, ekler.get(i + 1)));
            else
                ekOlusumu = new HarfDizisi(ek.olusumIcinUret(kelime, TemelEkYonetici.BOS_EK));

            //TODO: asagidaki bolum dil ozel. muhtemelen olusumIcinURet metodu duzletilirse gerek kalmaz.
            // ek son harf yumusatmayi kendimiz hallediyoruz (eger yalin ek ise bu islemi pas geciyoruz.)
            if (i > 1) {
                if (kelime.icerik().sonHarf().sertMi() && ekOlusumu.ilkHarf().sesliMi())
                    kelime.icerik().sonHarfYumusat();
            }

            //eki kelimeye ve ek olusumlarina ekle.
            kelime.icerik().ekle(ekOlusumu);
            if (ekOlusumu.length() > 0)
                ure.olusumlar.add(ekOlusumu.toString());
            kelime.ekler().add(ek);
        }

        //son duzeltmeleri uygula.
        yardimci.kelimeBicimlendir(kelime);
        ure.olusum = kelime.icerik().toString();
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
