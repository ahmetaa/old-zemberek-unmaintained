package net.zemberek.yapi.ek;

import net.zemberek.TemelTest;
import net.zemberek.islemler.cozumleme.KesinHDKiyaslayici;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;

/**
 * User: aakin
 * Date: Feb 24, 2004
 */
public class BaseTestEkler extends TemelTest {

    protected Kelime[] kelimeler;

    public BaseTestEkler() {
        super();
    }

    protected void olusanEkKontrol(String[] strs, String[] gercek, Ek ek) {
        String[] olusanEkler;
        kelimeleriOlustur(strs);
        olusanEkler = ekleriOlustur(ek);
        for (int i = 0; i < gercek.length; i++) {
            assertEquals("Hatali olusum:" + olusanEkler[i], olusanEkler[i], gercek[i]);
        }
    }

    protected void kelimeleriOlustur(String[] strs) {
        kelimeler = new Kelime[strs.length];
        for (int i = 0; i < strs.length; i++) {
            kelimeler[i] = new Kelime(new Kok(strs[i], KelimeTipi.FIIL), alfabe);
        }
    }

    protected String[] ekleriOlustur(Ek ek) {
        String[] olusan = new String[kelimeler.length];
        for (int i = 0; i < kelimeler.length; i++) {
            olusan[i] = ek.cozumlemeIcinUret(kelimeler[i], kelimeler[i].icerik(),
                    new KesinHDKiyaslayici()).toString();
        }
        return olusan;
    }

    public void testEmpty() {
        assertTrue(true);
    }
}
