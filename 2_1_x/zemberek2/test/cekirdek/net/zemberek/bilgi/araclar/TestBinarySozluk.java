/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

/*
 * Created on 13.?ub.2005
 *
 */
package net.zemberek.bilgi.araclar;

import java.io.IOException;

import junit.framework.TestCase;

/**
 * @author MDA
 */
public class TestBinarySozluk extends TestCase {

    public void testYazici() throws IOException {
 /*       TimeTracker.startClock("x");
        KokOkuyucu kokOkuyucu = new DuzYaziKokOkuyucu(
                "kaynaklar/tr/bilgi/duzyazi-kilavuz.txt",
                TurkceKokOzelDurumlari.ref(),
                TurkceAlfabe.ref(),
                KokTipiAdlari.getKokTipAdlari());
        IkiliKokYazici yazici = new IkiliKokYazici("test.bin");
        List list = kokOkuyucu.hepsiniOku();
        System.out.println(TimeTracker.getElapsedTimeString("x"));
        yazici.yaz(list);
        System.out.println(TimeTracker.getElapsedTimeString("x"));
        IkiliKokOkuyucu binaryOkuyucu = new IkiliKokOkuyucu("test.bin",TurkceKokOzelDurumlari.ref());
        //List list2 = binaryOkuyucu.hepsiniOku();
        Kok kok = null;
        int cnt = 0;
        AgacSozluk sozluk = new AgacSozluk(binaryOkuyucu, TurkceAlfabe.ref(), TurkceKokOzelDurumlari.ref());
//        while((kok = binaryOkuyucu.oku())!= null){
//            cnt++;
//        }
        System.out.println(TimeTracker.getElapsedTimeString("x"));
        System.out.println("Count: " + cnt + "  Toplam s�re: " + TimeTracker.stopClock("x"));
        //TestUtils.assertCollectionsEqual(list, list2);*/
    }
}