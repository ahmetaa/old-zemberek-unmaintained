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
 *  The Original Code is Zemberek Do?al Dil ??leme Kütüphanesi.
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Ak?n, Mehmet D. Ak?n.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.yapi.alfabe;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.TurkceHarf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class TestAlfabe {

    private Alfabe alfabe;

    @Before
    public void once() throws IOException {
        alfabe = new Alfabe(
                "kaynaklar/tr/bilgi/harf_tr.txt",
                "tr");
    }

    @Test
    public void testHarfErisim() {
        TurkceHarf harf = new TurkceHarf('a', 1);
        harf.setSesli(true);

        TurkceHarf okunan = alfabe.harf('a');
        assertEquals(harf.charDeger(), okunan.charDeger());
        assertTrue(harf.sesliMi());

    }

    @Test
    public void testAyikla() {
        String kel = "a'ghh-";
        assertEquals(alfabe.ayikla(kel), "aghh");
    }

    @Test
    public void testTurkceMi() {
        String kel = "wws$$dgsdashj";
        assertTrue(!alfabe.cozumlemeyeUygunMu(kel));
        kel = "merhaba";
        assertTrue(alfabe.cozumlemeyeUygunMu(kel));
    }

    @Test
    public void testLowerUpperCase() {
        TurkceHarf ii = alfabe.harf(Alfabe.CHAR_ii);
        TurkceHarf harfI = alfabe.buyukHarf(ii);
        assertEquals(harfI.charDeger(), Alfabe.CHAR_II);
        TurkceHarf i = alfabe.harf('i');
        TurkceHarf harfBuyuki = alfabe.buyukHarf(i);
        assertEquals(harfBuyuki.charDeger(), 'I');
    }

}
