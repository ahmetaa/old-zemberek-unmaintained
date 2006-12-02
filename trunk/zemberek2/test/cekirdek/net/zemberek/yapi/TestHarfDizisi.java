package net.zemberek.yapi;

import net.zemberek.TemelTest;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * User: aakin
 * Date: Feb 24, 2004
 */
public class TestHarfDizisi extends TemelTest {

    String str;
    HarfDizisi dizi1;

    @Before
    public void once() throws IOException {
        super.once();
        str = "kalem";
        dizi1 = new HarfDizisi(str, alfabe);
    }

    @Test
    public void testSonSesli() {
        assertEquals("Yanlis sonsesli " + dizi1.sonSesli().charDeger(), dizi1.sonSesli(), alfabe.harf('e'));
    }

    @Test
    public void testSonHarf() {
        assertEquals("Yanlis son harf " + dizi1.sonHarf().charDeger(), dizi1.sonHarf(), alfabe.harf('m'));
    }

    @Test
    public void testHarfEkle() {
        dizi1.ekle(alfabe.harf('s'));
        dizi1.ekle(alfabe.harf('i'));
        assertEquals("harf ekleme problemi", alfabe.harf('i'), dizi1.sonHarf());
    }

    @Test
    public void testDiziEkle() {
        HarfDizisi dizi2 = new HarfDizisi("ler", alfabe);
        dizi1.ekle(dizi2);
        assertEquals("dizi1 ekleme problemi " + dizi1.toString(), dizi1.toString(), "kalemler");
    }

    @Test
    public void testAradanEkle() {
        HarfDizisi dizi = hd("kale");
        HarfDizisi ek = hd("ku");
        dizi.ekle(2, ek);
        assertEquals(dizi.toString(), "kakule");
        ek = hd("kara");
        dizi.ekle(0, ek);
        assertEquals(dizi.toString(), "karakakule");
        try {
            dizi.ekle(20, ek);
            fail("Exception olmai gerekirdi");
            dizi.ekle(-1, ek);
        } catch (IndexOutOfBoundsException expected) {
            assertTrue(true);
        }
    }

    @Test
    public void testAradanSil() {
        HarfDizisi dizi = hd("abcdefg");
        assertEquals(dizi.harfSil(2, 3).toString(), "abfg");
        dizi = hd("abcdefg");
        assertEquals(dizi.harfSil(4, 7).toString(), "abcd");
        dizi = hd("abcdefg");
        assertEquals(dizi.harfSil(0, 4).toString(), "efg");
        dizi = hd("abcdefg");
        assertEquals(dizi.harfSil(1, 4).toString(), "afg");

        try {
            dizi.harfSil(20, 1);
            dizi.harfSil(-1, 2);
            fail("Exception olmasi gerekirdi");
        } catch (IndexOutOfBoundsException expected) {
            assertTrue(true);
        }
    }

    @Test
    public void testAradanKarsilastir() {
        HarfDizisi kelime = new HarfDizisi("kedicikler", alfabe);
        HarfDizisi o1 = new HarfDizisi("cik", alfabe);
        HarfDizisi o2 = new HarfDizisi("er", alfabe);
        HarfDizisi o3 = new HarfDizisi("ere", alfabe);
        assertTrue(kelime.aradanKiyasla(4, o1));
        assertTrue(kelime.aradanKiyasla(5, o1) == false);
        assertTrue(kelime.aradanKiyasla(11, o1) == false);
        assertTrue(kelime.aradanKiyasla(8, o2));
        assertTrue(kelime.aradanKiyasla(8, o3) == false);
    }

    @Test
    public void testHarfDegistir() {
        HarfDizisi kelime = new HarfDizisi("kedicikler", alfabe);
        kelime.harfDegistir(0, alfabe.harf('c'));
        kelime.harfDegistir(3, alfabe.harf('a'));
        assertEquals(kelime.toString(), "cedacikler");
    }

    @Test
    public void testHarfSil() {
        HarfDizisi kelime = new HarfDizisi("kedicikler", alfabe);
        kelime.harfSil(9);
        assertEquals(kelime.toString(), "kedicikle");
        kelime.harfSil(0);
        assertEquals(kelime.toString(), "edicikle");
        kelime.harfSil(3);
        assertEquals(kelime.toString(), "ediikle");
    }

    @Test
    public void testIlkSesli() {
        HarfDizisi kelime = new HarfDizisi("saatte", alfabe);
        assertEquals(kelime.ilkSesli(0), alfabe.harf('a'));
        assertEquals(kelime.ilkSesli(1), alfabe.harf('a'));
        assertEquals(kelime.ilkSesli(3), alfabe.harf('e'));
        assertEquals(kelime.ilkSesli(6), Alfabe.TANIMSIZ_HARF);
    }

    @Test
    public void testSonHarfYumusat() {
        HarfDizisi kelime = new HarfDizisi("kitap", alfabe);
        kelime.sonHarfYumusat();
        assertEquals(kelime.sonHarf(), alfabe.harf('b'));
        kelime = new HarfDizisi("armut", alfabe);
        kelime.sonHarfYumusat();
        assertEquals(kelime.sonHarf(), alfabe.harf('d'));
        kelime = new HarfDizisi("kulak", alfabe);
        kelime.sonHarfYumusat();
        assertEquals(kelime.sonHarf(), alfabe.harf(Alfabe.CHAR_gg));
    }

