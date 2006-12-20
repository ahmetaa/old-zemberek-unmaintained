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

package net.zemberek.islemler.cozumleme;

import net.zemberek.yapi.HarfDizisi;

/**
 */
public class KesinHDKiyaslayici implements HarfDizisiKiyaslayici {
    public final boolean kiyasla(HarfDizisi h1, HarfDizisi h2) {
        return h1.equals(h2);
    }

    public final boolean bastanKiyasla(HarfDizisi h1, HarfDizisi h2) {
        return h1.bastanKiyasla(h2);
    }

    public final boolean aradanKiyasla(HarfDizisi h1, HarfDizisi h2, int index) {
        return h1.aradanKiyasla(index, h2);
    }
}
