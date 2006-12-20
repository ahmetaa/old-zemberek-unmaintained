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

package net.zemberek.yapi.kok;

/**
 * User: ahmet
 * Date: Sep 7, 2006
 */
public interface KokOzelDurumTipi {

    /**
     * ozel durumun tam adini temsil eder.
     * @return ad
     */
    String ad();

    /**
     * ozel durumun duz yazi dosyasindaki ifade edilen halini temsil eder.
     * @return kisaAd
     */
    String kisaAd();

    /**
     * ozel durum indeksi enum ordinal() bilgisi ile uretilir.
     * (yani enum bilesninin sira indeksi)
     * bu bilgi ikili kok olusumu sirasinda ozel durumu temsil etmek icin dosyaya yazilir.
     * okuma sirasinda bu indeks ile ozel duruma erisilir. Eger bir sekilde ozel durumlarin
     * enum iceirsindeki sirasi gelistirme asamasinda degistirilirse ikili kok dosyasinin
     * tekrar uretilmesi gerekir..
     * @return ozel durum indeksi.
     */
    int indeks();

    /**
     * bu ozel durumun olusamasina neden olacak eklerin adlari.
     * @return 0 yada n uzunluklu dizi.
     */
    String[] ekAdlari();
}
