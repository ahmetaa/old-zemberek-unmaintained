/*
 * Created on 05.Eyl.2004
 */
package net.zemberek.bilgi.koksecici;

import net.zemberek.bilgi.araclar.KokOkuyucu;
import net.zemberek.bilgi.kokler.KokBulucu;

import java.io.IOException;

/**
 * @author MDA & GBA
 */
public class TestHataToleransliKokSecici {

    static KokOkuyucu okuyucu;
    static KokBulucu bulucu = null;

//    public void once()
//    {
//        okuyucu.initialize("kaynaklar/test/test-kokler.txt");
//        bulucu.initialize(new MapSozluk(okuyucu));
//    }

//    public void testToleransliKokSecici() throws IOException
//    {
//        parse();
//        for (int i = 0; i < testKumesi.size(); i++)
//        {
//            Uye uye = (Uye) testKumesi.get(i);
//            System.out.println("giris:" + uye.giris);
//            List sonuclar = bulucu.getAdayKokler(uye.giris);
//            assertTrue("Aday sayisi hatali:" + sonuclar.size(), sonuclar.size() == uye.sonuclar.size());
//            for (int j = 0; j < sonuclar.size(); j++)
//            {
//                System.out.println("Gelenler:");
//                printList(sonuclar);
//                System.out.println("Beklenenler:");
//                printList(uye.sonuclar);
//                Kok kok = (Kok) sonuclar.get(j);
//                System.out.println("Sonuc " + j + " = " + kok.icerik());
//                assertTrue("Hatali aday:" + kok.icerik(),
//                        uye.sonuclar.contains(kok.icerik()));
//            }
//        }
//    }

    public void testMore() throws IOException {
/*        TimeTracker.startClock("x");
        okuyucu = new DuzYaziKokOkuyucu(
                "kaynaklar/tr/bilgi/duzyazi-kokler.txt",
                TurkceKokOzelDurumlari.ref(),
                TurkceAlfabe.ref(),
                KokTipiAdlari.getKokTipAdlari());
//        okuyucu.initialize("kaynaklar/test/tree_test_sozluk.txt");
        System.out.println("Okuyucu Initialization süresi: " + TimeTracker.getElapsedTimeString("x"));
        AgacSozluk sozluk = new AgacSozluk(okuyucu, TurkceAlfabe.ref(), TurkceKokOzelDurumlari.ref());
        System.out.println("Sozluk Initialization süresi: " + TimeTracker.getElapsedTimeString("x"));
        bulucu = sozluk.getKokBulucuFactory().getToleransliKokBulucu();
        List list = bulucu.getAdayKokler("ixtersen");
        //System.out.println("Walk Count : " + bulucu.getYurumeSayisi());
        //System.out.println("Distance Calculation Count : " + bulucu.getDistanceCalculationCount());
        System.out.println("Tamamlanma süresi: " + TimeTracker.stopClock("x"));
        TestUtils.printList(list);
        ToleransliCozumleyici cozumleyici =
                new ToleransliCozumleyici(bulucu, TurkceEkYonetici.ref(), TurkceAlfabe.ref(), new TurkceCozumlemeYardimcisi(TurkceAlfabe.ref(), null));
        cozumleyici.cozumle("tes");*/
    }

}

