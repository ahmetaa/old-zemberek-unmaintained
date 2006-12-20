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

package net.zemberek.az.yapi;

import net.zemberek.yapi.HeceBulucu;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Alfabe;


public class AzericeHeceBulucu implements HeceBulucu {

    Alfabe alfabe;


    public AzericeHeceBulucu(Alfabe alfabe) {
        this.alfabe = alfabe;
    }

    public int sonHeceHarfSayisi(HarfDizisi dizi) {
        return 0;
    }
}
