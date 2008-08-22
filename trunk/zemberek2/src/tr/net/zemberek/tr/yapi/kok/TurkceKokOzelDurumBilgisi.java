/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tr.yapi.kok;

import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.CIFTLEME;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.EK_OZEL_DURUMU;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.FIIL_ARA_SESLI_DUSMESI;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.FIIL_GECISSIZ;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.FIIL_KOK_BOZULMASI;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.GENIS_ZAMAN;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.ISIM_SESLI_DUSMESI;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.ISIM_SON_SESLI_DUSMESI;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.ISIM_TAMLAMASI;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.KAYNASTIRMA_N;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.KESME;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.KESMESIZ;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.KISALTMA_SON_SESLI;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.KISALTMA_SON_SESSIZ;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.KUCULTME;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.OZEL_IC_KARAKTER;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.SESSIZ_YUMUSAMASI;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.SESSIZ_YUMUSAMASI_NK;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.SIMDIKI_ZAMAN;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.SU_OZEL_DURUMU;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.TEKIL_KISI_BOZULMASI;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.TERS_SESLI_EK;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.TIRE;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.YALIN;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.ZAMIR_IM;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.ZAMIR_IN;
import static net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri.ZAMIR_SESLI_OZEL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zemberek.yapi.*;
import net.zemberek.yapi.ek.EkYonetici;
import net.zemberek.yapi.kok.AraSesliDusmesi;
import net.zemberek.yapi.kok.BosHarfDizisiIslemi;
import net.zemberek.yapi.kok.Ciftleme;
import net.zemberek.yapi.kok.KokOzelDurumBilgisi;
import net.zemberek.yapi.kok.KokOzelDurumu;
import net.zemberek.yapi.kok.SonHarfDusmesi;
import net.zemberek.yapi.kok.SonSesliIncelt;
import net.zemberek.yapi.kok.TemelKokOzelDurumBilgisi;
import net.zemberek.yapi.kok.Ulama;
import net.zemberek.yapi.kok.YeniIcerikAta;
import net.zemberek.yapi.kok.Yumusama;


/**
 * Turkce icin gecerli kok ozle durumlarinin olusturulmasi, ve islenmesi islemleri bu sinif uzerinden
 * gerceklestirilir. Turkce ozel durum tanimlamalari TurkceKokOzelDurumTipleri sinifinda yapilmistir.
 */
public class TurkceKokOzelDurumBilgisi extends TemelKokOzelDurumBilgisi implements KokOzelDurumBilgisi {

    private static final String[] BOS_STRING_DIZISI = new String[0];

    /**
     * KokOzel durum nesneleri burada uretilir. uretim icin "uretici" metodu kullanilir. bu metod KokOzelDurumu sinifi
     * icinde yer alan Uretim sinifindan bir nesne dondurur. Bu nesne uzerinden kok izel durumu parametreleri belirlenir
     * ve ekle sinifi icinde kokOzelDurumu nesnesi uretilip cesitli map, dizi vs tasiyici parametrelere atanir.
     */
    private void uret() {
        ekle(uretici(SESSIZ_YUMUSAMASI, new Yumusama()).
                sesliEkIleOlusur(true).
                yapiBozucu(true));

        ekle(uretici(SESSIZ_YUMUSAMASI_NK, new YumusamaNk(alfabe)).
                sesliEkIleOlusur(true).
                yapiBozucu(true));

        ekle(uretici(ISIM_SESLI_DUSMESI, new AraSesliDusmesi()).yapiBozucu(true));

        ekle(uretici(CIFTLEME, new Ciftleme()).
                sesliEkIleOlusur(true).
                yapiBozucu(true));

        ekle(uretici(FIIL_ARA_SESLI_DUSMESI, new AraSesliDusmesi()).yapiBozucu(true));

        ekle(uretici(KUCULTME, new SonHarfDusmesi()).yapiBozucu(true));

        Map<String, String> benSenDonusum = new HashMap<String, String>();

        benSenDonusum.put("ben", "ban");
        benSenDonusum.put("sen", "san");
        ekle(uretici(TEKIL_KISI_BOZULMASI, new YeniIcerikAta(alfabe, benSenDonusum)).yapiBozucu(true));

        Map<String, String> deYeDonusum = new HashMap<String, String>();
        deYeDonusum.put("de", "di");
        deYeDonusum.put("ye", "yi");
        ekle(uretici(FIIL_KOK_BOZULMASI, new YeniIcerikAta(alfabe, deYeDonusum)).yapiBozucu(true));

        ekle(uretici(TERS_SESLI_EK, new SonSesliIncelt(alfabe)).
                yapiBozucu(true).
                herZamanOlusur(true));

        ekle(uretici(SIMDIKI_ZAMAN, new SonHarfDusmesi())
                .yapiBozucu(true)
                .otomatikBelirlenir(true));

        ekle(uretici(ISIM_SON_SESLI_DUSMESI, new SonHarfDusmesi()).
                secimlik(true).
                yapiBozucu(true));

        HarfDizisi n = new HarfDizisi("n", alfabe);
        ekle(uretici(ZAMIR_SESLI_OZEL, new Ulama(n)).yapiBozucu(true));

        ekle(uretici(ISIM_TAMLAMASI, new BosHarfDizisiIslemi()).ekKisitlayici(true));

        bosOzelDurumEkle(
                YALIN,
                EK_OZEL_DURUMU,
                GENIS_ZAMAN,
                FIIL_GECISSIZ,
                KAYNASTIRMA_N,
                KESMESIZ,
                TIRE,
                KESME,
                OZEL_IC_KARAKTER,
                ZAMIR_IM,
                ZAMIR_IN,
                KISALTMA_SON_SESLI,
                KISALTMA_SON_SESSIZ,
                SU_OZEL_DURUMU);
    }

