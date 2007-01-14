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

package net.zemberek.islemler.cozumleme;

import net.zemberek.araclar.TimeTracker;
import net.zemberek.araclar.turkce.YaziIsleyici;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

/**
 */
public class TestCozumlemePerformans extends TestKelimeCozumleyici {

    public void testPerformans() {
    }

    //@Ignore("Performans")
    @Test
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
        for(int j=0; j<3; j++) {
        TimeTracker.startClock("x");
        int dogrular = 0, yanlislar = 0;
            long t1 = System.nanoTime();
        for (int i = 0; i < kelimeler.size(); i++) {

            String kelime = (String) kelimeler.get(i);
/*            if(cep.containsKey(kelime))
            {
               vuru++;
               dogrular++;
            } else*/

            if(cozumleyici.denetle(kelime)) {
                  //System.out.println(Arrays.toString(heceIslemleri.hecele(kelime)));
                  dogrular++;
            } else {
                //System.out.println(kelime);
                yanlislar++;
            }
        }
            long time = (System.nanoTime()-t1)/1000000L;

            System.out.println("elp:"+time);
            System.out.println("blah="+(1000*kelimeler.size())/time);
            
        System.out.println(kelimeler.size() + " kelimelik metin icin Saniyede : " + TimeTracker.getItemsPerSecond("x", kelimeler.size()));
        System.out.println("Dogru kelime sayisi: " + dogrular + " Yanlis Kelime Sayisi: " + yanlislar);
        System.out.println("Toplam sure: " + TimeTracker.stopClock("x"));
        }
    }
}
