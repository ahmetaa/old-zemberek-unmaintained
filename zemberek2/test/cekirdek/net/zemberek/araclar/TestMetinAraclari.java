/*
 *  ***** BEGIN LICENSE BLOCK *****
 *
 *  Version: MPL 1.1
 *
 *  The contents of this file are subject to the Mozilla Public License Version
 *  1.1 (the "License"); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an "AS IS" basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 *  for the specific language governing rights and limitations under the
 *  License.
 *
 *  The Original Code is "Zemberek Dogal Dil Isleme Kutuphanesi"
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akin, Mehmet D. Akin.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

/*
 * Created on 13.Mar.2004
 */
package net.zemberek.araclar;

import net.zemberek.araclar.turkce.YaziIsleyici;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Ignore;

import java.io.IOException;
import java.util.List;

/**
 * @author MDA
 */
public class TestMetinAraclari {


    @Test
    public void testEditDistance() {
        assertEquals(0, MetinAraclari.duzeltmeMesafesi("elma", "elma"));
        assertEquals(1, MetinAraclari.duzeltmeMesafesi("elma", "elmax"));
        assertEquals(1, MetinAraclari.duzeltmeMesafesi("elma", "lma"));
        assertEquals(2, MetinAraclari.duzeltmeMesafesi("elma", "ma"));
        assertEquals(2, MetinAraclari.duzeltmeMesafesi("elma", "frma"));
        assertEquals(3, MetinAraclari.duzeltmeMesafesi("elma", "a"));
        assertEquals(3, MetinAraclari.duzeltmeMesafesi("elma", "elmalar"));
        assertEquals(4, MetinAraclari.duzeltmeMesafesi("elma", "elmalara"));
        assertEquals(4, MetinAraclari.duzeltmeMesafesi("elma", "amel"));
        assertEquals(5, MetinAraclari.duzeltmeMesafesi("elma", "frtyu"));
        // ************ TRANSPOZISYON *********************
        assertEquals(1, MetinAraclari.duzeltmeMesafesi("elma", "emla"));
        assertEquals(1, MetinAraclari.duzeltmeMesafesi("elma", "elam"));
        assertEquals(1, MetinAraclari.duzeltmeMesafesi("elma", "lema"));
        assertEquals(1, MetinAraclari.duzeltmeMesafesi("varil", "varli"));
        assertEquals(1, MetinAraclari.duzeltmeMesafesi("varil", "vrail"));
        assertEquals(1, MetinAraclari.duzeltmeMesafesi("varil", "vairl"));
        assertEquals(1, MetinAraclari.duzeltmeMesafesi("varil", "avril"));
    }

    @Test
    public void testInModifiedLevenshteinDistance() {
        assertTrue(MetinAraclari.duzeltmeMesafesiIcinde("elma", "elma", 1));
        assertTrue(MetinAraclari.duzeltmeMesafesiIcinde("elma", "ekma", 1));
        assertTrue(MetinAraclari.duzeltmeMesafesiIcinde("elma", "ema", 1));
        assertTrue(MetinAraclari.duzeltmeMesafesiIcinde("elma", "elmas", 1));
        assertTrue(MetinAraclari.duzeltmeMesafesiIcinde("elma", "lma", 1));
        assertTrue(MetinAraclari.duzeltmeMesafesiIcinde("elma", "emas", 2));
        assertTrue(MetinAraclari.duzeltmeMesafesiIcinde("elma", "el", 2));
        assertFalse(MetinAraclari.duzeltmeMesafesiIcinde("elma", "el", 1));
        assertFalse(MetinAraclari.duzeltmeMesafesiIcinde("elma", "eksa", 1));
        assertFalse(MetinAraclari.duzeltmeMesafesiIcinde("armutu", "armutlr", 1));
        assertTrue(MetinAraclari.duzeltmeMesafesiIcinde("armutlar", "armutlr", 1));
    }

    @Test
    public void testIsInSubStringEditDistance() {
        assertTrue(MetinAraclari.parcasiDuzeltmeMesafesiIcinde("elma", "elma", 1));
        assertTrue(MetinAraclari.parcasiDuzeltmeMesafesiIcinde("elma", "elmalar", 1));
        assertTrue(MetinAraclari.parcasiDuzeltmeMesafesiIcinde("elma", "ekmalar", 1));
        assertTrue(MetinAraclari.parcasiDuzeltmeMesafesiIcinde("elma", "emaciklar", 1));
        assertTrue(MetinAraclari.parcasiDuzeltmeMesafesiIcinde("sefil", "sfil", 1));
    }

    @Test
    public void testJaroWinklerBenzerlik()
    {
        assertTrue(MetinAraclari.sozcukBenzerlikOrani("elma","elm")>0.9d);
        assertTrue(MetinAraclari.sozcukBenzerlikOrani("elma","elam")>0.9d);
        assertTrue(MetinAraclari.sozcukBenzerlikOrani("elma","elfa")>0.85d);
        assertTrue(MetinAraclari.sozcukBenzerlikOrani("elma","elmar")>0.9d);
    }



    @Ignore("Performans")
    @Test
    public void benzerlikPerformans() throws IOException {

        int count = 0;
        String s = YaziIsleyici.yaziOkuyucu("kaynaklar/tr/metinler/commodore.txt");
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
