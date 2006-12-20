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

/**
 * User: ahmet
 * Date: Sep 10, 2005
 */
public interface HeceBulucu {

    /**
     * Giren harf dizisinin sonunda mantikli olarak yer alan hecenin harf
     * sayisini dondurur.
     *
     * @param dizi: harf dizisi.
     * @return int, 1,2,3 ya da 4 donerse giris dizisinin dizinin sondan o
     *         kadarharfi heceyi temsil eder -1 donerse hecenin bulunamadigi
     *         anlamina gelir.
     */
     int sonHeceHarfSayisi(HarfDizisi dizi);
}