    @Test
    public void testKirp() {
        HarfDizisi dizi = new HarfDizisi("merhaba", alfabe);
        dizi.kirp(5);
        assertEquals("merha", dizi.toString());
        dizi.kirp(5);
        assertEquals("merha", dizi.toString());
        dizi.kirp(0);
        assertTrue(dizi.length() == 0);
    }

    @Test
    public void testtoStringIndex() {
        HarfDizisi dizi = new HarfDizisi("merhaba", alfabe);
        assertEquals(dizi.toString(4), "aba");
        assertEquals(dizi.toString(0), "merhaba");
        assertEquals(dizi.toString(7), "");
        assertEquals(dizi.toString(-1), "");
        assertEquals(dizi.toString(6), "a");
    }

    @Test
    public void testBastanKarsilastir() {
        HarfDizisi dizi = new HarfDizisi("merhaba", alfabe);
        assertTrue(dizi.bastanKiyasla(new HarfDizisi("m", alfabe)));
        assertTrue(dizi.bastanKiyasla(new HarfDizisi("merha", alfabe)));
        assertTrue(dizi.bastanKiyasla(new HarfDizisi("merhaba", alfabe)));
        assertTrue(dizi.bastanKiyasla(new HarfDizisi("merhabal", alfabe)) == false);
    }

    @Test
    public void testEquals() {
        HarfDizisi dizi = new HarfDizisi("merhaba", alfabe);
        assertTrue(dizi.equals(new HarfDizisi("merhaba", alfabe)));
        assertTrue(dizi.equals(new HarfDizisi("merha", alfabe)) == false);
        assertTrue(dizi.equals(new HarfDizisi("merhabalar", alfabe)) == false);
        dizi = new HarfDizisi("merhaba", alfabe, 20);
        assertTrue(dizi.equals(new HarfDizisi("merhaba", alfabe)));
        assertTrue(dizi.equals(new HarfDizisi("merhaba", alfabe, 15)));
        assertTrue(dizi.equals(new HarfDizisi("merhabalar", alfabe, 15)) == false);
    }

    @Test
    public void testSesliSayisi() {
        HarfDizisi dizi = new HarfDizisi("merhaba", alfabe);
        assertTrue(dizi.sesliSayisi() == 3);
        dizi = new HarfDizisi("aarteetytye", alfabe);
        assertTrue(dizi.sesliSayisi() == 5);
        dizi = new HarfDizisi("art", alfabe);
        assertTrue(dizi.sesliSayisi() == 1);
        dizi = new HarfDizisi("rrt", alfabe);
        assertTrue(dizi.sesliSayisi() == 0);
    }

    @Test
    public void testTurkceToleransliKiyasla() {
        HarfDizisi hd1 = new HarfDizisi("\u00c7\u0131k\u0131s", alfabe);
        HarfDizisi hd2 = new HarfDizisi("Ciki\u015f", alfabe);
        HarfDizisi hdkisa = new HarfDizisi("Ci", alfabe);
        HarfDizisi hdtkisaturkce = new HarfDizisi("\u00c7\u0131", alfabe);
        assertTrue(hd1.asciiToleransliKiyasla(hd2));
        assertTrue(hd2.asciiToleransliKiyasla(hd1));
        assertTrue(hd1.asciiToleransliBastanKiyasla(hdkisa));
        assertTrue(hd2.asciiToleransliBastanKiyasla(hdkisa));
        assertTrue(hd2.asciiToleransliBastanKiyasla(hdtkisaturkce));
    }

    @Test
    public void testHarfEkleHarf() {
        HarfDizisi dizi = new HarfDizisi("armut", alfabe);
        dizi.ekle(2, alfabe.harf('n'));
        assertEquals(dizi.toString(), "arnmut");
        dizi.ekle(1, alfabe.harf('c'));
        assertEquals(dizi.toString(), "acrnmut");
        dizi.ekle(0, alfabe.harf('s'));
        assertEquals(dizi.toString(), "sacrnmut");
        dizi.ekle(8, alfabe.harf('a'));
        assertEquals(dizi.toString(), "sacrnmuta");
    }

    @Test
    public void testCharSequenceMethods() {
        HarfDizisi dizi = new HarfDizisi("armut", alfabe);
        assertEquals(dizi.length(), 5);
        assertEquals(dizi.subSequence(0, 3), new HarfDizisi("arm", alfabe));
        assertEquals(dizi.subSequence(1, 3), new HarfDizisi("rm", alfabe));
        assertEquals(dizi.subSequence(3, 3), new HarfDizisi("", alfabe));
        assertNull(dizi.subSequence(3, 1));
        assertEquals(dizi.charAt(0), 'a');
        assertEquals(dizi.charAt(4), 't');
    }

    @Test
    public void testHepsiBuyukHarfmi() {
        String strs[] = {"AA", "AA" + Alfabe.CHAR_SAPKALI_A, "AWAQ", ""};
        for (String s : strs) {
            HarfDizisi d = hd(s);
            assertTrue("olmadi" + s, d.hepsiBuyukHarfmi());
        }
        String ss[] = {"aaa", "Aa", "AA"+Alfabe.CHAR_SAPKALI_a, "AwAQ", "..A", "-"};
        for (String s : ss) {
            HarfDizisi d = hd(s);
            assertFalse("olmadi" + s, d.hepsiBuyukHarfmi());
        }

    }
}
