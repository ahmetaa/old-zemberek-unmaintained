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

package net.zemberek.yapi;

import net.zemberek.TemelTest;
import net.zemberek.tr.yapi.TurkceHeceBulucu;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 * User: ahmet
 * Date: Sep 10, 2005
 */
public class TestHeceBulur extends TemelTest {

    @Test
    public void testSonHece() {
        HeceBulucu heceBulur = new TurkceHeceBulucu();
        String[] strs = {"turk", "ara", "sarta", "siir", "siiir", "kanat", "kanaat",
                "yaptirt", "artti", "arttir", "arttirt", "sirret", "siirt", "teleskop"};
        int[] sonuclar = {4, 2, 2, 2, 2, 3, 2,
                4, 2, 3, 4, 3, 3, 3};
        HarfDizisi[] girisler = new HarfDizisi[strs.length];
        for (int i = 0; i < strs.length; i++)
            girisler[i] = hd(strs[i]);
        for (int i = 0; i < girisler.length; i++) {
            HarfDizisi harfDizisi = girisler[i];
            assertEquals(harfDizisi.toString(), heceBulur.sonHeceHarfSayisi(harfDizisi), sonuclar[i]);
        }
    }
}
