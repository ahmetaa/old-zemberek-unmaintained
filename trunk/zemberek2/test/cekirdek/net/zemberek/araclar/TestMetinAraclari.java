/*
 * Created on 13.Mar.2004
 */
package net.zemberek.araclar;

import net.zemberek.araclar.turkce.YaziIsleyici;
import static org.junit.Assert.*;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author MDA & GBA
 */
public class TestMetinAraclari {

    public void testInjectError() {

    }

    @Test
    public void testEditDistance() {
        assertEquals(0, MetinAraclari.editDistance("elma", "elma"));
        assertEquals(1, MetinAraclari.editDistance("elma", "elmax"));
        assertEquals(1, MetinAraclari.editDistance("elma", "lma"));
        assertEquals(2, MetinAraclari.editDistance("elma", "ma"));
        assertEquals(2, MetinAraclari.editDistance("elma", "frma"));
        assertEquals(3, MetinAraclari.editDistance("elma", "a"));
        assertEquals(3, MetinAraclari.editDistance("elma", "elmalar"));
        assertEquals(4, MetinAraclari.editDistance("elma", "elmalara"));
        assertEquals(4, MetinAraclari.editDistance("elma", "amel"));
        assertEquals(5, MetinAraclari.editDistance("elma", "frtyu"));
        // ************ TRANSPOZISYON *********************
        assertEquals(1, MetinAraclari.editDistance("elma", "emla"));
        assertEquals(1, MetinAraclari.editDistance("elma", "elam"));
        assertEquals(1, MetinAraclari.editDistance("elma", "lema"));
        assertEquals(1, MetinAraclari.editDistance("varil", "varli"));
        assertEquals(1, MetinAraclari.editDistance("varil", "vrail"));
        assertEquals(1, MetinAraclari.editDistance("varil", "vairl"));
        assertEquals(1, MetinAraclari.editDistance("varil", "avril"));
    }

    @Test
    public void testInModifiedLevenshteinDistance() {
        assertTrue(MetinAraclari.inEditDistance("elma", "elma", 1));
        assertTrue(MetinAraclari.inEditDistance("elma", "ekma", 1));
        assertTrue(MetinAraclari.inEditDistance("elma", "ema", 1));
        assertTrue(MetinAraclari.inEditDistance("elma", "elmas", 1));
        assertTrue(MetinAraclari.inEditDistance("elma", "lma", 1));
        assertTrue(MetinAraclari.inEditDistance("elma", "emas", 2));
        assertTrue(MetinAraclari.inEditDistance("elma", "el", 2));
        assertFalse(MetinAraclari.inEditDistance("elma", "el", 1));
        assertFalse(MetinAraclari.inEditDistance("elma", "eksa", 1));
        assertFalse(MetinAraclari.inEditDistance("armutu", "armutlr", 1));
        assertTrue(MetinAraclari.inEditDistance("armutlar", "armutlr", 1));
    }

    @Test
    public void testIsInSubStringEditDistance() {
        assertTrue(MetinAraclari.isInSubstringEditDistance("elma", "elma", 1));
        assertTrue(MetinAraclari.isInSubstringEditDistance("elma", "elmalar", 1));
        assertTrue(MetinAraclari.isInSubstringEditDistance("elma", "ekmalar", 1));
        assertTrue(MetinAraclari.isInSubstringEditDistance("elma", "emaciklar", 1));
        assertTrue(MetinAraclari.isInSubstringEditDistance("sefil", "sfil", 1));
    }

    @Test
    public void testJaroWinklerBenzerlik()
    {
        assertTrue(MetinAraclari.sozcukBenzerlikOrani("elma","elm")>0.9d);
        assertTrue(MetinAraclari.sozcukBenzerlikOrani("elma","elam")>0.9d);
        assertTrue(MetinAraclari.sozcukBenzerlikOrani("elma","elfa")>0.85d);
        assertTrue(MetinAraclari.sozcukBenzerlikOrani("elma","elmar")>0.9d);
    }

    @Test
    public void benzerlikPerformans() throws IOException {

        int count = 0;
        String s = YaziIsleyici.yaziOkuyucu("kaynaklar/metinler/commodore.txt");
        long start = System.currentTimeMillis();
        List<String> kelimeler = YaziIsleyici.kelimeAyikla(s);
        for (String kelime : kelimeler) {
            for (int j = 0; j < 100; j++) {
                String s2 = kelimeler.get(j);
                MetinAraclari.sozcukBenzerlikOrani(kelime, s2);
                count++;
            }
        }
        System.out.println("time:" + (System.currentTimeMillis()-start)
                + "  karsilastirma sayisi:" + count);
    }

}
