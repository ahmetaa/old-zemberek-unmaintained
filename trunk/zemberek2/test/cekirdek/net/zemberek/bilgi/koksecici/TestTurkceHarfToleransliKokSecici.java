/*
 *  ***** BEGIN LICENSE BLOCK *****
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
 *  The Original Code is Zemberek Doðal Dil Ýþleme Kütüphanesi.
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akýn, Mehmet D. Akýn.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  ***** END LICENSE BLOCK *****
 */

/*
 * Created on 30.Eyl.2004
 */
package net.zemberek.bilgi.koksecici;

import junit.framework.TestCase;

import java.io.IOException;

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
        System.out.println("Okuyucu Initialization süresi: " + TimeTracker.getElapsedTimeString("x"));
//        okuyucu.initialize("kaynaklar/test/tree_test_sozluk.txt");
        AgacSozluk sozluk = new AgacSozluk(okuyucu, TurkceAlfabe.ref(), TurkceKokOzelDurumlari.ref());
        System.out.println("Sözlük initialization süresi: " + TimeTracker.getElapsedTimeString("x"));
        KokBulucu bulucu = sozluk.getKokBulucuFactory().getAsciiKokBulucu();
        List list = null;
        list = bulucu.getAdayKokler("goruyor");
        System.out.println("Islem tamam: " + TimeTracker.getElapsedTimeString("x"));
        System.out.println("Tamamlanma süresi: " + TimeTracker.stopClock("x"));
        TestUtils.printList(list);*/
    }

    public void testPerf() {
//        TimeTracker.startClock("x");
//        KokOkuyucu okuyucu = new DuzMetinSozlukOkuyucu();
//        okuyucu.initialize("kaynaklar/tr/bilgi/duzyazi-kokler.txt");
//        System.out.println("Okuyucu Initialization süresi: " + TimeTracker.getElapsedTimeString("x"));
////        okuyucu.initialize("kaynaklar/test/tree_test_sozluk.txt");
//        AgacSozluk kokler = new AgacSozluk(okuyucu);
//        System.out.println("Sozluk Initialization süresi: " + TimeTracker.getElapsedTimeString("x"));
//        KokBulucu bulucu = kokler.getKokBulucuFactory().getAsciiKokBulucu();
//        List list  =null;
//        int testSayisi = 10000;
//        for(int i=0; i< testSayisi ; i++){
//        	list = bulucu.getAdayKokler("siraci");
//        }
//        System.out.println("Islem tamam: " + TimeTracker.getElapsedTimeString("x")  
//                + " Saniyede : " + TimeTracker.getItemsPerSecond("x",testSayisi) + " islem.");
//        System.out.println("Tamamlanma süresi: "  + TimeTracker.stopClock("x"));
//        //System.out.println("Walk Count : " + bulucu.getYurumeSayisi());
//        TestUtils.printList(list);
    }

}
