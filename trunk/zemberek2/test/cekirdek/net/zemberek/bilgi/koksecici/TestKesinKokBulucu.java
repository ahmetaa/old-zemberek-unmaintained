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
 * Created on 18.Eki.2004
 */
package net.zemberek.bilgi.koksecici;

import net.zemberek.TemelTest;
import net.zemberek.TestUtils;
import net.zemberek.araclar.TimeTracker;
import net.zemberek.bilgi.araclar.DuzYaziKokOkuyucu;
import net.zemberek.bilgi.araclar.IkiliKokOkuyucu;
import net.zemberek.bilgi.araclar.KokOkuyucu;
import net.zemberek.bilgi.kokler.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import static java.lang.System.*;

/**
 * @author MDA & GBA
 */
public class TestKesinKokBulucu extends TemelTest {
    AgacSozluk sozluk = null;
    KokAdayiBulucu bulucu;
    String[] kelimeler;
    KokOkuyucu okuyucu;

    @Before
    public void once() throws IOException {
        super.once();
        sozluk = sozlukUret("kaynaklar/tr/test/test-sozluk.txt");
    }

    @Ignore("Bilgi gosterimi amacli test.")
    @Test
    public void testWordTreeKokSecici() {
        bulucu = new KesinKokAdayiBulucu(sozluk.getAgac());
        out.println("Agac:" + sozluk.getAgac().getKokDugumu().goster(2));
        List list = bulucu.adayKokleriBul("karalar");
        TestUtils.printList(list);
        out.println("Tamamlanma s�resi: " + TimeTracker.stopClock("x"));
    }

    @Test
    public void testToleransliKokBulBasit() {
        bulucu = new ToleransliKokAdayiBulucu(sozluk.getAgac(), 1);
        out.println("Agac:" + sozluk.getAgac().getKokDugumu().goster(2));
        List list = bulucu.adayKokleriBul("deniz");
        TestUtils.printList(list);
        out.println("Tamamlanma s�resi: " + TimeTracker.stopClock("x"));
    }

    @Test
    public void testKokSeciciTumSozluk() throws IOException {
        okuyucu = new IkiliKokOkuyucu("kaynaklar/tr/bilgi/kokler_tr.bin", dilBilgisi.kokOzelDurumlari());
        sozluk = new AgacSozluk(okuyucu, alfabe, dilBilgisi.kokOzelDurumlari());
        bulucu = new KesinKokAdayiBulucu(sozluk.getAgac());
        List list = bulucu.adayKokleriBul("etkiler");
        out.println(list);
    }


    @Test
    public void ozelKokAgacTest() throws IOException {
        sozluk=sozlukUret("kaynaklar/tr/test/agac-kokler-2.txt");
        bulucu = new KesinKokAdayiBulucu(sozluk.getAgac());
        assertTrue("tek sonuc bekleniyordu", bulucu.adayKokleriBul("atoller").size()==1);
    }

    private AgacSozluk sozlukUret(String duzyaziDosya) throws IOException {
        okuyucu = new DuzYaziKokOkuyucu(
                duzyaziDosya,
                dilBilgisi.kokOzelDurumlari(),
                alfabe,
                dilAyarlari.kokTipiAdlari());
        AgacSozluk s = new AgacSozluk(okuyucu, alfabe, dilBilgisi.kokOzelDurumlari());
        return s;
    }





}
