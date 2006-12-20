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
 *  The Original Code is Zemberek Doğal Dil İşleme Kütüphanesi.
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akın, Mehmet D. Akın.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.bilgi.kokler;

import net.zemberek.TemelTest;
import net.zemberek.araclar.IstatistikAraclari;
import net.zemberek.araclar.TimeTracker;

/**
 * User: ahmet
 * Date: Sep 7, 2005
 */
public class TestKokAgaciYuruyucu extends TemelTest {


  public void testYuruyucu()
  {
      KokAgaciYuruyucu yuruyucu = new KokAgaciYuruyucu(dilBilgisi.kokler(), null);

        TimeTracker.startClock("x");
        System.out.println("Sozluk Yuklendi: " + TimeTracker.getElapsedTimeString("x"));
        yuruyucu.walk(dilBilgisi.kokler().getAgac().getKokDugumu(), "");
        System.out.println("Agacta Yurundu: " + TimeTracker.getElapsedTimeString("x"));
        System.out.println("Toplam d�g�m sayisi:" + yuruyucu.dugumSayisi);
        System.out.println("Kok tasiyan dugum sayisi:" + yuruyucu.kokTasiyanDugumSayisi);
        System.out.println("Es Sesli Tasiyan d�g�m sayisi:" + yuruyucu.esSesliTasiyanDugumSayisi);
        System.out.println("Alt dugumu olan dugum sayisi:" + (yuruyucu.dugumSayisi - yuruyucu.ucDugumSayisi));
        System.out.println("Alt dugumu olmayan dugum sayisi:" + yuruyucu.ucDugumSayisi);
        int araToplam = 0;
        for(int i=1; i<29; i++){
            araToplam += yuruyucu.dugumSayilari[i];
            System.out.println(i + " alt dugumu olanlar:" + yuruyucu.dugumSayilari[i]
                                  + " Ara Toplam: " + araToplam + " Yuzdesi: %" +
                    IstatistikAraclari.yuzdeHesapla(araToplam, (yuruyucu.dugumSayisi - yuruyucu.ucDugumSayisi)));

        }
        TimeTracker.stopClock("x");
        Runtime.getRuntime().gc();
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
  }
}
