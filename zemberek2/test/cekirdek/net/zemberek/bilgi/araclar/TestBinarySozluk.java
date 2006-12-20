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
 * Created on 13.?ub.2005
 *
 */
package net.zemberek.bilgi.araclar;

import junit.framework.TestCase;

import java.io.IOException;

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
        System.out.println("Count: " + cnt + "  Toplam süre: " + TimeTracker.stopClock("x"));
        //TestUtils.assertCollectionsEqual(list, list2);*/
    }
}