    public TurkceKokOzelDurumBilgisi(EkYonetici ekler, Alfabe alfabe) {
        super(ekler, alfabe);
        uret();
    }

    public String[] ozelDurumUygula(Kok kok) {
        //kok icinde ozel durum yok ise cik..
        if (!kok.ozelDurumVarmi())
            return BOS_STRING_DIZISI;

        HarfDizisi hdizi = new HarfDizisi(kok.icerik(), alfabe);

        List<String> degismisIcerikler = new ArrayList<String>(1);

        //ara sesli dusmesi nedeniyle bazen yapay oarak kok'e ters sesli etkisi ozel durumunun eklenmesi gerekir.
        // nakit -> nakde seklinde. normal kosullarda "nakda" olusmasi gerekirdi.
        boolean eskiSonsesliInce = false;
        if (kok.ozelDurumIceriyormu(ISIM_SESLI_DUSMESI))
            eskiSonsesliInce = hdizi.sonSesli().inceSesliMi();

        boolean yapiBozucuOzelDurumvar = false;

        //ters sesli ozel durumu yapi bozucu ama sadece seslinin tipini degistirdiginden
        //islemeye gerek yok.
        if (kok.ozelDurumDizisi().length == 1 && kok.ozelDurumIceriyormu(TERS_SESLI_EK))
            return new String[0];

        // kok uzerindeki ozel durumlar basta sona taranip ozel durum koke uygulaniyor.
        for (KokOzelDurumu ozelDurum : kok.ozelDurumDizisi()) {
            // kucultme ozel durumunda problem var, cunku kok'te hem kucultme hem yumusama uygulaniyor.
            if (ozelDurum == null) {
                logger.warning("null ozel durum!. Kok:" + kok);
                return new String[0];
            }
            if (!ozelDurum.equals(ozelDurum(KUCULTME)))
                ozelDurum.uygula(hdizi);
            if (ozelDurum.yapiBozucumu())
                yapiBozucuOzelDurumvar = true;
        }
        // ara sesli dusmesi durumunda dusen sesi ile dustukten sonra olusan seslinin farkli olmasi durumunda
        // kok'e bir de ters sesli ek ozel durumu ekleniyor.,
        if (kok.ozelDurumIceriyormu(ISIM_SESLI_DUSMESI)
                || kok.ozelDurumIceriyormu(FIIL_ARA_SESLI_DUSMESI)) {
            if (!hdizi.sonSesli().inceSesliMi() && eskiSonsesliInce)
                kok.ozelDurumEkle(ozelDurumlar.get(TERS_SESLI_EK));
        }

        if (yapiBozucuOzelDurumvar)
            degismisIcerikler.add(hdizi.toString());

        if (kok.ozelDurumVarmi() &&
                kok.ozelDurumIceriyormu(KUCULTME) &&
                kok.ozelDurumIceriyormu(SESSIZ_YUMUSAMASI)) {
            HarfDizisi tempDizi = new HarfDizisi(kok.icerik(), alfabe);
            ozelDurum(KUCULTME).uygula(tempDizi);
            degismisIcerikler.add(tempDizi.toString());
        }
        // yani ozel durumlar eklenmis olabileceginden koke ods'u tekrar koke esle.
        return degismisIcerikler.toArray(new String[degismisIcerikler.size()]);
    }

