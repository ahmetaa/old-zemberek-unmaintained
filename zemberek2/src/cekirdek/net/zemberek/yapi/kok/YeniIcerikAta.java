/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.kok;

import java.util.Map;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;

/**
 * kuralsiz kok bozulmasi ozel durumlarinda kullanilir.
 * uretim parametresi olarak gelen Map icerisinde hangi kelimenin hangi kelimeye
 * donusecegi belirtilir.
 * ornegin
 * demek->diyen icin de->di donusumu, ben->bana icin ben->ban donusumu.
 */
public class YeniIcerikAta implements HarfDizisiIslemi {

    private Map<String, String> kokDonusum;
    private Alfabe alfabe;

    public YeniIcerikAta( Alfabe alfabe, Map<String, String> kokDonusum) {
        this.kokDonusum = kokDonusum;
        this.alfabe = alfabe;
    }

    public void uygula(HarfDizisi dizi) {
        String kelime = kokDonusum.get(dizi.toString());
        if (kelime != null) {
            dizi.sil();
            dizi.ekle(new HarfDizisi(kelime, alfabe));
        }
    }
}
