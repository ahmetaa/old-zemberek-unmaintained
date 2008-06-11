/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.araclar.turkce;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.List;

/**
 */
public class TestExtractor  {
    @Test
    public void testAnalizDizisiOlustur() {
        String giris = "Merhaba, ben Ahmet!.  Nasilsiniz? ";
        String[] parcalar = {"Merhaba",
                             ", ",
                             "ben",
                             " ",
                             "Ahmet",
                             "!.  ",
                             "Nasilsiniz",
                             "? "};
        List analizDizisi = YaziIsleyici.analizDizisiOlustur(giris);
        assertEquals(analizDizisi.size(), parcalar.length);
        for (int i = 0; i < parcalar.length; i++) {
            YaziBirimi birim = (YaziBirimi) analizDizisi.get(i);
            assertEquals(parcalar[i], birim.icerik);

        }
    }
}
