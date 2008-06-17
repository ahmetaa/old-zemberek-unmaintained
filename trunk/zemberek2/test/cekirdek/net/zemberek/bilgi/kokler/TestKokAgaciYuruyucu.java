/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.bilgi.kokler;

import net.zemberek.TemelTest;
import net.zemberek.araclar.IstatistikAraclari;
import net.zemberek.araclar.TimeTracker;

import org.junit.Test;

/**
 * User: ahmet
 * Date: Sep 7, 2005
 */
public class TestKokAgaciYuruyucu extends TemelTest {

  @Test
  public void testYuruyucu()
  {
	  AgacSozluk sozluk = (AgacSozluk)dilBilgisi.kokler();
      KokAgaciYuruyucu yuruyucu = new KokAgaciYuruyucu(sozluk , null);
        TimeTracker.startClock("x");
        System.out.println("Sozluk Yuklendi: " + TimeTracker.getElapsedTimeString("x"));
        yuruyucu.yuru(sozluk.getAgac().getKokDugumu(), "");
        System.out.println("Agacta Yurundu: " + TimeTracker.getElapsedTimeString("x"));
        System.out.println("Toplam dugum sayisi:" + yuruyucu.dugumSayisi);
        System.out.println("Kok tasiyan dugum sayisi:" + yuruyucu.kokTasiyanDugumSayisi);
        System.out.println("Es Sesli Tasiyan dugum sayisi:" + yuruyucu.esSesliTasiyanDugumSayisi);
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
