package net.zemberek.araclar.turkce;

import net.zemberek.araclar.TimeTracker;

/**
 */
public class TestTurkishTokenStreamPerf {

    public void testNextWordPerf() {
        TimeTracker.startClock("x");
        TurkishTokenStream tstream = new TurkishTokenStream("kaynaklar/tr/metinler/Frank_Herbert_Dune1.txt", null);
        String s = null;
        int wordCount = 0;
        while ((s = tstream.nextWord()) != null) {
            wordCount++;
        }
        System.out.println("Kelime sayisi: " + wordCount);
        System.out.println("Toplam islem suresi:" + TimeTracker.stopClock("x"));

    }


    public void testNextSentencePerf() {
        TimeTracker.startClock("x");
        TurkishTokenStream tstream = new TurkishTokenStream("kaynaklar/tr/metinler/Frank_Herbert_Dune1.txt", null);
        String s = null;
        int sentenceCount = 0;
        while ((s = tstream.nextSentence()) != null) {
            sentenceCount++;
        }
        System.out.println("Cumle sayisi: " + sentenceCount);
        System.out.println("Toplam islem suresi:" + TimeTracker.stopClock("x"));
    }
}
