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

/*
 * Created on 05.Eyl.2004
 */
package net.zemberek.bilgi.koksecici;

import net.zemberek.bilgi.araclar.KokOkuyucu;
import net.zemberek.bilgi.kokler.KokAdayiBulucu;

import java.io.IOException;

/**
 * @author MDA & GBA
 */
public class TestHataToleransliKokSecici {

    static KokOkuyucu okuyucu;
    static KokAdayiBulucu bulucu = null;

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