    public void ozelDurumBelirle(Kok kok) {
        // eger bir fiilin son harfi sesli ise bu dogrudan simdiki zaman ozel durumu olarak ele alinir.
        // bu ozel durum bilgi tabaninda ayrica belirtilmedigi icin burada kok'e eklenir.  aramak -> ar(a)Iyor
        char sonChar = kok.icerik().charAt(kok.icerik().length() - 1);
        if (kok.tip() == KelimeTipi.FIIL && alfabe.harf(sonChar).sesliMi()) {
            //demek-yemek fiilleri icin bu uygulama yapilamaz.
            if (!kok.ozelDurumIceriyormu(FIIL_KOK_BOZULMASI)) {
                kok.ozelDurumEkle(ozelDurumlar.get(SIMDIKI_ZAMAN));
            }
        }
    }

    public void duzyaziOzelDurumOku(Kok kok, String okunanIcerik, String[] parcalar) {
        for (int i = 2; i < parcalar.length; i++) {

            String ozelDurum = parcalar[i];

            if (ozelDurum.startsWith(OZEL_IC_KARAKTER.kisaAd()))
                kok.ozelDurumEkle(ozelDurumlar.get(OZEL_IC_KARAKTER));

            //kisaltma ozel durumunun analizi burada yapiliyor.
            if (ozelDurum.startsWith(KISALTMA_SON_SESLI.kisaAd())) {
                int loc = ozelDurum.indexOf(':');
                if (loc > 0) {
                    String parca = ozelDurum.substring(loc + 1);
                    char sonSesli = parca.charAt(0);
                    if (!alfabe.harf(sonSesli).sesliMi())
                        logger.warning("Hatali kisaltma harfi.. Sesli bekleniyordu." + ozelDurum);
                    Kisaltma kis = (Kisaltma) kok;
                    kis.setKisaltmaSonSeslisi(sonSesli);
                    if (parca.length() > 1) {
                        kok.ozelDurumEkle(ozelDurumlar.get(KISALTMA_SON_SESSIZ));
                    } else
                        kok.ozelDurumCikar(KISALTMA_SON_SESLI);
                } else {
                    char sonHarf = kok.icerik().charAt(kok.icerik().length() - 1);
                    if (!alfabe.harf(sonHarf).sesliMi()) {
                        ((Kisaltma) kok).setKisaltmaSonSeslisi('e');
                        kok.ozelDurumEkle(ozelDurumlar.get(KISALTMA_SON_SESLI));
                    }
                }
                continue;
            }

            //diger ozel durumlarin elde edilmesi..
            KokOzelDurumu oz = ozelDurum(ozelDurum);
            if (oz != null) {
                kok.ozelDurumEkle(oz);
            } else {
                logger.warning("Hatali kok bileseni" + kok.icerik() + " Token: " + ozelDurum);
            }
        }

        //kisaltma ve ozel karakter iceren kokler disinda asil icerik olarak bir sey yazma.
        if (kok.tip() != KelimeTipi.KISALTMA && !kok.ozelDurumIceriyormu(OZEL_IC_KARAKTER))
            kok.setAsil(null);
        else kok.ozelDurumEkle(ozelDurumlar.get(OZEL_IC_KARAKTER));
    }

    public void kokIcerikIsle(Kok kok, KelimeTipi tip, String icerik) {
        //tip kisaltma ise ya da icerik ozel karakterler iceriyorsa bunu kok'un asil haline ata.
        if (tip.equals(KelimeTipi.KISALTMA))
            kok.setAsil(icerik);
        if (tip.equals(KelimeTipi.FIIL) && (icerik.endsWith("mek") || icerik.endsWith("mak"))) {
            icerik = icerik.substring(0, icerik.length() - 3);
            kok.setIcerik(icerik);
        }
    }
}
