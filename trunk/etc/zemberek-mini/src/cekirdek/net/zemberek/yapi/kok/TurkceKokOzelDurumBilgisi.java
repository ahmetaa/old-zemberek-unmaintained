package net.zemberek.yapi.kok;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.ek.EkYonetici;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Turkce icin gecerli kok ozle durumlarinin olusturulmasi, ve islenmesi islemleri bu sinif uzerinden
 * gerceklestirilir. Turkce ozel durum tanimlamalari TurkceKokOzelDurumTipleri sinifinda yapilmistir.
 */
public class TurkceKokOzelDurumBilgisi extends TemelKokOzelDurumBilgisi implements KokOzelDurumBilgisi {

    /**
     * KokOzel durum nesneleri burada uretilir. uretim icin "uretici" metodu kullanilir. bu metod KokOzelDurumu sinifi
     * icinde yer alan Uretim sinifindan bir nesne dondurur. Bu nesne uzerinden kok izel durumu parametreleri belirlenir
     * ve ekle sinifi icinde kokOzelDurumu nesnesi uretilip cesitli map, dizi vs tasiyici parametrelere atanir.
     */
    private void uret() {
        ekle(uretici(TurkceKokOzelDurumTipleri.SESSIZ_YUMUSAMASI, new Yumusama()).
                sesliEkIleOlusur(true).
                yapiBozucu(true));

        ekle(uretici(TurkceKokOzelDurumTipleri.SESSIZ_YUMUSAMASI_NK, new YumusamaNk(alfabe)).
                sesliEkIleOlusur(true).
                yapiBozucu(true));

        ekle(uretici(TurkceKokOzelDurumTipleri.ISIM_SESLI_DUSMESI, new AraSesliDusmesi()).yapiBozucu(true));

        ekle(uretici(TurkceKokOzelDurumTipleri.CIFTLEME, new Ciftleme()).
                sesliEkIleOlusur(true).
                yapiBozucu(true));

        ekle(uretici(TurkceKokOzelDurumTipleri.FIIL_ARA_SESLI_DUSMESI, new AraSesliDusmesi()).yapiBozucu(true));

        ekle(uretici(TurkceKokOzelDurumTipleri.KUCULTME, new SonHarfDusmesi()).yapiBozucu(true));

        Map<String, String> benSenDonusum = new HashMap();

        benSenDonusum.put("ben", "ban");
        benSenDonusum.put("sen", "san");
        ekle(uretici(TurkceKokOzelDurumTipleri.TEKIL_KISI_BOZULMASI, new YeniIcerikAta(alfabe, benSenDonusum)).yapiBozucu(true));

        Map<String, String> deYeDonusum = new HashMap();
        deYeDonusum.put("de", "di");
        deYeDonusum.put("ye", "yi");
        ekle(uretici(TurkceKokOzelDurumTipleri.FIIL_KOK_BOZULMASI, new YeniIcerikAta(alfabe, deYeDonusum)).yapiBozucu(true));

        ekle(uretici(TurkceKokOzelDurumTipleri.TERS_SESLI_EK, new SonSesliIncelt(alfabe)).
                yapiBozucu(true).
                herZamanOlusur(true));

        ekle(uretici(TurkceKokOzelDurumTipleri.SIMDIKI_ZAMAN, new SonHarfDusmesi()).yapiBozucu(true));

        ekle(uretici(TurkceKokOzelDurumTipleri.ISIM_SON_SESLI_DUSMESI, new SonHarfDusmesi()).
                secimlik(true).
                yapiBozucu(true));

        HarfDizisi yu = new HarfDizisi("yu", alfabe);
        ekle(uretici(TurkceKokOzelDurumTipleri.SU_OZEL_DURUMU, new Ulama(yu)).yapiBozucu(true));

        HarfDizisi n = new HarfDizisi("n", alfabe);
        ekle(uretici(TurkceKokOzelDurumTipleri.ZAMIR_SESLI_OZEL, new Ulama(n)).yapiBozucu(true));

        ekle(uretici(TurkceKokOzelDurumTipleri.ISIM_TAMLAMASI, new BosHarfDizisiIslemi()).ekKisitlayici(true));

        bosOzelDurumEkle(TurkceKokOzelDurumTipleri.YALIN,
                TurkceKokOzelDurumTipleri.EK_OZEL_DURUMU,
                TurkceKokOzelDurumTipleri.GENIS_ZAMAN,
                TurkceKokOzelDurumTipleri.FIIL_GECISSIZ,
                TurkceKokOzelDurumTipleri.KAYNASTIRMA_N,
                TurkceKokOzelDurumTipleri.KESMESIZ,
                TurkceKokOzelDurumTipleri.TIRE,
                TurkceKokOzelDurumTipleri.KESME,
                TurkceKokOzelDurumTipleri.OZEL_IC_KARAKTER,
                TurkceKokOzelDurumTipleri.ZAMIR_IM,
                TurkceKokOzelDurumTipleri.ZAMIR_IN,
                TurkceKokOzelDurumTipleri.KISALTMA_SON_SESLI, TurkceKokOzelDurumTipleri.KISALTMA_SON_SESSIZ);
    }

