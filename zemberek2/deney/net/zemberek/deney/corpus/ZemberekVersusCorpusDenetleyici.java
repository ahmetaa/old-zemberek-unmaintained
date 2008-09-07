package net.zemberek.deney.corpus;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;

import java.util.Collection;
import java.io.IOException;

public class ZemberekVersusCorpusDenetleyici {

    BasitCorpus corp = new BasitCorpus("corpus/corpus.txt");
    Zemberek zemberek = new Zemberek(new TurkiyeTurkcesi());
    int zemberekHit, zemberekMiss, corpusHit, corpusMiss;

    public void kiyaslamaRaporu(String... files) throws IOException {
        for (String file : files) {
            Collection<String> kelimeler = BasitCorpus.kelimeleriOku(file);
            for (String s : kelimeler) {
                if (zemberek.kelimeDenetle(s)) {
                    zemberekHit++;
                } else {
                    zemberekMiss++;
                }                
                if (corp.denetle(s))
                    corpusHit++;
                else corpusMiss++;
            }
            System.out.println("-------------------------------------");
            System.out.println("Dosya:" + file);
            System.out.println("Zemberek dogru:" + zemberekHit);
            System.out.println("Zemberek yanlis:" + zemberekMiss);
            System.out.println("Corpus dogru:" + corpusHit);
            System.out.println("Corpus yanlis:" + corpusMiss);
        }
    }


    public static void main(String[] args) throws IOException {
        new ZemberekVersusCorpusDenetleyici().kiyaslamaRaporu("corpus/kaynaklar/test/tolstoy-dirilis-utf8.txt");
        new ZemberekVersusCorpusDenetleyici().kiyaslamaRaporu("corpus/kaynaklar/test/efendi-ile-usagi.txt");
        new ZemberekVersusCorpusDenetleyici().kiyaslamaRaporu("corpus/kaynaklar/test/bu-iste-bir-yalnizlik-var.txt");
    }


}
