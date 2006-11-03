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
