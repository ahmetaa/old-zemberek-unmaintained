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
 *  Ahmet A. Akin, Mehmet D. Akin, Guychmyrat Amanmyradov.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.tk.islemler;

import net.zemberek.islemler.cozumleme.CozumlemeYardimcisi;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;

/**
 * Bu sinif Turkiye Turkcesine ozel cesitli islemleri icerir.
 * User: ahmet
 * Date: Sep 11, 2005
 */
public class TurkmenceCozumlemeYardimcisi implements CozumlemeYardimcisi {

    private Alfabe alfabe;

    public TurkmenceCozumlemeYardimcisi(Alfabe alfabe) {
        this.alfabe = alfabe;
    }

    public void kelimeBicimlendir(Kelime kelime) {
    }

    public boolean kelimeBicimiDenetle(Kelime kelime, String giris) {
        return giris.length() != 0;
    }

    public boolean kokGirisDegismiVarsaUygula(Kok kok, HarfDizisi kokDizi, HarfDizisi girisDizi) {
        return false;
    }

}
