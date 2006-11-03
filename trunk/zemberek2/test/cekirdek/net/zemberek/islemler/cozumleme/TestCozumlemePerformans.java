package net.zemberek.islemler.cozumleme;

import net.zemberek.araclar.IstatistikAraclari;
import net.zemberek.araclar.TimeTracker;
import net.zemberek.araclar.turkce.TurkceMetinOkuyucu;
import net.zemberek.araclar.turkce.YaziIsleyici;
import net.zemberek.istatistik.IstatistikXMLIsleyici;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 */
public class TestCozumlemePerformans extends TestKelimeCozumleyici {

    public void testPerformans() {
        int vuru = 0;
        IstatistikXMLIsleyici ist = new IstatistikXMLIsleyici(dilBilgisi.ekler());
        //ist.xmlDosyaCozumle("kaynaklar/kb/stats.jar");
        HashMap cep = ist.getKelimeCebi();
        System.out.println("Cep Boyu: " + cep.size());
        TurkceMetinOkuyucu tmo = new TurkceMetinOkuyucu();
        //String[] buyukMetin = tmo.MetinOku("kaynaklar/metinler/Frank_Herbert_Dune1.txt");
        String[] buyukMetin = tmo.MetinOku("kaynaklar/tr/metinler/Ahmet_Hamdi_Tanpinar_Huzur.txt");
        TimeTracker.startClock("x");
        int dogrular = 0, yanlislar = 0;
        for (int i = 0; i < buyukMetin.length; i++) {
            if (cep.get(buyukMetin[i]) != null) {
                dogrular++;
                vuru++;
                continue;
            }
            if (cozumleyici.denetle(buyukMetin[i])) {
                dogrular++;
            } else {
                //System.out.println(buyukMetin[i]);
                yanlislar++;
            }
        }
        System.out.println(buyukMetin.length + " kelimelik metin için Saniyede : " + TimeTracker.getItemsPerSecond("x", buyukMetin.length));
        System.out.println("Dogru kelime sayisi: " + dogrular + " Yanlis Kelime Sayisi: " + yanlislar);
        System.out.println("Toplam süre: " + TimeTracker.stopClock("x"));
        System.out.println("Vuru sayýsý: " + vuru + " Vuru oraný: " + IstatistikAraclari.yuzdeHesapla(vuru, dogrular));
    }

    public void testPerformans2() throws IOException {
        //List kelimeler = YaziIsleyici.analizIcinKelimeAyikla(YaziIsleyici.yaziOkuyucu("kaynaklar/metinler/Ahmet_Hamdi_Tanpinar_Huzur.txt"));
        //List kelimeler = YaziIsleyici.analizIcinKelimeAyikla(YaziIsleyici.yaziOkuyucu("kaynaklar/metinler/Oscar_Wilde_Oykuler1.txt"));
        //List kelimeler = YaziIsleyici.analizIcinKelimeAyikla(YaziIsleyici.yaziOkuyucu("kaynaklar/metinler/Frank_Herbert_Dune1.txt"));
        List kelimeler = YaziIsleyici.analizIcinKelimeAyikla(YaziIsleyici.yaziOkuyucu("kaynaklar/tr/metinler/BuzyeliVadisi2.txt"));
        //List kelimeler = YaziIsleyici.analizIcinKelimeAyikla(YaziIsleyici.yaziOkuyucu("kaynaklar/metinler/commodore.txt"));
        //List kelimeler = YaziIsleyici.kelimeAyikla(YaziIsleyici.yaziOkuyucu("kaynaklar/metinler/corpus.txt"));
        int vuru = 0;
        //IstatistikXMLIsleyici ist = new IstatistikXMLIsleyici();
        //ist.xmlDosyaCozumle("kaynaklar/kb/stats.jar");
        //HashMap cep = ist.getKelimeCebi();
        //System.out.println("Cep Boyu: " + cep.size());
        for(int j=0; j<10; j++) {
        TimeTracker.startClock("x");
        int dogrular = 0, yanlislar = 0;
        for (int i = 0; i < kelimeler.size(); i++) {
            String kelime = (String) kelimeler.get(i);
/*            if(cep.containsKey(kelime))
            {
               vuru++;
               dogrular++;
            } else*/
            if (cozumleyici.denetle(kelime)) {
                dogrular++;
            } else {
                //System.out.println(kelimeler.get(i));
                yanlislar++;
            }
        }
        System.out.println(kelimeler.size() + " kelimelik metin için Saniyede : " + TimeTracker.getItemsPerSecond("x", kelimeler.size()));
        System.out.println("Dogru kelime sayisi: " + dogrular + " Yanlis Kelime Sayisi: " + yanlislar);
        System.out.println("Toplam süre: " + TimeTracker.stopClock("x"));
        System.out.println("Vuru sayýsý: " + vuru + " Vuru oraný: " + IstatistikAraclari.yuzdeHesapla(vuru, dogrular));
        }
    }
}
