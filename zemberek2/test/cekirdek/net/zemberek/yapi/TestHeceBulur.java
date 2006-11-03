package net.zemberek.yapi;

import net.zemberek.TemelTest;
import net.zemberek.tr.yapi.TurkceHeceBulucu;

/**
 * User: ahmet
 * Date: Sep 10, 2005
 */
public class TestHeceBulur extends TemelTest {

    public void testSonHece() {
        HeceBulucu heceBulur = new TurkceHeceBulucu(alfabe);
        String[] strs = {"turk", "ara", "sarta", "siir", "siiir", "kanat", "kanaat",
                "yaptirt", "artti", "arttir", "arttirt", "sirret", "siirt", "teleskop"};
        int[] sonuclar = {4, 2, 2, 2, 2, 3, 2,
                4, 2, 3, 4, 3, 3, 3};
        HarfDizisi[] girisler = new HarfDizisi[strs.length];
        for (int i = 0; i < strs.length; i++)
            girisler[i] = hd(strs[i]);
        for (int i = 0; i < girisler.length; i++) {
            HarfDizisi harfDizisi = girisler[i];
            assertEquals(harfDizisi.toString(), heceBulur.sonHeceHarfSayisi(harfDizisi), sonuclar[i]);
        }
    }
}
