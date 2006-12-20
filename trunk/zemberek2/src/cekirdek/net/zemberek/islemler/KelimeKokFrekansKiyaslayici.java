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

import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;

import java.util.Comparator;

/**
 * iki kelimeyi kok kullanim frekansina gore kiyaslar. Sonucta o1 frekansi yuksek ise NEGATIF
 * aksi halde pozitif doner. azalan siralamada kullanilir.
 * User: ahmet
 * Date: Dec 10, 2005
 */
public class KelimeKokFrekansKiyaslayici implements Comparator<Kelime> {

    public int compare(Kelime o1, Kelime o2) {
        if (o1 == null || o2 == null) return -1;
        final Kok k1 = o1.kok();
        final Kok k2 = o2.kok();
        return k2.getFrekans() - k1.getFrekans();
    }
}
