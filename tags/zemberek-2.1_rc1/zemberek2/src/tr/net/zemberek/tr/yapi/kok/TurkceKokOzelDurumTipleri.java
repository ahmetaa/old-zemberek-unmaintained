/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tr.yapi.kok;

import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.FIIL_BERABERLIK_IS;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.FIIL_DONUSUM_ECEK;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.FIIL_DONUSUM_EN;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.FIIL_DONUSUM_IS;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.FIIL_EDILGEN_IL;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.FIIL_EMIR_SIZRESMI_INIZ;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.FIIL_EMIR_SIZ_IN;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.FIIL_GELECEKZAMAN_ECEK;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.FIIL_ISTEK_E;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.FIIL_SIMDIKIZAMAN_IYOR;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.FIIL_SUREKLILIK_EREK;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.FIIL_SURERLIK_EDUR;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.FIIL_SURERLIK_EGEL;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.FIIL_SURERLIK_EKAL;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.FIIL_YAKLASMA_AYAZ;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.FIIL_YETENEK_EBIL;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.ISIM_BELIRTME_I;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.ISIM_BULUNMA_LI;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.ISIM_BULUNMA_LIK;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.ISIM_CIKMA_DEN;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.ISIM_COGUL_LER;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.ISIM_DURUM_LIK;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.ISIM_ILGI_CI;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.ISIM_KALMA_DE;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.ISIM_KUCULTME_CEGIZ;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.ISIM_KUCULTME_CIK;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.ISIM_SAHIPLIK_BEN_IM;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.ISIM_SAHIPLIK_BIZ_IMIZ;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.ISIM_SAHIPLIK_ONLAR_LERI;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.ISIM_SAHIPLIK_O_I;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.ISIM_SAHIPLIK_SEN_IN;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.ISIM_SAHIPLIK_SIZ_INIZ;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.ISIM_TAMLAMA_I;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.ISIM_TAMLAMA_IN;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.ISIM_TARAFINDAN_CE;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.ISIM_YOKLUK_SIZ;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.ISIM_YONELME_E;
import net.zemberek.yapi.kok.KokOzelDurumTipi;

/**
 * User: ahmet
 * Date: Sep 9, 2006
 */
public enum TurkceKokOzelDurumTipleri implements KokOzelDurumTipi {

    SESSIZ_YUMUSAMASI("YUM"),

    SESSIZ_YUMUSAMASI_NK("YUM_NK"),

    ISIM_SESLI_DUSMESI("DUS",
            ISIM_YONELME_E,
            ISIM_BELIRTME_I,
            ISIM_SAHIPLIK_BEN_IM,
            ISIM_SAHIPLIK_SEN_IN,
            ISIM_SAHIPLIK_SIZ_INIZ,
            ISIM_SAHIPLIK_O_I,
            ISIM_SAHIPLIK_BIZ_IMIZ,
            ISIM_TAMLAMA_IN),

    CIFTLEME("CIFT"),

    FIIL_ARA_SESLI_DUSMESI("DUS_FI", FIIL_EDILGEN_IL),

    KUCULTME("KUCULTME", ISIM_KUCULTME_CIK),

    TEKIL_KISI_BOZULMASI("TEKIL_KISI_BOZULMASI", ISIM_YONELME_E),

    FIIL_KOK_BOZULMASI("FIIL_KOK_BOZULMASI",
            FIIL_SIMDIKIZAMAN_IYOR,
            FIIL_GELECEKZAMAN_ECEK,
            FIIL_DONUSUM_ECEK,
            FIIL_DONUSUM_EN,
            FIIL_DONUSUM_IS,
            FIIL_ISTEK_E,
            FIIL_SUREKLILIK_EREK,
            FIIL_SURERLIK_EDUR,
            FIIL_SURERLIK_EGEL,
            FIIL_SURERLIK_EKAL,
            FIIL_YAKLASMA_AYAZ,
            FIIL_YETENEK_EBIL,
            FIIL_BERABERLIK_IS,
            FIIL_EMIR_SIZ_IN,
            FIIL_EMIR_SIZRESMI_INIZ),

    TERS_SESLI_EK("TERS"),

    SIMDIKI_ZAMAN("SDK_ZAMAN", FIIL_SIMDIKIZAMAN_IYOR),

    ISIM_SON_SESLI_DUSMESI("DUS_SON",
            ISIM_KALMA_DE,
            ISIM_CIKMA_DEN,
            ISIM_COGUL_LER),

    ZAMIR_SESLI_OZEL("ZAMIR_SESLI_OZEL",
            ISIM_YONELME_E,
            ISIM_KALMA_DE,
            ISIM_CIKMA_DEN,
            ISIM_BELIRTME_I,
            ISIM_TAMLAMA_IN,
            ISIM_YOKLUK_SIZ,
            ISIM_COGUL_LER,
            ISIM_TARAFINDAN_CE,
            ISIM_DURUM_LIK,
            ISIM_KUCULTME_CEGIZ),

    /*
     * bazi kokler aslinda saf kok degil, icinde isim tamlamasi iceriyor
     * mesela, zeytinyagi, acemborusu gibi.
     * bu koklere bazi ekler eklendiginde kok bozuluyor
     * mesela: acemborusu -> acemborulari seklinde.
     * bu kokler sistemde ozel sekilde saklaniyor.
     * acemborusu -> acemboru IS_TAM seklinde tanimlanmistir.
     * ayni sekilde zeytinyag IS_TAM gibi
     */    
    ISIM_TAMLAMASI("IS_TAM",
            ISIM_SAHIPLIK_BEN_IM,
            ISIM_SAHIPLIK_SEN_IN,
            ISIM_SAHIPLIK_O_I,
            ISIM_SAHIPLIK_BIZ_IMIZ,
            ISIM_SAHIPLIK_SIZ_INIZ,
            ISIM_SAHIPLIK_ONLAR_LERI,
            ISIM_TAMLAMA_I,
            ISIM_BULUNMA_LIK,
            ISIM_BULUNMA_LI,
            ISIM_COGUL_LER,
            ISIM_YOKLUK_SIZ,
            ISIM_TARAFINDAN_CE,
            ISIM_DURUM_LIK,
            ISIM_KUCULTME_CEGIZ,
            ISIM_ILGI_CI),

    YALIN("YAL"),
    GENIS_ZAMAN("GEN"),
    EK_OZEL_DURUMU("EK"),
    FIIL_GECISSIZ("GEC"),
    KAYNASTIRMA_N("KAYNASTIRMA_N"),
    KESMESIZ("KESMESIZ"),
    TIRE("TIRE"),
    KESME("KESME"),
    OZEL_IC_KARAKTER("OZEL_IC_KARAKTER"),
    ZAMIR_IM("ZAMIR_IM"),
    ZAMIR_IN("ZAMIR_IN"),
    KISALTMA_SON_SESLI("SON"),
    KISALTMA_SON_SESSIZ("SON_N"),
    SU_OZEL_DURUMU("FARKLI_KAYNASTIRMA");

    private String kisaAd;
    private String[] ekAdlari = new String[0];

    TurkceKokOzelDurumTipleri(String kisaAd, String... ekAdlari) {
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
