/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

/*
 * Created on 15.Mar.2004
 */
package net.zemberek.araclar.turkce;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author MDA & GBA
 */
public class TestTurkishTokenStream {

    //@Test
    @Ignore
    public void testNextWord() {
        String input = "Ahmet Mehmet Betul";
        ArrayList<String> list = new ArrayList<String>();
        ByteArrayInputStream is = new ByteArrayInputStream(input.getBytes());
        TurkishTokenStream tstream;
        tstream = new TurkishTokenStream(is, "UTF-8");
        String s = null;
        while ((s = tstream.nextWord()) != null) {
            list.add(s);
        }
        assertEquals(3, list.size());
        assertTrue(list.get(0).equals("Ahmet"));
    }

    @Test
    public void testNextSentence() {
        ArrayList<String> list = new ArrayList<String>();
        TurkishTokenStream tstream;
        tstream = new TurkishTokenStream("kaynaklar/tr/test/tokensentencetest.txt", null);
        String s = null;
        while ((s = tstream.nextSentence()) != null) {
            list.add(s);
        }
        assertEquals(5, list.size());
        System.out.println("list = " + list);
    }

    @Test
    public void testNextWordKomplex() {
        ArrayList<String> list = new ArrayList<String>();
        TurkishTokenStream tstream;
        tstream = new TurkishTokenStream("kaynaklar/tr/test/tokentest.txt", null);
        String s = null;
        while ((s = tstream.nextWord()) != null) {
            list.add(s);
        }
        assertEquals(7, list.size());
        System.out.println("list = " + list);
        assertTrue("kelime" + list.get(0), list.get(6).equalsIgnoreCase(list.get(0)));
    }


}
