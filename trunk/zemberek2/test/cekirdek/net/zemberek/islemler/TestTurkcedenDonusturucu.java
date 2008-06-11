/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler;

import net.zemberek.TemelTest;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 */
public class TestTurkcedenDonusturucu extends TemelTest {

    @Test
    public void testToAscii() {
        AsciiDonusturucu donusturucu = new AsciiDonusturucu(alfabe);
        String turkce = "abci\u00e7\u011f\u0131\u00f6\u015f\u00fc";
        String sonuc = donusturucu.toAscii(turkce);
        assertEquals("abcicgiosu", sonuc);
        String abuk = "32432aas_";
        sonuc = donusturucu.toAscii(abuk);
        assertEquals("32432aas_", sonuc);
    }

}