    public TurkceKokOzelDurumBilgisi(EkYonetici ekler, Alfabe alfabe) {
        super(ekler, alfabe);
        uret();
    }

    public String[] ozelDurumUygula(Kok kok) {
        //kok icinde ozel durum yok ise cik..
        if (!kok.ozelDurumVarmi())
            return new String[0];

        HarfDizisi hdizi = new HarfDizisi(kok.icerik(), alfabe);

        List degismisIcerikler = new ArrayList(1);

        //ara sesli dusmesi nedeniyle bazen yapay oarak kok'e ters sesli etkisi ozel durumunun eklenmesi gerekir.
        // nakit -> nakde seklinde. normal kosullarda "nakda" olusmasi gerekirdi.
        boolean eskiSonsesliInce = false;
        if (kok.ozelDurumIceriyormu(TurkceKokOzelDurumTipleri.ISIM_SESLI_DUSMESI))
            eskiSonsesliInce = hdizi.sonSesli().inceSesliMi();

        boolean yapiBozucuOzelDurumvar = false;
        // kok uzerindeki ozel durumlar basta sona taranip ozel durum koke uygulaniyor.
        for (KokOzelDurumu ozelDurum : kok.ozelDurumDizisi()) {
            // kucultme ozel durumunda problem var, cunku kok'te hem kucultme hem yumusama uygulaniyor.
            if (ozelDurum == null) {
                System.out.println("kok = " + kok);
                System.exit(-1);
            }
            if (!ozelDurum.equals(ozelDurum(TurkceKokOzelDurumTipleri.KUCULTME)))
                ozelDurum.uygula(hdizi);
            if (ozelDurum.yapiBozucumu())
                yapiBozucuOzelDurumvar = true;
        }
        // ara sesli dusmesi durumunda dusen sesi ile dustukten sonra olusan seslinin farkli olmasi durumunda
        // kok'e bir de ters sesli ek ozel durumu ekleniyor.,
        if (kok.ozelDurumIceriyormu(TurkceKokOzelDurumTipleri.ISIM_SESLI_DUSMESI)
                || kok.ozelDurumIceriyormu(TurkceKokOzelDurumTipleri.FIIL_ARA_SESLI_DUSMESI)) {
            if (!hdizi.sonSesli().inceSesliMi() && eskiSonsesliInce)
                kok.ozelDurumEkle(ozelDurumlar.get(TurkceKokOzelDurumTipleri.TERS_SESLI_EK));
        }

        if (yapiBozucuOzelDurumvar)
            degismisIcerikler.add(hdizi.toString());

        if (kok.ozelDurumVarmi() &&
                kok.ozelDurumIceriyormu(TurkceKokOzelDurumTipleri.KUCULTME) &&
                kok.ozelDurumIceriyormu(TurkceKokOzelDurumTipleri.SESSIZ_YUMUSAMASI)) {
            HarfDizisi tempDizi = new HarfDizisi(kok.icerik(), alfabe);
            ozelDurum(TurkceKokOzelDurumTipleri.KUCULTME).uygula(tempDizi);
            degismisIcerikler.add(tempDizi.toString());
        }
        // yani ozel durumlar eklenmis olabileceginden koke ods'u tekrar koke esle.
        return (String[]) degismisIcerikler.toArray(new String[degismisIcerikler.size()]);
    }

