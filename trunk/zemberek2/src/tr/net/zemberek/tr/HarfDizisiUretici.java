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

package net.zemberek.tr;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;

/**
 * User: ahmet
 * Date: May 4, 2006
 */
public class HarfDizisiUretici {

    Alfabe alfabe;

    public HarfDizisiUretici(Alfabe alfabe) {
        this.alfabe = alfabe;
    }

    public final HarfDizisi uret(String str) {
        return new HarfDizisi(str, alfabe);
    }
}
