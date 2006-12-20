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

package net.zemberek.yapi;

public enum KelimeTipi {

    ISIM,
    FIIL,
    SIFAT,
    SAYI,
    YANKI,
    ZAMIR,
    SORU,
    IMEK,
    ZAMAN,
    EDAT,
    BAGLAC,
    OZEL,
    UNLEM,
    KISALTMA,
    HATALI;

    // Hızlı look-up için tiplerin indeksini bir dizide tutalım
    private static KelimeTipi[] degerler = KelimeTipi.values();
    
    public static KelimeTipi getTip(int indeks) {
        if(indeks<0 || indeks>=degerler.length)
          throw new ArrayIndexOutOfBoundsException("Girilen degerde indeksli KelimeTipi yok!:"+indeks);
        return degerler[indeks];
    }

    public int getIndeks() {
        return this.ordinal();
    }

}