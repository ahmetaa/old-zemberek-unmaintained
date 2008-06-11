/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.alfabe;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.TurkceHarf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class TestAlfabe {

    private Alfabe alfabe;

    @Before
    public void once() throws IOException {
        alfabe = new Alfabe(
                "kaynaklar/tr/bilgi/harf_tr.txt",
                "tr");
    }

    @Test
    public void testHarfErisim() {
        TurkceHarf harf = new TurkceHarf('a', 1);
        harf.setSesli(true);

        TurkceHarf okunan = alfabe.harf('a');
        assertEquals(harf.charDeger(), okunan.charDeger());
        assertTrue(harf.sesliMi());

    }

    @Test
    public void testAyikla() {
        String kel = "a'ghh-";
        assertEquals(alfabe.ayikla(kel), "aghh");
    }

    @Test
    public void testTurkceMi() {
        String kel = "wws$$dgsdashj";
        assertTrue(!alfabe.cozumlemeyeUygunMu(kel));
        kel = "merhaba";
        assertTrue(alfabe.cozumlemeyeUygunMu(kel));
    }

    @Test
    public void testLowerUpperCase() {
        TurkceHarf ii = alfabe.harf(Alfabe.CHAR_ii);
        TurkceHarf harfI = alfabe.buyukHarf(ii);
        assertEquals(harfI.charDeger(), 'I');
        TurkceHarf i = alfabe.harf('i');
        TurkceHarf harfBuyuki = alfabe.buyukHarf(i);
        assertEquals(harfBuyuki.charDeger(), Alfabe.CHAR_II);
    }

}
