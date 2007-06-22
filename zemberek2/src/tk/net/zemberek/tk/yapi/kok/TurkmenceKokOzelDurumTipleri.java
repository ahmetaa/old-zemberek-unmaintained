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

package net.zemberek.tk.yapi.kok;

import static net.zemberek.tk.yapi.ek.TurkmenceEkAdlari.*;
import net.zemberek.yapi.kok.KokOzelDurumTipi;

/**
 * User: ahmet
 * Date: Sep 20, 2006
 */
public enum TurkmenceKokOzelDurumTipleri implements KokOzelDurumTipi {

    SESSIZ_YUMUSAMASI("YUM"),

    ARA_SESLI_DUSMESI("DUS",
            AT_DUSUM_E,
            AT_DUSUM_I,
            AT_YONKEME_MEN_IM,
            AT_YONKEME_SEN_IN,
            AT_YONKEME_OL_I,
            AT_YONKEME_BIZ_IMIZ,
            AT_YONKEME_SIZ_INIZ,
            AT_DUSUM_IN),

    IKILEME("CUBT"),

    ISLIK_ARA_SESLI_DUSMESI("DUS_FI", ISLIK_GAYDIMDEREJESI_IL),

    KICELTME("KUCULTME", AT_SYPATYASAYIJI_JIK),

    YEKELIK_KISI_BOZUMASI("YEKELIK_KISI_BOZULMASI", AT_DUSUM_E),

    TERS_SESLI_GOSULMA("TERS"),

    //SIMDIKI_ZAMAN("SDK_ZAMAN",AT_DUSUM_E),

    YALIN("YAL"),

    GENIS_ZAMAN("GEN"),

    ISIM_SON_SESLI_DUSMESI("DUS_SON", AT_DUSUM_E),

    KESMESIZ("KESMESIZ"),

    ISIM_TAMLAMASI("IS_TAM",
            AT_YONKEME_MEN_IM,
            AT_YONKEME_SEN_IN,
            AT_YONKEME_OL_I,
            AT_YONKEME_BIZ_IMIZ,
            AT_YONKEME_SIZ_INIZ,
            AT_YONKEME_OLAR_LERI,
            AT_ATYASAYIJI_LIK,
            AT_ATYASAYIJI_LI,
            AT_KOPLUK_LER,
            AT_YOKLUK_SIZ,
            AT_SYPATYASAYIJI_JAGAZ,
            AT_ATYASAYIJI_JI),

    TIRE("TIRE"),

    KESME("KESME"),

    ZAMIR_IM("ZAMIR_IM"),

    ZAMIR_IN("ZAMIR_IN"),

    ZAMIR_SESLI_OZEL("ZAMIR_SESLI_OZEL",
            AT_DUSUM_E,
            AT_DUSUM_DE,
            AT_DUSUM_DEN,
            AT_DUSUM_I,
            AT_DUSUM_IN,
            AT_YOKLUK_SIZ,
            AT_KOPLUK_LER,
            AT_SYPATYASAYIJI_JAGAZ);


    private String kisaAd;
    private String[] ekAdlari = new String[0];

    TurkmenceKokOzelDurumTipleri(String kisaAd, String... ekAdlari) {
        this.kisaAd = kisaAd;
        if (this.ekAdlari != null) {
            this.ekAdlari = ekAdlari;
        }
    }

    public String ad() {
        return this.name();
    }

    public String kisaAd() {
        return kisaAd;
    }

    public int indeks() {
        return ordinal();
    }

    public String[] ekAdlari() {
        return ekAdlari;
    }
}
