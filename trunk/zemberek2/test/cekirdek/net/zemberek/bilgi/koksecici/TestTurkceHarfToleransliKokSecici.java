/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

/*
 * Created on 30.Eyl.2004
 */
package net.zemberek.bilgi.koksecici;

import java.io.IOException;

import junit.framework.TestCase;

/**
 * @author MDA & GBA
 */
public class TestTurkceHarfToleransliKokSecici extends TestCase {

    public void testMore() throws IOException {
/*        TimeTracker.startClock("x");
        KokOkuyucu okuyucu = new DuzYaziKokOkuyucu(
                "kaynaklar/tr/bilgi/duzyazi-kilavuz.txt",
                TurkceKokOzelDurumlari.ref(),
                TurkceAlfabe.ref(),
                KokTipiAdlari.getKokTipAdlari());
        System.out.println("Okuyucu Initialization s�resi: " + TimeTracker.getElapsedTimeString("x"));
//        okuyucu.initialize("kaynaklar/test/tree_test_sozluk.txt");
        AgacSozluk sozluk = new AgacSozluk(okuyucu, TurkceAlfabe.ref(), TurkceKokOzelDurumlari.ref());
        System.out.println("S�zl�k initialization s�resi: " + TimeTracker.getElapsedTimeString("x"));
        KokAdayiBulucu bulucu = sozluk.getKokBulucuFactory().getAsciiKokBulucu();
        List list = null;
        list = bulucu.getAdayKokler("goruyor");
        System.out.println("Islem tamam: " + TimeTracker.getElapsedTimeString("x"));
        System.out.println("Tamamlanma s�resi: " + TimeTracker.stopClock("x"));
        TestUtils.printList(list);*/
    }

    public void testPerf() {
//        TimeTracker.startClock("x");
//        KokOkuyucu okuyucu = new DuzMetinSozlukOkuyucu();
//        okuyucu.initialize("kaynaklar/tr/bilgi/duzyazi-kokler.txt");
//        System.out.println("Okuyucu Initialization s�resi: " + TimeTracker.getElapsedTimeString("x"));
////        okuyucu.initialize("kaynaklar/test/tree_test_sozluk.txt");
//        AgacSozluk kokler = new AgacSozluk(okuyucu);
//        System.out.println("Sozluk Initialization s�resi: " + TimeTracker.getElapsedTimeString("x"));
//        KokAdayiBulucu bulucu = kokler.getKokBulucuFactory().getAsciiKokBulucu();
//        List list  =null;
//        int testSayisi = 10000;
//        for(int i=0; i< testSayisi ; i++){
//        	list = bulucu.getAdayKokler("siraci");
//        }
//        System.out.println("Islem tamam: " + TimeTracker.getElapsedTimeString("x")  
//                + " Saniyede : " + TimeTracker.getItemsPerSecond("x",testSayisi) + " islem.");
//        System.out.println("Tamamlanma s�resi: "  + TimeTracker.stopClock("x"));
//        //System.out.println("Walk Count : " + bulucu.getYurumeSayisi());
//        TestUtils.printList(list);
    }

}
