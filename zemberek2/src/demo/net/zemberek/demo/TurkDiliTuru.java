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

package net.zemberek.demo;

import net.zemberek.erisim.Zemberek;
import net.zemberek.yapi.DilAyarlari;

/**
 * User: ahmet
 * Date: Jun 23, 2006
 */
public enum TurkDiliTuru {

    TURKIYE("net.zemberek.tr.yapi.TurkiyeTurkcesi"),
    TURKMEN("net.zemberek.tm.yapi.Turkmence"),
    AZERI("net.zemberek.az.yapi.Azerice");

    //KAZAK("kk"),
    //OZBEK("uz"),
    //TATAR("tt"),
    //UYGUR("ug");

    TurkDiliTuru(String sinif) {
        this.sinif = sinif;
    }

    private String sinif;

    public String sinif() {
        return sinif;
    }

    public Zemberek zemberekUret() throws ClassNotFoundException {
        try {
            DilAyarlari da = (DilAyarlari) Class.forName(sinif).newInstance();
            return new Zemberek(da);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean sinifVarmi() throws ClassNotFoundException {
        try {
            this.getClass().getClassLoader().loadClass(sinif);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}