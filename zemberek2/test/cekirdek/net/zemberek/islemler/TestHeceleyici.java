package net.zemberek.islemler;

import net.zemberek.TemelTest;
import net.zemberek.tr.yapi.TurkceHeceBulucu;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.HeceBulucu;

import java.io.IOException;

/**
 */
public class TestHeceleyici extends TemelTest {

    Heceleyici heceleyici;

    public void setUp() throws IOException {
        super.setUp();
        heceleyici = new Heceleyici(dilBilgisi.alfabe(), dilBilgisi.heceBulucu());
    }

    public void testHeceleyici() {
        String kelime = "kanaat";
        String[] beklenen = {"ka", "na", "at"};
        String[] sonuc = heceleyici.hecele(kelime);
        assertTrue(sonuc.length == 3);
        for (int i = 0; i < sonuc.length; i++) {
            String s = sonuc[i];
            assertEquals(s, beklenen[i]);
        }

        kelime = "durttur";
        String[] beklenen2 = {"durt", "tur"};
        String[] sonuc2 = heceleyici.hecele(kelime);
        assertTrue(sonuc2.length == 2);
        for (int i = 0; i < sonuc2.length; i++) {
            String s = sonuc2[i];
            assertEquals(s, beklenen2[i]);
        }
    }

    public void testHecelenemez() {
        String[] strs = {"tr", "r", "rty", "artpya", "kitttr", "kertreryt"};
        for (int i = 0; i < strs.length; i++)
            assertTrue(heceleyici.hecele(strs[i]).length == 0);

    }

    public void testSonHece() {
        HeceBulucu heceBulur = new TurkceHeceBulucu(alfabe);
        String[] strs = {"turk", "ara", "sarta", "siir", "siiir", "kanat", "kanaat",
                "yaptirt", "artti", "arttir", "arttirt", "sirret", "siirt", "teleskop"};
        int[] sonuclar = {4, 2, 2, 2, 2, 3, 2,
                4, 2, 3, 4, 3, 3, 3};
        HarfDizisi[] girisler = new HarfDizisi[strs.length];
        for (int i = 0; i < strs.length; i++)
            girisler[i] = new HarfDizisi(strs[i], alfabe);
        for (int i = 0; i < girisler.length; i++) {
            HarfDizisi harfDizisi = girisler[i];
            assertEquals(harfDizisi.toString(), heceBulur.sonHeceHarfSayisi(harfDizisi), sonuclar[i]);
        }
    }

    public void testHecelenebilirmi() {
        String strs[] = {"NATO", "merhabalar", "kimyev" + Alfabe.CHAR_SAPKALI_i, "BORA"};
        for (String s : strs)
            assertTrue("hecelenemedi:" + s, heceleyici.hecelenebilirmi(s));

        String ss[] = {"IBM", "lycos", "AwAtt", ".", "-"};
        for (String s : ss)
            assertFalse("hecelendi:" + s, heceleyici.hecelenebilirmi(s));

    }

    public void testHeceIndeks1() {
        String kelime = "merhaba";
        int[] sonuc = heceleyici.heceIndeksleriniBul(kelime);
        for (int i = 0; i < sonuc.length; i++) {
            System.out.println(sonuc[i]);
        }
        assertNotNull(sonuc);
        assertEquals(3, sonuc.length);
        assertEquals(3, sonuc[1]);
        assertEquals(5, sonuc[2]);
    }

    public void testHeceIndeks2() {
        String kelime = "türklerin";
        int[] sonuc = heceleyici.heceIndeksleriniBul(kelime);
        for (int i = 0; i < sonuc.length; i++) {
            System.out.println(sonuc[i]);
        }
        assertNotNull(sonuc);
        assertEquals(3, sonuc.length);
        assertEquals(4, sonuc[1]);
        assertEquals(6, sonuc[2]);
    }

    public void testHeceIndeks3() {
        String kelime = "türkülerin";
        int[] sonuc = heceleyici.heceIndeksleriniBul(kelime);
        for (int i = 0; i < sonuc.length; i++) {
            System.out.println(sonuc[i]);
        }
        assertNotNull(sonuc);
        assertEquals(4, sonuc.length);
        assertEquals(3, sonuc[1]);
        assertEquals(5, sonuc[2]);
        assertEquals(7, sonuc[3]);
    }

    public void testHeceIndeks4() {
        String kelime = "psikoloji";
        int[] sonuc = heceleyici.heceIndeksleriniBul(kelime);
        for (int i = 0; i < sonuc.length; i++) {
            System.out.println(sonuc[i]);
        }
        assertNotNull(sonuc);
        assertEquals(4, sonuc.length);
        assertEquals(3, sonuc[1]);
        assertEquals(5, sonuc[2]);
        assertEquals(7, sonuc[3]);
    }

}
