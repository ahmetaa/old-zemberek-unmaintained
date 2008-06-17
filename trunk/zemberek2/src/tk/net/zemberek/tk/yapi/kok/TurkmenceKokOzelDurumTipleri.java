/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tk.yapi.kok;

import static net.zemberek.tk.yapi.ek.TurkmenceEkAdlari.AT_ATYASAYIJI_JI;
import static net.zemberek.tk.yapi.ek.TurkmenceEkAdlari.AT_ATYASAYIJI_LI;
import static net.zemberek.tk.yapi.ek.TurkmenceEkAdlari.AT_ATYASAYIJI_LIK;
import static net.zemberek.tk.yapi.ek.TurkmenceEkAdlari.AT_DUSUM_DE;
import static net.zemberek.tk.yapi.ek.TurkmenceEkAdlari.AT_DUSUM_DEN;
import static net.zemberek.tk.yapi.ek.TurkmenceEkAdlari.AT_DUSUM_E;
import static net.zemberek.tk.yapi.ek.TurkmenceEkAdlari.AT_DUSUM_I;
import static net.zemberek.tk.yapi.ek.TurkmenceEkAdlari.AT_DUSUM_IN;
import static net.zemberek.tk.yapi.ek.TurkmenceEkAdlari.AT_KOPLUK_LER;
import static net.zemberek.tk.yapi.ek.TurkmenceEkAdlari.AT_SYPATYASAYIJI_JAGAZ;
import static net.zemberek.tk.yapi.ek.TurkmenceEkAdlari.AT_SYPATYASAYIJI_JIK;
import static net.zemberek.tk.yapi.ek.TurkmenceEkAdlari.AT_YOKLUK_SIZ;
import static net.zemberek.tk.yapi.ek.TurkmenceEkAdlari.AT_YONKEME_BIZ_IMIZ;
import static net.zemberek.tk.yapi.ek.TurkmenceEkAdlari.AT_YONKEME_MEN_IM;
import static net.zemberek.tk.yapi.ek.TurkmenceEkAdlari.AT_YONKEME_OLAR_LERI;
import static net.zemberek.tk.yapi.ek.TurkmenceEkAdlari.AT_YONKEME_OL_I;
import static net.zemberek.tk.yapi.ek.TurkmenceEkAdlari.AT_YONKEME_SEN_IN;
import static net.zemberek.tk.yapi.ek.TurkmenceEkAdlari.AT_YONKEME_SIZ_INIZ;
import static net.zemberek.tk.yapi.ek.TurkmenceEkAdlari.ISLIK_GAYDIMDEREJESI_IL;
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
