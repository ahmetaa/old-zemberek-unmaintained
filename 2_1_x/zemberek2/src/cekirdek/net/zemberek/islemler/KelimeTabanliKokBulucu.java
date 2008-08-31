/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.zemberek.islemler.cozumleme.CozumlemeSeviyesi;
import net.zemberek.islemler.cozumleme.KelimeCozumleyici;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;

/**
 * basit kelime tabanli kok bulucu. simdilik sadece kelime frekansina gore siralama yapar.
 * (eger kelime frekans bilgisi mevcutsa)
 */
public class KelimeTabanliKokBulucu implements KokBulucu {

    KelimeCozumleyici cozumleyici;
    Alfabe alfabe;


    public KelimeTabanliKokBulucu(KelimeCozumleyici cozumleyici, Alfabe alfabe) {
        this.cozumleyici = cozumleyici;
        this.alfabe = alfabe;
    }

    /**
     * <b>EN:</b>Finds possible root words for an input word.
     * <p/>
     * <b>TR:</b>
     * girilen kelime icin olasi kelime koklerini Kok dizisi olarak dondurur.
     *
     * @param giris :  giris kelimesi
     * @return EN: root words as a Kok array.
     *         TR: girise uygun olabilecek kokler, Kok dizisi seklinde.
     */
    public Kok[] kokBul(String giris) {
        Kelime[] cozumler = cozumleVeSirala(giris);
        List<Kok> sonuclar = new ArrayList<Kok>(cozumler.length);
        for (Kelime kelime : cozumler) {
            if (!sonuclar.contains(kelime.kok()))
                sonuclar.add(kelime.kok());
        }
        return sonuclar.toArray(new Kok[sonuclar.size()]);
    }

    private Kelime[] cozumleVeSirala(String giris) {
        Kelime[] cozumler = cozumleyici.cozumle(giris, CozumlemeSeviyesi.TUM_KOKLER);
        Arrays.sort(cozumler, new KelimeKokFrekansKiyaslayici());
        return cozumler;
    }

    /**
     * <b>EN:</b>Finds possible root words for an input word.
     * <p/>
     * <b>TR:</b>
     * girilen kelime icin olasi kelime koklerini String dizisi olarak dondurur.
     *
     * @param giris :  giris kelimesi
     * @return EN: root words as a String array.
     *         TR: girise uygun olabilecek kokler, String dizisi seklinde.
     */
    public String[] stringKokBul(String giris) {
        Kelime[] cozumler = cozumleVeSirala(giris);
        List<String> sonuclar = new ArrayList<String>(cozumler.length);
        for (Kelime kelime : cozumler) {
            String s = kelime.kok().asilIcerikUret(alfabe);
            if (!sonuclar.contains(s))
                sonuclar.add(s);
        }
        return sonuclar.toArray(new String[sonuclar.size()]);
    }
}