    public void ozelDurumBelirle(Kok kok) {
        // eger bir fiilin son harfi sesli ise bu dogrudan simdiki zaman ozel durumu olarak ele alinir.
        // bu ozel durum bilgi tabaninda ayrica belirtilmedigi icin burada kok'e eklenir.  aramak -> ar(a)Iyor
        char sonChar = kok.icerik().charAt(kok.icerik().length() - 1);
        if (kok.tip() == KelimeTipi.FIIL && alfabe.harf(sonChar).sesliMi()) {
            //demek-yemek fiilleri icin bu uygulama yapilamaz.
            if (!kok.ozelDurumIceriyormu(TurkceKokOzelDurumTipleri.FIIL_KOK_BOZULMASI)) {
                kok.ozelDurumEkle(ozelDurumlar.get(TurkceKokOzelDurumTipleri.SIMDIKI_ZAMAN));
            }
        }
    }

    public void duzyaziOzelDurumOku(Kok kok, String okunanIcerik, String[] parcalar) {
        for (int i = 2; i < parcalar.length; i++) {

            String ozelDurum = parcalar[i];

            //kisaltma ozel durumunun analizi burada yapiliyor.
            if (ozelDurum.startsWith(TurkceKokOzelDurumTipleri.KISALTMA_SON_SESLI.kisaAd())) {
                int loc = ozelDurum.indexOf(':');
                if (loc > 0) {
                    String parca = ozelDurum.substring(loc + 1);
                    char sonSesli = parca.charAt(0);
                    if (!alfabe.harf(sonSesli).sesliMi())
                        System.out.println("Hatali kisaltma harfi.. Sesli bekleniyordu." + ozelDurum);
                    kok.setKisaltmaSonSeslisi(sonSesli);
                    if (parca.length() > 1) {
                        kok.ozelDurumEkle(ozelDurumlar.get(TurkceKokOzelDurumTipleri.KISALTMA_SON_SESSIZ));
                    } else
                        kok.ozelDurumCikar(TurkceKokOzelDurumTipleri.KISALTMA_SON_SESLI);
                } else {
                    char sonHarf = kok.icerik().charAt(kok.icerik().length() - 1);
                    if (!alfabe.harf(sonHarf).sesliMi()) {
                        kok.setKisaltmaSonSeslisi('e');
                        kok.ozelDurumEkle(ozelDurumlar.get(TurkceKokOzelDurumTipleri.KISALTMA_SON_SESLI));
                    }
                }
                continue;
            }

            //diger ozel durumlarin elde edilmesi..
            KokOzelDurumu oz = ozelDurum(ozelDurum);
            if (oz != null) {
                kok.ozelDurumEkle(oz);
            } else {
                System.out.println("Hatali kok bileseni" + kok.icerik() + " Token: " + ozelDurum);
            }
        }

        //kisaltmalari ve ozel karakter iceren kokleri asil icerik olarak ata.
        if (kok.tip() == KelimeTipi.KISALTMA || kok.ozelDurumIceriyormu(TurkceKokOzelDurumTipleri.OZEL_IC_KARAKTER))
            kok.setAsil(okunanIcerik);
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
