/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler;

import net.zemberek.TemelTest;
import net.zemberek.yapi.Alfabe;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

/**
 */
public class TestHeceleyici extends TemelTest {

    HeceIslemleri heceIslemleri;

    @Before
    public void once() throws IOException {
        super.once();
        heceIslemleri = new HeceIslemleri(dilBilgisi.alfabe(), dilBilgisi.heceBulucu());
    }

    @Test
    public void testHeceleyici() {
        String kelime = "kanaat";
        String[] beklenen = {"ka", "na", "at"};
        String[] sonuc = heceIslemleri.hecele(kelime);
        assertTrue(sonuc.length == 3);
        for (int i = 0; i < sonuc.length; i++) {
            String s = sonuc[i];
            assertEquals(s, beklenen[i]);
        }

        kelime = "durttur";
        String[] beklenen2 = {"durt", "tur"};
        String[] sonuc2 = heceIslemleri.hecele(kelime);
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
            assertTrue(heceIslemleri.hecele(str).length == 0);

    }

    @Test
    public void testHecelenebilirmi() {
        String strs[] = {"NATO", "merhabalar", "kimyev" + Alfabe.CHAR_SAPKALI_i, "BORA"};
        for (String s : strs)
            assertTrue("hecelenemedi:" + s, heceIslemleri.hecelenebilirmi(s));

        String ss[] = { "lycos", "AwAtrt", ".", "-"};
        for (String s : ss)
            assertFalse("hecelendi:" + s, heceIslemleri.hecelenebilirmi(s));

    }

    @Test
    public void testHeceIndeks1() {
        String kelime = "merhaba";
        int[] sonuclar = heceIslemleri.heceIndeksleriniBul(kelime);
        Arrays.toString(sonuclar);
        assertNotNull(sonuclar);
        assertEquals(3, sonuclar.length);
        assertEquals(3, sonuclar[1]);
        assertEquals(5, sonuclar[2]);
    }

    @Test
    public void testHeceIndeks2() {
        String kelime = "turklerin";
        int[] sonuclar = heceIslemleri.heceIndeksleriniBul(kelime);
        Arrays.toString(sonuclar);
        assertNotNull(sonuclar);
        assertEquals(3, sonuclar.length);
        assertEquals(4, sonuclar[1]);
        assertEquals(6, sonuclar[2]);
    }

    @Test
    public void testHeceIndeks3() {
        String kelime = "turkulerin";
        int[] sonuclar = heceIslemleri.heceIndeksleriniBul(kelime);
        Arrays.toString(sonuclar);
        assertNotNull(sonuclar);
        assertEquals(4, sonuclar.length);
        assertEquals(3, sonuclar[1]);
        assertEquals(5, sonuclar[2]);
        assertEquals(7, sonuclar[3]);
    }
    
    @Test
    public void testHeceIndeks4() {
        String kelime = "psikoloji";
        int[] sonuclar = heceIslemleri.heceIndeksleriniBul(kelime);
        Arrays.toString(sonuclar);
        assertNotNull(sonuclar);
        assertEquals(4, sonuclar.length);
        assertEquals(3, sonuclar[1]);
        assertEquals(5, sonuclar[2]);
        assertEquals(7, sonuclar[3]);
    }

}
