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

/*
 * Created on 06.Mar.2004
 */
package net.zemberek.bilgi.kokler;

import java.util.List;

import net.zemberek.yapi.Kok;

/**
 * Kok secme islemi yazim denetleme isleminin ilk asamalarindandir. Bu arayuz sayesinde
 * farkli Kok secim algoritmalari ve veri yapilari denenebilir.
 *
 * @author MDA
 */
public interface KokAdayiBulucu {
    /**
     * @param giris: Uzerinde aday kok aramasi yapilacak giris kelimesi.
     * @return Aday kok dizisi
     */
    public List<Kok> adayKokleriBul(String giris);
}
