package net.zemberek.yapi;

import net.zemberek.yapi.ek.Ek;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: ahmet
 * Date: Sep 6, 2005
 */

/**
 * Bu sinif rasgele bir sirada gelen ek ve kok bilgisini kullanarak olasi dogru ek dizilimlerini uretir
 * <p/>
 * aakin,Sep 6, 2005
 */
public class EkSiralayici {

    private List<List<Ek>> tumOlusumlar;
    private List<Ek> ekler;
    private Ek baslangicEki;
    private int tum;

    public EkSiralayici(List ekler, Ek baslangicEki) {
        this.ekler = ekler;
        this.baslangicEki = baslangicEki;
        this.tum = ekler.size();
    }

    /**
     * olasi dogru ek dizilimlerini bulur.
     *
     * @return List<List<Ek>> Her bir dogru ek dizilimi bir listede tutulur. Donus ise tum dogru
     *         ek dizilim listelerini tutan baska bir liste. Eger liste bos ise dogru dizilim bulunamamis demektir.
     */
    public List<List<Ek>> olasiEkDizilimleriniBul() {
        if (ekler == null)
            return Collections.EMPTY_LIST;
        tumOlusumlar = new ArrayList<List<Ek>>();
        List<Ek> kopya = new ArrayList(ekler);
        yuru(new ArrayList<Ek>(), baslangicEki, kopya);
        return tumOlusumlar;
    }


    /**
     * Bu metod rasgele girilen bir ek dizisinin olasi dogru siralamalarini bulur.
     * Metod kendi kendini cagiran bir yapidadir (recursive). Sonucta dogru olabilecek dizilimler
     * nesne icindeki "tumOlusumlar" dizisine atilir.
     *
     * @param olusan:       ic calisma sirasinda dogru olusan dizilimi tasiyan ArrayList.
     * @param incelenenEk:  Kok'un tipine gore gereken baslangic eki. FIIL ise FIIL_YALIN vs.
     * @param rasgeleEkler: rasgele sirali ekleri tasiyan liste.
     */
    private void yuru(List<Ek> olusan, Ek incelenenEk, List<Ek> rasgeleEkler) {
        for (int i = 0; i < rasgeleEkler.size(); i++) {
            Ek ek = rasgeleEkler.get(i);
            if (incelenenEk.ardindanGelebilirMi(ek)) {
                List newList = new ArrayList(rasgeleEkler);
                newList.remove(i);
                olusan.add(ek);
                if (newList.size() != 0)
                    yuru(olusan, ek, newList);
            }
        }
        if (olusan.size() == tum) {
            if (!this.tumOlusumlar.contains(olusan))
                this.tumOlusumlar.add(olusan);
            olusan = new ArrayList();
        } else {
            rasgeleEkler.add(incelenenEk);
            if (olusan.size() > 0)
                olusan.remove(olusan.size() - 1);
        }
    }
}
