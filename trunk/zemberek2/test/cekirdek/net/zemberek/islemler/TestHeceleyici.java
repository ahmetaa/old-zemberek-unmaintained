package net.zemberek.islemler;

import net.zemberek.TemelTest;
import net.zemberek.tr.yapi.TurkceHeceBulucu;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.HeceBulucu;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

/**
 */
public class TestHeceleyici extends TemelTest {

    Heceleyici heceleyici;

    @Before
    public void once() throws IOException {
        super.once();
        heceleyici = new Heceleyici(dilBilgisi.alfabe(), dilBilgisi.heceBulucu());
    }

    @Test
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

    @Test
    public void testHecelenemez() {
        String[] strs = {"tr", "r", "rty", "artpya", "kitttr", "kertreryt"};
        for (String str : strs)
            assertTrue(heceleyici.hecele(str).length == 0);

    }

    @Test
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

    @Test
    public void testHecelenebilirmi() {
        String strs[] = {"NATO", "merhabalar", "kimyev" + Alfabe.CHAR_SAPKALI_i, "BORA"};
        for (String s : strs)
            assertTrue("hecelenemedi:" + s, heceleyici.hecelenebilirmi(s));

        String ss[] = {"IBM", "lycos", "AwAtt", ".", "-"};
        for (String s : ss)
            assertFalse("hecelendi:" + s, heceleyici.hecelenebilirmi(s));

    }

    @Test
    public void testHeceIndeks1() {
        String kelime = "merhaba";
        int[] sonuclar = heceleyici.heceIndeksleriniBul(kelime);
        Arrays.toString(sonuclar);
        assertNotNull(sonuclar);
        assertEquals(3, sonuclar.length);
        assertEquals(3, sonuclar[1]);
        assertEquals(5, sonuclar[2]);
    }

    @Test
    public void testHeceIndeks2() {
        String kelime = "türklerin";
        int[] sonuclar = heceleyici.heceIndeksleriniBul(kelime);
        Arrays.toString(sonuclar);
        assertNotNull(sonuclar);
        assertEquals(3, sonuclar.length);
        assertEquals(4, sonuclar[1]);
        assertEquals(6, sonuclar[2]);
    }

    public void testHeceIndeks3() {
        String kelime = "türkülerin";
        int[] sonuclar = heceleyici.heceIndeksleriniBul(kelime);
        Arrays.toString(sonuclar);
        assertNotNull(sonuclar);
        assertEquals(4, sonuclar.length);
        assertEquals(3, sonuclar[1]);
        assertEquals(5, sonuclar[2]);
        assertEquals(7, sonuclar[3]);
    }

    public void testHeceIndeks4() {
        String kelime = "psikoloji";
        int[] sonuclar = heceleyici.heceIndeksleriniBul(kelime);
        Arrays.toString(sonuclar);
        assertNotNull(sonuclar);
        assertEquals(4, sonuclar.length);
        assertEquals(3, sonuclar[1]);
        assertEquals(5, sonuclar[2]);
        assertEquals(7, sonuclar[3]);
    }

}
