package net.zemberek.araclar.turkce;

import junit.framework.TestCase;

import java.util.List;

/**
 */
public class TestYaziIsleyici extends TestCase {

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
        YaziBirimiTipi[] tipler = {YaziBirimiTipi.KELIME, //Merhaba
                                   YaziBirimiTipi.DIGER, //,
                                   YaziBirimiTipi.KELIME, //ben
                                   YaziBirimiTipi.DIGER, //
                                   YaziBirimiTipi.KELIME, //Ahmet
                                   YaziBirimiTipi.DIGER, //!.
                                   YaziBirimiTipi.KELIME, //Nasilsiniz
                                   YaziBirimiTipi.DIGER //?
        };
        List analizDizisi = YaziIsleyici.analizDizisiOlustur(giris);
        assertEquals(analizDizisi.size(), parcalar.length);
        for (int i = 0; i < parcalar.length; i++) {
            YaziBirimi birim = (YaziBirimi) analizDizisi.get(i);
            assertEquals(parcalar[i], birim.icerik);
            //assertEquals(tipler[i],birim.tip);
            assertEquals("hatali" + i + ":", tipler[i], birim.tip);

        }
    }

    public void testKelimeAyikla() {
        String giris = "Merhaba, ben Ahmet!.  Nasilsiniz? ";
        String[] parcalar = {"Merhaba",
                             "ben",
                             "Ahmet",
                             "Nasilsiniz",
        };
        List kelimeler = YaziIsleyici.kelimeAyikla(giris);
        assertEquals(kelimeler.size(), parcalar.length);
        for (int i = 0; i < parcalar.length; i++) {
            String birim = (String) kelimeler.get(i);
            assertEquals(parcalar[i], birim);

        }


    }

    public void testAnalizIcinKelimeAyikla() {
        String giris = "Merhaba, ben Ahmet!.  Nasilsiniz? ";
        String[] parcalar = {"ben"
        };
        List kelimeler = YaziIsleyici.analizIcinKelimeAyikla(giris);

        assertEquals(kelimeler.size(), parcalar.length);
        for (int i = 0; i < parcalar.length; i++) {
            String birim = (String) kelimeler.get(i);
            assertEquals(parcalar[i], birim);

        }


    }

    public void testCumleAyikla() {
        String giris = "Merhaba, ben Ahmet!. Nasilsiniz?";
        String[] cumle = {"Merhaba, ben Ahmet!.",
                          "Nasilsiniz?"};
        List cumleler = YaziIsleyici.cumleAyikla(giris);
        assertEquals(cumleler.size(), 2);
        for (int i = 0; i < cumle.length; i++) {
            String birim = (String) cumleler.get(i);
            assertEquals(cumle[i], birim.trim());

        }
    }


}
