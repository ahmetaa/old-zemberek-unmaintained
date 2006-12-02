/*
 * Created on 15.Mar.2004
 */
package net.zemberek.araclar.turkce;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * @author MDA & GBA
 */
public class TestTurkishTokenStream {

    @Test
    public void testNextWord() {
        String input = "Ahmet Mehmet Betul Madeline";
        ArrayList list = new ArrayList();
        ByteArrayInputStream is = new ByteArrayInputStream(input.getBytes());
        TurkishTokenStream tstream;
        tstream = new TurkishTokenStream(is, null);
        String s = null;
        while ((s = tstream.nextWord()) != null) {
            list.add(s);
        }
        assertEquals(4, list.size());
        assertTrue(((String) list.get(0)).equals("ahmet"));
    }

    @Test
    public void testNextSentence() {
        ArrayList list = new ArrayList();
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
        ArrayList list = new ArrayList();
        TurkishTokenStream tstream;
        tstream = new TurkishTokenStream("kaynaklar/tr/test/tokentest.txt", null);
        String s = null;
        while ((s = tstream.nextWord()) != null) {
            list.add(s);
        }
        assertEquals(7, list.size());
        System.out.println("list = " + list);
        assertTrue("kelime" + list.get(0), list.get(6).equals(list.get(0)));
    }


}
