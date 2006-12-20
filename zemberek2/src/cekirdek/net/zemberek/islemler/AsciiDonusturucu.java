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

package net.zemberek.islemler;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.TurkceHarf;

/**
 */
public class AsciiDonusturucu {
    Alfabe alfabe;

    public AsciiDonusturucu(Alfabe alfabe) {
        this.alfabe = alfabe;
    }

    public String toAscii(String giris) {
        char[] chars = giris.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            TurkceHarf harf = alfabe.harf(chars[i]);
            if (harf != null && harf != Alfabe.TANIMSIZ_HARF)
                if (harf.asciiDonusum() != null)
                    chars[i] = harf.asciiDonusum().charDeger();
        }
        return String.valueOf(chars);
    }
}
