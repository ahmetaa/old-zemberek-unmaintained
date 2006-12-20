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

package net.zemberek.az.islemler;

import net.zemberek.islemler.DenetlemeCebi;
import net.zemberek.islemler.cozumleme.CozumlemeYardimcisi;
import net.zemberek.yapi.*;


/**
 * Bu sinif Turkiye Turkcesine ozel cesitli islemleri icerir.
 * User: ahmet
 * Date: Sep 11, 2005
 */
public class AzericeCozumlemeYardimcisi implements CozumlemeYardimcisi {

    private Alfabe alfabe;
    private DenetlemeCebi cep;

    public AzericeCozumlemeYardimcisi(Alfabe alfabe, DenetlemeCebi cep) {
        this.alfabe = alfabe;
        this.cep = cep;
    }

    public void kelimeBicimlendir(Kelime kelime) {
    }

    public boolean kelimeBicimiDenetle(Kelime kelime, String giris) {
        return giris.length() != 0;
    }

    public boolean kokGirisDegismiVarsaUygula(Kok kok, HarfDizisi kokDizi, HarfDizisi girisDizi) {
        return false;
    }

    public boolean cepteAra(String str) {
        return false;
    }
}
