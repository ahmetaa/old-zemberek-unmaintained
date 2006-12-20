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
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 */
public class TestTurkcedenDonusturucu extends TemelTest {

    @Test
    public void testToAscii() {
        AsciiDonusturucu donusturucu = new AsciiDonusturucu(alfabe);
        String turkce = "abci\u00e7\u011f\u0131\u00f6\u015f\u00fc";
        String sonuc = donusturucu.toAscii(turkce);
        assertEquals("abcicgiosu", sonuc);
        String abuk = "32432aas_";
        sonuc = donusturucu.toAscii(abuk);
        assertEquals("32432aas_", sonuc);
    }

}
