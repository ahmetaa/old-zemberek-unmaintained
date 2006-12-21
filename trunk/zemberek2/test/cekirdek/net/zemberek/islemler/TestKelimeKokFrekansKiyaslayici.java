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

package net.zemberek.islemler;

import net.zemberek.TemelTest;
import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: ahmet
 * Date: Dec 10, 2005
 */
public class TestKelimeKokFrekansKiyaslayici extends TemelTest {

    @Test
    public void testKiyaslayici()
    {
        Kok kok1 = new Kok("alo");
        kok1.setFrekans(10);
        Kelime k1 = new Kelime(kok1, alfabe);

        Kok kok2 = new Kok("merhaba");
        kok2.setFrekans(20);
        Kelime k2 = new Kelime(kok2, alfabe);

        List<Kelime> kel = new ArrayList();
        kel.add(k1);
        kel.add(k2);
        Collections.sort(kel, new KelimeKokFrekansKiyaslayici());

        KelimeKokFrekansKiyaslayici kiyaslayici = new KelimeKokFrekansKiyaslayici();
        assertTrue(kiyaslayici.compare(k1, k2) > 0);
        assertTrue(kiyaslayici.compare(k2, k1) < 0);
        kok2.setFrekans(10);
        assertTrue(kiyaslayici.compare(k2, k1) == 0);
        assertTrue(kiyaslayici.compare(null, k1) < 0);
        assertTrue(kiyaslayici.compare(k2, null) < 0);

    }

}
