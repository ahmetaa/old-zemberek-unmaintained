package net.zemberek.araclar.turkce;

import junit.framework.TestCase;

import java.util.List;

/**
 */
public class TestExtractor extends TestCase {
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
