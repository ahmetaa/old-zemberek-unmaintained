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

package net.zemberek.yapi.ek;

import net.zemberek.TemelTest;
import net.zemberek.bilgi.KaynakYukleyici;
import net.zemberek.tr.yapi.ek.EkUreticiTr;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Fonksiyonel test. ek uretimlerinin dogrulugunu denetler.
 * User: ahmet
 * Date: Aug 23, 2005
 */
public class TestEkUreticiTr extends TemelTest {

@Test
    public void testUretici() throws IOException {
        EkUretici uretici = new EkUreticiTr(alfabe);

        BufferedReader reader = new KaynakYukleyici().getReader("kaynaklar/tr/test/ek_olusum.txt");
        String s;
        while ((s = reader.readLine()) != null) {
            if (s.startsWith("#") || s.trim().length() == 0) continue;
            String kuralKelimesi = s.substring(0, s.indexOf(':'));
            String olusumlar[] = s.substring(s.indexOf(':') + 1).trim().split(" ", -1);
            System.out.println("okunan kelime:" + kuralKelimesi + " olusumlar:" + Arrays.toString(olusumlar));
            olusumTesti(kuralKelimesi, olusumlar);
        }
        reader.close();
    }

    public void testKuralCozumleyici() {
         
    }

    private void olusumTesti(String kural, String[] olusumlar) {
/*        Set beklenen = new HashSet();
        for (String s : olusumlar)
            beklenen.add(new HarfDizisi(s, TurkceAlfabe.ref()));
        Set gelen = new HashSet(uretici.ekOlusumlariniUret(kural).values());
        assertEquals("beklenmedik ya da eksik olusum!", gelen, beklenen);*/
    }
}
