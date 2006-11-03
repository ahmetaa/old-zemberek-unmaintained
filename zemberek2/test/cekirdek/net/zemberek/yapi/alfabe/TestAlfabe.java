package net.zemberek.yapi.alfabe;

import junit.framework.TestCase;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.TurkceHarf;

import java.io.IOException;

public class TestAlfabe extends TestCase {

    private Alfabe alfabe;

    public void setUp() throws IOException {
        alfabe = new Alfabe(
                "kaynaklar/tr/bilgi/harf_tr.txt",
                "tr");
    }

    public void testHarfErisim() {
        TurkceHarf harf = new TurkceHarf('a', 1);
        harf.setSesli(true);

        TurkceHarf okunan = alfabe.harf('a');
        assertEquals(harf.charDeger(), okunan.charDeger());
        assertTrue(harf.sesliMi());

    }

    public void testAyikla() {
        String kel = "a'ghh-";
        assertEquals(alfabe.ayikla(kel), "aghh");
    }

    public void testTurkceMi() {
        String kel = "wws$$dgsdashj";
        assertTrue(!alfabe.cozumlemeyeUygunMu(kel));
        kel = "merhaba";
        assertTrue(alfabe.cozumlemeyeUygunMu(kel));
    }

    public void testLowerUpperCase() {
        TurkceHarf ii = alfabe.harf(Alfabe.CHAR_ii);
        TurkceHarf harfI = alfabe.buyukHarf(ii);
        assertEquals(harfI.charDeger(), Alfabe.CHAR_II);
        TurkceHarf i = alfabe.harf('i');
        TurkceHarf harfBuyuki = alfabe.buyukHarf(i);
        assertEquals(harfBuyuki.charDeger(), 'I');
    }

}
