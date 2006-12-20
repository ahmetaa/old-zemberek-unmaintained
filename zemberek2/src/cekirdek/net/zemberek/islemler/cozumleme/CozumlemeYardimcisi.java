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
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;

/**
 * User: ahmet
 * Date: Sep 11, 2005
 */
public interface CozumlemeYardimcisi {

    /**
     * kelimenin icindeki olusumu kok'un orjinal haline gore ve gerekli noktalama isaretlerine gore
     * bicimlendirir. Ornegin Turkiye turkcesinde "ankaraya" -> "Ankara'ya" ve "bbceye"->"BBC'ye" seklinde.
     * Bu metod ozellikle oneri mekanizmasinda kullaniliyor.
     * @param kelime : cozumleme sonrasi olusan kelime.
     */
    void kelimeBicimlendir(Kelime kelime);

    /**
     * eger kok ozel karaterler iceriyorsa bunun giris ile olan uygunlugunu denetler.
     * @param kelime
     * @param giris
     * @return eger kok orjinal icereigi ve kurallari girise uygunsa true.
     */
    boolean kelimeBicimiDenetle(Kelime kelime, String giris);

    /**
     * Asagidaki aciklamalar Turkiye Turkcesi icindir.
     * Kisaltmalarin cozumlenmesi sirasinda karsilasilan bir sorun bazi durumlarda
     * kokun hic sesli icermemesi, ya da kisaltmanin okunusunun son sessizin okunusuna bagli olmasidir.
     * Bu durumda eklenecek ekin belirlenmesi son harfin
     * okunusu ile belirlenir. Bu durumun cozumleme islemine uygulanabilmesi icin
     * hem giris hem de kok dizisinde degisiklik yapilamsi gerekebiliyor. Bu metod sesli icermeyen
     * kok ve girise gecici sesli eklenmesi ya da baska gerekli bir ozel durum varsa uygulanmasini
     * saglar.
     * @param kokDizi
     * @param girisDizi
     */
    boolean kokGirisDegismiVarsaUygula(Kok kok, HarfDizisi kokDizi, HarfDizisi girisDizi);

    /**
     * Eger dil icin denetleme cebi olusturulmussa cepte arama islemi yapilir.
     * @param str
     * @return true eger String cepte yer aliyorsa.
     */
    boolean cepteAra(String str);

}
