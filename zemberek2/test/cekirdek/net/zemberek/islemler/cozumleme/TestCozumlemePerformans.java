package net.zemberek.islemler.cozumleme;

import java.io.IOException;
import java.util.List;

import net.zemberek.araclar.IstatistikAraclari;
import net.zemberek.araclar.TimeTracker;
import net.zemberek.araclar.turkce.YaziIsleyici;

/**
 */
public class TestCozumlemePerformans extends TestKelimeCozumleyici {

    public void testPerformans() {
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
        for(int j=0; j<6; j++) {
        TimeTracker.startClock("x");
        int dogrular = 0, yanlislar = 0;
        for (int i = 0; i < kelimeler.size(); i++) {
            String kelime = (String) kelimeler.get(i);
/*            if(cep.containsKey(kelime))
            {
               vuru++;
               dogrular++;
            } else*/
            if (heceleyici.hecelenebilirmi(kelime)) {
               // System.out.println(Arrays.toString(heceleyici.hecele(kelime)));
                dogrular++;
            } else {
                //System.out.println(kelimeler.get(i));
                yanlislar++;
            }
        }
        System.out.println(kelimeler.size() + " kelimelik metin i�in Saniyede : " + TimeTracker.getItemsPerSecond("x", kelimeler.size()));
        System.out.println("Dogru kelime sayisi: " + dogrular + " Yanlis Kelime Sayisi: " + yanlislar);
        System.out.println("Toplam s�re: " + TimeTracker.stopClock("x"));
        System.out.println("Vuru say�s�: " + vuru + " Vuru oran�: " + IstatistikAraclari.yuzdeHesapla(vuru, dogrular));
        }
    }
}
