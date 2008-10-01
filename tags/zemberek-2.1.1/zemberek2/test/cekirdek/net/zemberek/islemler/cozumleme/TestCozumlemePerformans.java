/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler.cozumleme;

import java.io.IOException;
import java.util.List;

import net.zemberek.araclar.TimeTracker;
import net.zemberek.araclar.turkce.YaziIsleyici;

import org.junit.Test;
import org.junit.Ignore;

/**
 */
public class TestCozumlemePerformans extends TestKelimeCozumleyici {

    public void testPerformans() {
    }

    @Ignore("Performans")
    @Test
    public void testPerformans2() throws IOException {

        //List kelimeler = YaziIsleyici.analizIcinKelimeAyikla(YaziIsleyici.yaziOkuyucu("kaynaklar/metinler/Ahmet_Hamdi_Tanpinar_Huzur.txt"));
        //List kelimeler = YaziIsleyici.analizIcinKelimeAyikla(YaziIsleyici.yaziOkuyucu("kaynaklar/metinler/Oscar_Wilde_Oykuler1.txt"));
        //List kelimeler = YaziIsleyici.analizIcinKelimeAyikla(YaziIsleyici.yaziOkuyucu("kaynaklar/metinler/Frank_Herbert_Dune1.txt"));
        List<String> kelimeler = YaziIsleyici.analizIcinKelimeAyikla(YaziIsleyici.yaziOkuyucu("kaynaklar/tr/metinler/buzyeli_vadisi_2.txt"));
        //List kelimeler = YaziIsleyici.analizIcinKelimeAyikla(YaziIsleyici.yaziOkuyucu("kaynaklar/tr/metinler/commodore.txt"));
        //List kelimeler = YaziIsleyici.kelimeAyikla(YaziIsleyici.yaziOkuyucu("kaynaklar/metinler/corpus.txt"));
        //int vuru = 0;
        //IstatistikXMLIsleyici ist = new IstatistikXMLIsleyici();
        //ist.xmlDosyaCozumle("kaynaklar/kb/stats.jar");
        //HashMap denetlemeCebi = ist.getKelimeCebi();
        //System.out.println("Cep Boyu: " + denetlemeCebi.size());
        for(int j=0; j<3; j++) {
        TimeTracker.startClock("x");
        int dogrular = 0, yanlislar = 0;
            long t1 = System.nanoTime();
        for (int i = 0; i < kelimeler.size(); i++) {

            String kelime = kelimeler.get(i);
/*            if(denetlemeCebi.containsKey(kelime))
            {
               vuru++;
               dogrular++;
            } else*/

            if(cozumleyici.cozumlenebilir(kelime)) {
                  //System.out.println(Arrays.toString(heceIslemleri.hecele(kelime)));
                  dogrular++;
            } else {
                //System.out.println(kelime);
                yanlislar++;
            }
        }
            long time = (System.nanoTime()-t1)/1000000L;

            System.out.println("elp:"+time);
            
        System.out.println(kelimeler.size() + " kelimelik metin icin Saniyede : " + TimeTracker.getItemsPerSecond("x", kelimeler.size()));
        System.out.println("Dogru kelime sayisi: " + dogrular + " Yanlis Kelime Sayisi: " + yanlislar);
        System.out.println("Toplam sure: " + TimeTracker.stopClock("x"));
        }
    }
}
