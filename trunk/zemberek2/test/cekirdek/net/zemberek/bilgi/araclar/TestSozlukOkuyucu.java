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
 * Created on 10.Mar.2004
 */
package net.zemberek.bilgi.araclar;

import junit.framework.TestCase;

/**
 * @author MDA & GBA
 */
public class TestSozlukOkuyucu extends TestCase {
/*    SozlukAraclari sozlukaraclari = new SozlukAraclari();

    public void testOku()
    {
        TimeTracker.startClock("s1");
        EskiTspellSozlukOkuyucu sozlukOkuyucu = new EskiTspellSozlukOkuyucu("kaynaklar/kb/kilavuz.txt");
        sozlukOkuyucu.oku();
        System.out.println(TimeTracker.getElapsedTimeString("s1"));
        System.out.println(TimeTracker.stopClock("s1"));
    }

    public void testDogruluk()
    {
        KokOkuyucu sozlukOkuyucu = new DuzMetinSozlukOkuyucu();
        sozlukOkuyucu.initialize("kaynaklar/test/test-kokler.txt");
        List list = sozlukOkuyucu.oku();
        assertNotNull(list);
        assertEquals(5, list.size());

        for (int i = 0; i < list.size(); i++)
        {
            System.out.println(i + ". " + ((Kok) list.get(i)).icerik());
        }

        assertFalse(inList(list, "KabulEtme"));
        assertTrue(inList(list, "aba"));
        assertTrue(inList(list, "acemborusu"));
        assertTrue(inList(list, "gel"));
        assertTrue(inList(list, "abart"));
        assertTrue(inList(list, "acele"));

        assertEquals(KelimeTipi.ISIM, getElement(list, "aba").tip());
        assertEquals(KelimeTipi.FIIL, getElement(list, "gel").tip());
        assertEquals(KelimeTipi.FIIL, getElement(list, "abart").tip());
        assertEquals(KelimeTipi.SIFAT, getElement(list, "acele").tip());
        Kok kok;
        kok = getElement(list, "acemborusu");
        assertTrue(kok.getOzellikler().isEkVar());
        assertTrue(kok.sonEk() == Ekler.ISIM_HAL_I);
        kok = getElement(list, "abart");
        assertTrue(kok.getOzellikler().isGecissiz());
        assertTrue(kok.getOzellikler().isFarkliGenisZaman());
    }

    private boolean inList(List list, String str)
    {
        for (int i = 0; i < list.size(); i++)
        {
            if (((Kok) list.get(i)).icerik().equals(str)) return true;
        }
        return false;
    }

    private Kok getElement(List list, String str)
    {
        for (int i = 0; i < list.size(); i++)
        {
            Kok kok = (Kok) list.get(i);
            if (kok.icerik().equals(str)) return kok;
        }
        return null;
    }*/


}
